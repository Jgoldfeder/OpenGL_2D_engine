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
import rendering.Scroll;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class AbstractStaticEntity implements Entity{

	//initialize variables

	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;
	protected Texture texture;
	protected int displayListHandle = 0;
	private Rectangle boundingBox = new Rectangle(x,y,width,height);
	@Override
	public void draw() {

		
	}

	public AbstractStaticEntity(int x,int y,int width, int height,Texture texture)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		 
		//make this entity part of the static entities of its collision cells
		addToCells();
		
		  
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
		boundingBox.setBounds(x,y,width,height);
		return boundingBox.intersects(other.getX(),other.getY(),other.getWidth(),other.getHeight());
	}

	@Override
	public Rectangle getBoundingBox() {

		return boundingBox;
	}
	private void addToCells(){
		//variable diff is increased by 1 each time the x's are different and y's are different. if it
		// equals 2 perform last condition, as this entity lies in 4 cells
		int diff = 0;
		Screen screen1 = Renderer.SCREEN[(int)Math.floor(x/640)][(int)Math.floor(y/480)];
		Screen screen2 = Renderer.SCREEN[(int)Math.floor((x+width-1)/640)][(int)Math.floor(y/480)];
		Screen screen3 = Renderer.SCREEN[(int)Math.floor(x/640)][(int)Math.floor((y+height-1)/480)];
		Screen screen4 = Renderer.SCREEN[(int)Math.floor((x+width-1)/640)][(int)Math.floor((y+height-1)/480)];
		
		screen1.getGrid((int)Math.floor(x/160),(int)Math.floor(y/160)).addStatic(this);
		if(Math.floor(x/160) != Math.floor((width+x-1)/160)){
			screen2.getGrid((int)Math.floor((x+width-1)/160),(int)Math.floor(y/160)).addStatic(this);
			diff++;
		}
		if(Math.floor(y/160) != Math.floor((height+y-1)/160)){
			screen3.getGrid((int)Math.floor(x/160),(int)Math.floor((y+height-1)/160)).addStatic(this);
			diff++;
		}
		if(diff==2){
			screen4.getGrid((int)Math.floor((x+width-1)/160),(int)Math.floor((y+height-1)/160)).addStatic(this);
		}
	
				

	}
	



}
