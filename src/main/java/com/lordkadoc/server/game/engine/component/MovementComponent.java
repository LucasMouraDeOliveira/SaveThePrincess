package com.lordkadoc.server.game.engine.component;

import com.badlogic.ashley.core.Component;

public class MovementComponent implements Component {
	
	public static final int NORTH = 0;
	
	public static final int EAST = 1;
	
	public static final int SOUTH = 2;
	
	public static final int WEST = 3;
	
	private boolean[] direction;
	
	public MovementComponent() {
		this.direction = new boolean[4];
	}
	
	public void setDirection(int d, boolean activated) {
		this.direction[d] = activated;
	}
	
	public boolean isMoving(int d) {
		return this.direction[d];
	}

	public void clear() {
		for (int i = 0; i < direction.length; i++) {
			direction[i] = false;
		}
	}
	
}
