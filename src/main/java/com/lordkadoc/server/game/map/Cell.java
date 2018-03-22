package com.lordkadoc.server.game.map;

public class Cell {
	
	private int x;
	
	private int y;
	
	private int floorTexture;
	
	private int wallTexture;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getFloorTexture() {
		return floorTexture;
	}
	
	public void setFloorTexture(int floorTexture) {
		this.floorTexture = floorTexture;
	}
	
	public int getWallTexture() {
		return wallTexture;
	}
	
	public void setWallTexture(int wallTexture) {
		this.wallTexture = wallTexture;
	}
	
	public boolean isEmpty() {
		return this.wallTexture == 0;
	}
	
}
