package com.lordkadoc.server.game.factory;

import com.lordkadoc.server.game.map.GameMap;

public class GameMapFactory {
	
	public static GameMap createEmptyGameMap(int width, int height) {
		return new GameMap(width, height);
	}

}
