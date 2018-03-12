package com.lordkadoc.server.game.factory;

import com.lordkadoc.server.game.map.Cell;
import com.lordkadoc.server.game.map.GameMap;

public class GameMapFactory {
	
	private static final int WALL = 1;
	
	public static GameMap createEmptyGameMap(int width, int height) {
		GameMap gameMap = new GameMap(width, height);
		Cell[][] cells = gameMap.getCells();
		Cell cell;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cell = cells[i][j];
				if(i == 0 || i == cells.length-1 || j == 0 || j == cells[i].length-1) {
					cell.setEntity(EntityFactory.createSolidEntity(WALL));
				}
			}
		}
		
		return gameMap;
	}

}
