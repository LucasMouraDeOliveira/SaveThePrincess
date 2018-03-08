package com.lordkadoc.server.game.engine.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.lordkadoc.server.game.engine.component.MovementComponent;
import com.lordkadoc.server.game.engine.component.PositionComponent;
import com.lordkadoc.server.game.engine.component.SpeedComponent;

public class MovementSystem extends EntitySystem {
	
	private ImmutableArray<Entity> entities;
	
	@Override
	public void addedToEngine(Engine engine) {
		this.entities = engine.getEntitiesFor(Family.all(PositionComponent.class, MovementComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		
		PositionComponent entityPosition;
		MovementComponent entityMovement;
		SpeedComponent speedComponent;
		for(Entity entity : entities) {
			entityPosition = Mapper.positionMapper.get(entity);
			entityMovement = Mapper.movementMapper.get(entity);
			speedComponent = Mapper.speedMapper.get(entity);
			
			double movementX = 0;
			double movementY = 0;
			double speed = speedComponent.getSpeed();
			if(entityMovement.isMoving(MovementComponent.NORTH)) {
				movementY-=speed;
			}
			if(entityMovement.isMoving(MovementComponent.EAST)) {
				movementX+=speed;
			}
			if(entityMovement.isMoving(MovementComponent.SOUTH)) {
				movementY+=speed;
			}
			if(entityMovement.isMoving(MovementComponent.WEST)) {
				movementX-=speed;
			}
			
			entityPosition.setX(entityPosition.getX()+movementX);
			entityPosition.setY(entityPosition.getY()+movementY);
			
			entityMovement.clear();
		}
	}

}
