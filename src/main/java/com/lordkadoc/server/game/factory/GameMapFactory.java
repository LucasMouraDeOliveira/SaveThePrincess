package com.lordkadoc.server.game.factory;

import com.lordkadoc.server.game.map.Biome;
import com.lordkadoc.server.game.map.Cell;
import com.lordkadoc.server.game.map.GameMap;
import com.lordkadoc.server.game.map.ImageID;
import com.lordkadoc.server.game.map.Room;

public class GameMapFactory {
	
	public static GameMap createEmptyGameMap(int width, int height) {
		GameMap gameMap = new GameMap(width, height);
		Cell[][] cells = gameMap.getCells();
		Cell cell;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cell = cells[i][j];
				//TODO changer la texture de sol pour avoir des carrés de 4x4
				cell.setFloorTexture(ImageID.WOODEN_FLOOR_TOP_LEFT);
				if(i == 0 || i == cells.length-1) {
					Cell c = getCell(cells, i, j-1); 
					if(c == null || c.getFloorTexture() != ImageID.STONE_WALL_VERTICAL_TOP) {
						cell.setWallTexture(ImageID.STONE_WALL_VERTICAL_TOP);
					} else {
						cell.setWallTexture(ImageID.STONE_WALL_VERTICAL_BOTTOM);
					}
				} else if(j == 0 || j == cells[i].length-1) {
					Cell c = getCell(cells, i-1, j); 
					if(c == null || c.getWallTexture() != ImageID.STONE_WALL_FRONT_LEFT) {
						cell.setWallTexture(ImageID.STONE_WALL_FRONT_LEFT);
					} else {
						cell.setWallTexture(ImageID.STONE_WALL_FRONT_RIGHT);
					}
				}
			}
		}
		
//		Room room = new Room(0, 0, width, height, Biome.KITCHEN);
//		room.generate();
		return gameMap;
	}
	
	private static Cell getCell(Cell[][] cells, int x, int y) {
		if(x < 0 || x >= cells.length || y < 0 || y >= cells[x].length) {
			return null;
		}
		return cells[x][y];
	}

}
