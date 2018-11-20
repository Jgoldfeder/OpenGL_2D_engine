package entities;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

import rendering.Renderer;
import rendering.TexLoader;

public class Block extends AbstractStaticEntity{
	
	private static Texture texture = TexLoader.loadTexture("png","Block");
    	  public Block(int x,int y,int width, int height){
    		  super( x, y, width, height,texture);
    		
    		  
    }
    	  public void draw(){
    		  texture.bind();
    	
    		  glCallList(displayListHandle);
    			
    	  }
}


