package rendering;



import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;


import entities.AbstractDynamicEntity;
import entities.Block;
import entities.Entity;
import entities.Player;
public class Renderer {
	
  	
	private static CollisionCell[][] grid = new CollisionCell[4][3];
	private static ArrayList<CollisionCell> cellList = new ArrayList<CollisionCell>();
	private static ArrayList<AbstractDynamicEntity> dynamicEntities = new ArrayList<AbstractDynamicEntity>();
	public static Screen[][] SCREEN = new Screen[2][2];
	public Renderer(){
		
		
		for(int x = 0; x<4;x++) {
			for (int y = 0;y<3;y++){
				grid[x][y] = new CollisionCell();
				cellList.add(grid[x][y]);
			}
		}
		//init display
		try{
			Display.setDisplayMode(new DisplayMode(640,480));
			Display.setFullscreen(true);
			Display.setTitle("Platformer");	
			Display.create();
			
			}
			catch (LWJGLException e) {
				e.printStackTrace();
			}
		
		
		//init opengl
		glViewport(0,0,640,480);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,640,480,0,1,-1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		
		for(int x = 0; x<2;x++) {
			for (int y = 0;y<2;y++){
				SCREEN[x][y] = new Screen();
			}
		}
		
		
	}
	public  static CollisionCell getGrid(int x, int y){
		
		return grid[x][y];
	}
	
	private static void draw(){
		for(AbstractDynamicEntity e:dynamicEntities){
			e.draw();
		}
	}
	public static void addDynamic(AbstractDynamicEntity e){
		dynamicEntities.add(e);
	}
	
	public static void main(String args[]) {
		new Renderer();
		TileMap tileMap = new TileMap();
	    new Block(64,128,32,32);
	    new Block(32,128,32,32);
	    new Block(0,128 ,32,32);
	    Player player = new Player(0,0,32,32);
	    

		//Game loop
	while(!Display.isCloseRequested()){
		//get delta
				
	    //RENDER
	    glClear(GL_COLOR_BUFFER_BIT);
	    
	    SCREEN[0][0].draw();
	    draw();
	    //player.draw();
	    
	 

		Display.update();
		Display.sync(60);
		
		//input
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				player.setHspeed(-5);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				player.setHspeed(5);
				//Scroll.scrollH(-5);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				//test if player is on a platform
				if(player.checkPlatform()){
				    player.setVspeed(-18);	
				}	
			}
			if(Mouse.isButtonDown(0)){
				new Block((int)Math.floor((Mouse.getX()-Scroll.getHScroll())/32)*32,(int)Math.floor(((480-Mouse.getY()-Scroll.getVScroll()))/32)*32,32,32);
			}

		}
	}
}
