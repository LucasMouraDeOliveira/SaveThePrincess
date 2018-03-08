package com.lordkadoc.server.game.factory;

import com.badlogic.ashley.core.Entity;
import com.lordkadoc.server.game.engine.component.MovementComponent;
import com.lordkadoc.server.game.engine.component.PositionComponent;
import com.lordkadoc.server.game.engine.component.SpeedComponent;

public class EntityFactory {
	
	public static Entity createPlayer(double x, double y) {
		Entity entity = new Entity();
		entity.add(new PositionComponent(x, y));
		entity.add(new SpeedComponent(10));
		entity.add(new MovementComponent());
		return entity;
	}

}
