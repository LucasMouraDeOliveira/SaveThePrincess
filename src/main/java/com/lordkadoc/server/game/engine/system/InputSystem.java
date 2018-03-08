package com.lordkadoc.server.game.engine.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.lordkadoc.server.game.engine.component.InputComponent;
import com.lordkadoc.server.game.engine.component.MovementComponent;

public class InputSystem extends EntitySystem {
	
	private ImmutableArray<Entity> entities;
	
	@Override
	public void addedToEngine(Engine engine) {
		this.entities = engine.getEntitiesFor(Family.all(InputComponent.class, MovementComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		
		MovementComponent entityMovement;
		InputComponent entityInput;
		for(Entity entity : entities) {
			entityMovement = Mapper.movementMapper.get(entity);
			entityInput = Mapper.inputMapper.get(entity);
			entityMovement.setDirection(MovementComponent.NORTH, entityInput.isPressed(MovementComponent.NORTH));
			entityMovement.setDirection(MovementComponent.SOUTH, entityInput.isPressed(MovementComponent.SOUTH));
			entityMovement.setDirection(MovementComponent.EAST, entityInput.isPressed(MovementComponent.EAST));
			entityMovement.setDirection(MovementComponent.WEST, entityInput.isPressed(MovementComponent.WEST));
		}
		
	}

}
