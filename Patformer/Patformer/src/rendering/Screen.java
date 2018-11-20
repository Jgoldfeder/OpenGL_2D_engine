package rendering;

import java.util.ArrayList;
import entities.Entity;

public class Screen {
	private final int width = 640;
	private final int height = 480;
	private  CollisionCell[][] grid = new CollisionCell[4][3];
	private  ArrayList<CollisionCell> cellList = new ArrayList<CollisionCell>();
	private TileMap tileMap = new TileMap();
	public Screen(){
		
		
		
		for(int x = 0; x<4;x++) {
			for (int y = 0;y<3;y++){
				grid[x][y] = new CollisionCell();
				cellList.add(grid[x][y]);
			}
		}
	}

	public  void draw(){
		tileMap.draw();
		
		for(CollisionCell c:cellList){
			for(Entity e:c.staticContents){
				e.draw();
			}
			
			
		}
		
	}
	
    public   CollisionCell getGrid(int x, int y){
		
		return grid[x][y];
	}
}
