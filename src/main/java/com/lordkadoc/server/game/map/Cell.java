package com.lordkadoc.server.game.map;

import com.badlogic.ashley.core.Entity;

public class Cell {
	
	private int x;
	
	private int y;
	
	private Entity entity;
	
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

	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public boolean isEmpty() {
		return this.entity == null;
	}
	
}
