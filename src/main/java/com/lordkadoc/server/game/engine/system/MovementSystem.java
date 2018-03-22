package com.lordkadoc.server.game.engine.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.lordkadoc.server.game.Game;
import com.lordkadoc.server.game.engine.component.MovementComponent;
import com.lordkadoc.server.game.engine.component.PositionComponent;
import com.lordkadoc.server.game.engine.component.SpeedComponent;
import com.lordkadoc.server.game.map.Cell;

public class MovementSystem extends EntitySystem {
	
	private ImmutableArray<Entity> entities;
	
	private Game game;
	
	public MovementSystem(Game game) {
		this.game = game;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.entities = engine.getEntitiesFor(Family.all(MovementComponent.class).get());
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
			
			double newPositionX = entityPosition.getX()+movementX;
			double newPositionY = entityPosition.getY()+movementY;
			
			if(isValidPosition(newPositionX, newPositionY)) {
				entityPosition.setX(entityPosition.getX()+movementX);
				entityPosition.setY(entityPosition.getY()+movementY);
			}
			
			entityMovement.clear();
		}
	}
	
	private boolean isValidPosition(double x, double y) {
		Cell cell = this.game.getGameMap().getCellFromPixelCoordinates(x, y);
		return cell != null && cell.isEmpty();
	}

}
