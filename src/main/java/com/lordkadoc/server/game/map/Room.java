package com.lordkadoc.server.game.map;

public class Room {
	
	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	private Biome biome;
	
	public Room(int x, int y, int width, int height, Biome biome) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.biome = biome;
	}

	public void generate() {
		
	}

}
