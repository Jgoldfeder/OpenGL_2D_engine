package entities;




import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import rendering.CollisionCell;
import rendering.Renderer;
import rendering.Screen;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class AbstractDynamicEntity implements Entity{

	//initialize variables
	protected ArrayList<CollisionCell> cells = new ArrayList<CollisionCell>(); 
	protected double friction = .2;
	protected int gravity = 1;
	protected final int GRAVITY=1;
	protected double hspeed;
	protected double vspeed;
	protected double x = 0;
	protected double y = 0;
	protected int width = 0;
	protected int height = 0;
	protected Texture texture;
	protected int displayListHandle = 0;
	private Rectangle boundingBox = new Rectangle((int)x,(int)y,width,height);
	
	@Override
	public void draw() {

		
	}

	public AbstractDynamicEntity(int x,int y,int width, int height,Texture texture)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		 texture.bind();
	     displayListHandle = glGenLists(1);
		  glNewList(displayListHandle,GL_COMPILE);
		  glBegin(GL_TRIANGLES);	 
			glTexCoord2f(1, 0);
			glVertex2i(x+width, y);
			glTexCoord2f(0, 0);
			glVertex2i(x, y);
			glTexCoord2f(0, 1);
			glVertex2i(x, y+height);
			 
			glTexCoord2f(0, 1);
			glVertex2i(x, y+height);
			glTexCoord2f(1, 1);
			glVertex2i(x+width, y+height);
			glTexCoord2f(1, 0);
			glVertex2i(x+width, y);    			 
			 glEnd();
			 glEndList();
			 Renderer.addDynamic(this);
    }
	
	@Override
	public double getX() {		
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setPosition(int x, int y) {
		this.y = y;
		this.x = x;		
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;	
	}

	@Override
	public int getWidth() {		
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public boolean collided(Entity other) {
		boundingBox.setBounds((int)x,(int)y,width,height);
		return boundingBox.intersects(other.getX(),other.getY(),other.getWidth(),other.getHeight());
	}

	@Override
	public Rectangle getBoundingBox() {

		return boundingBox;
	}
	
	public void setHspeed(double speed){
		hspeed = speed;
	}
	
	public void setVspeed(double speed){
		vspeed = speed;
	}
	
	private void useFriction(){
		if(Math.abs(hspeed)<friction){
			hspeed = 0;
		}
		if(hspeed > 0){
			hspeed-= friction;
		}
		if(hspeed<0){
			hspeed+=friction;
		}
		if(Math.abs(vspeed)<friction){
			vspeed = 0;
		}
		if(vspeed > 0){
			vspeed-= friction;
		}
		if(vspeed<0){
			vspeed+=friction;
		}
	
	   
   }
	
	public void getCells(){
		for(CollisionCell c:cells){
		c.dynamicContents.remove(this);
		}
		Screen screen1 = Renderer.SCREEN[(int)Math.floor(x/640)][(int)Math.floor(y/480)];
		Screen screen2 = Renderer.SCREEN[(int)Math.floor((x+width-1)/640)][(int)Math.floor(y/480)];
		Screen screen3 = Renderer.SCREEN[(int)Math.floor(x/640)][(int)Math.floor((y+height-1)/480)];
		Screen screen4 = Renderer.SCREEN[(int)Math.floor((x+width-1)/640)][(int)Math.floor((y+height-1)/480)];

		cells = new ArrayList<CollisionCell>();
		//variable diff is increased by 1 each time the x's are different and y's are different. if it
		// equals 2 perform last condition, as this entity lies in 4 cells
		int diff = 0;
		screen1.getGrid((int)Math.floor(x/160),(int)Math.floor(y/160)).addDynamic(this);
		cells.add(screen1.getGrid((int)Math.floor(x/160),(int)Math.floor(y/160)));
		if(Math.floor(x/160) != Math.floor((width+x-1)/160)){
			screen2.getGrid((int)Math.floor((x+width-1)/160),(int)Math.floor(y/160)).addDynamic(this);
			diff++;
			cells.add(screen2.getGrid((int)Math.floor((x+width-1)/160),(int)Math.floor(y/160)));
		}
		if(Math.floor(y/160) != Math.floor((height+y-1)/160)){
			screen3.getGrid((int)Math.floor(x/160),(int)Math.floor((y+height-1)/160)).addDynamic(this);
			diff++;
			cells.add(screen3.getGrid((int)Math.floor(x/160),(int)Math.floor((y+height-1)/160)));
		}
		if(diff==2){
		    screen4.getGrid((int)Math.floor((x+width-1)/160),(int)Math.floor((y+height-1)/160)).addDynamic(this);
		    cells.add(screen4.getGrid((int)Math.floor((x+width-1)/160),(int)Math.floor((y+height-1)/160)));
		}	
	}
	
	public void update(){
		
		//slow down by friction
		useFriction();
		
		//apply gravity
		if(vspeed<8){
			vspeed+=gravity; 
		}
		
		
		
		//if against a wall, prevent collision
		if(collisionCheck((int)x-1,(int)y,width,height)){
			if(hspeed<0){
				hspeed = 0;
			}
		}
		if(collisionCheck((int)x+1,(int)y,width,height)){
			if(hspeed>0){
				hspeed = 0;
			}
		}
		
		
		//update position
		x+=hspeed;
		y+=vspeed;
		if(y>420){
			y=0;
		}
		if(x>601){
			x=11;
		}
		if(x<0){
			x=600;
		}
	 	  
		//get collision cells that this entity occupies
		
		getCells();
		
		//correct offset
		for(CollisionCell c:cells){		
			for(Entity e:c.staticContents){
				
					if(collided(e)){
						//get unit x and y for the vector
						double dx = hspeed/Math.hypot(hspeed,vspeed);
						double dy = vspeed/Math.hypot(hspeed,vspeed);
						//reset to previous position
						x-=hspeed;
						y-=vspeed;
						

						//move to contact
						while(isEmpty()){
							y+=dy;
							x+=dx;
							
						}
						//after collision, go back to last non-colliding place
						y-=dy;
						x-=dx;
						
						//round out x and y
						x=(int)x;
						y=(int)y;
						
						// if there is an obstacle in the way, stop motion, otherwise continue fluidly
						if(collisionCheck((int)x,(int)y+1,width,height)){
						vspeed = 0;
						}
						if(collisionCheck((int)x,(int)y-1,width,height)){
							vspeed = 0;	
							}
						if(collisionCheck((int)x+1,(int)y,width,height)){
						hspeed = 0;
						}
						if(collisionCheck((int)x-1,(int)y,width,height)){
							hspeed = 0;
							}
						
				}
			}
		}
		//update gravity
		gravity = getGravity();
	}
	
	public boolean checkPlatform(){
		//checks if on a platform using gravity
		if (gravity==0){
			return true;
		}
		return false;
	}

	private int getGravity(){
		for(CollisionCell c:cells){
		for(Entity e:c.staticContents){
			if(new Rectangle((int)x,(int)(y+height),width,1 ).intersects(e.getX(),e.getY(),e.getWidth(),e.getHeight())){
				return  0;
			}	
		}
		}
		return GRAVITY;
	}
	
	private boolean isEmpty(){
		for(CollisionCell c:cells){
			for(Entity e:c.staticContents){
					if(collided(e)){
						return false;
					}
				}
			}
		return true;
	}
	
	private boolean collisionCheck(int x,int y,int width,int height){
		for(CollisionCell c:cells){
			for(Entity e:c.staticContents){
				if(new Rectangle(x,y,width,height ).intersects(e.getX(),e.getY(),e.getWidth(),e.getHeight())){
					return true;

				}	
			}
			}
		return false;
	}

}

