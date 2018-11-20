package rendering;
import java.util.ArrayList;

import entities.Entity;
import entities.AbstractStaticEntity;

public class CollisionCell {
	public ArrayList<Entity> staticContents = new ArrayList<Entity>(); 
	public ArrayList<Entity> dynamicContents = new ArrayList<Entity>(); 
	
	public void addStatic(Entity entity){
		staticContents.add(entity);
	}
	public void addDynamic(Entity entity){
		dynamicContents.add(entity);
	}
}

