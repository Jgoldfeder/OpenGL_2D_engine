package entities;

import java.awt.Rectangle;

public interface Entity {

	//render the image for the entity
	public void draw();
	//get position
	public double getX();
	public double getY();
	//set position
	public void setPosition(int x, int y);
	public void setX(int x);
	public void setY(int y);
	//get dimensions
	public int getWidth();
	public int getHeight();
	//set dimensions
	public void setDimensions(int width, int height);
	public void setWidth(int width);
	public void setHeight(int height);
	//collision detection
	public boolean collided(Entity other);
	public Rectangle getBoundingBox();
	
}
