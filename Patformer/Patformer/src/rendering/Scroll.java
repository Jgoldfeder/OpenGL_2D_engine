package rendering;

import static org.lwjgl.opengl.GL11.*;

public class Scroll {
	private static int vScroll;
	private static int hScroll;
	
	public static void scrollH(int scroll){
		hScroll+=scroll;
		glTranslatef(scroll,0,0);
	}
	public static void scrollV(int scroll){
		vScroll+=scroll;	
		glTranslatef(0,scroll,0);
	}
	public static int getVScroll(){
		return vScroll;
	}
	public static int getHScroll(){
		return hScroll;
	}

}
