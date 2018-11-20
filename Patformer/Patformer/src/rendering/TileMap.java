package rendering;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class TileMap {
	Texture tileSet = TexLoader.loadTexture("png", "tiles");
	private Tile[][] map = new Tile[20][15]; 
	private int displayListHandle = 0;
	
	
	public void draw(){
		map[4][4]=new Tile(4,3);
		float top = 0;
  	    float left = 0;
	    float bottom = 0;
	    float right = 0;

		tileSet.bind();
	     
		 displayListHandle = glGenLists(1);
		  glNewList(displayListHandle,GL_COMPILE);
		  glBegin(GL_TRIANGLES);	 
			
		  for(int x = 0;x<20;x++){
			  for(int y= 0; y<15;y++){
				  if(map[x][y]!=null){
		        	 	 top = map[x][y].getY()*.0625f;
				  	     left = map[x][y].getX()*.0625f;
					     bottom = top+.0625f;
					     right = left+.0625f;
					     
					    		
					    x=(x*32);
					    y=(y*32);
					
			
					    glTexCoord2f(right, top);
					    glVertex2i((x)+32, y);
					    glTexCoord2f(left, top);
					    glVertex2i(x, y);
					    glTexCoord2f(left, bottom);
						glVertex2i(x, y+32);
						 
						glTexCoord2f(left, bottom);
						glVertex2i(x, y+32);
						glTexCoord2f(right, bottom);
						glVertex2i(x+32, y+32);
						glTexCoord2f(right, top);
						glVertex2i(x+32, y);      
						
						x= x/32;
						y= y/32;

				  } 
			  }
			  
		  }
		    			 
			
			
			
			glEnd();
			glEndList();
			glCallList(displayListHandle);
	}

}
