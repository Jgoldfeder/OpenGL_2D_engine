package rendering;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.Texture;

public class TexLoader {

	public static Texture loadTexture(String format,String key) {
		try {
		       return TextureLoader.getTexture(format.toUpperCase(),new FileInputStream(new File("src/res/" + key + "." + format)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;

		
		
	}
	


}
