package com.lordkadoc.server.game.factory;

import com.badlogic.ashley.core.Entity;
import com.lordkadoc.server.game.engine.component.InputComponent;
import com.lordkadoc.server.game.engine.component.MovementComponent;
import com.lordkadoc.server.game.engine.component.PlayerComponent;
import com.lordkadoc.server.game.engine.component.PositionComponent;
import com.lordkadoc.server.game.engine.component.SolidComponent;
import com.lordkadoc.server.game.engine.component.SpeedComponent;
import com.lordkadoc.server.game.engine.component.TextureComponent;

public class EntityFactory {
	
	public static Entity createSolidEntity(int textureId) {
		Entity entity = new Entity();
		entity.add(new TextureComponent(textureId));
		entity.add(new SolidComponent());
		return entity;
	}
	
	public static Entity createPlayer(String name, double x, double y) {
		Entity entity = new Entity();
		entity.add(new PlayerComponent(name));
		entity.add(new PositionComponent(x, y));
		entity.add(new SpeedComponent(10));
		entity.add(new MovementComponent());
		entity.add(new InputComponent());
		return entity;
	}

}
