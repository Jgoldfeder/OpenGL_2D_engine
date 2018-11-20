package entities;

import rendering.TexLoader;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glTranslatef;



public class Player extends AbstractDynamicEntity {
	public Player(int x, int y, int width, int height){
		super(x,y,width,height,TexLoader.loadTexture("png","Block"));
	}

	
	public void draw(){
		

		update();
		glTranslatef((float)x,(float)y,0);
		texture.bind();
		glCallList(displayListHandle);
		glTranslatef((float)-x,(float)-y,0);
		
				
		}
		
	
	
	


}
