package com.lordkadoc.server.game.engine.component;

import com.badlogic.ashley.core.Component;

public class SpeedComponent implements Component {
	
	private double speed;
	
	public SpeedComponent(double speed) {
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
