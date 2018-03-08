package com.lordkadoc.server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.json.JsonObject;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.lordkadoc.server.game.engine.component.InputComponent;
import com.lordkadoc.server.game.engine.component.MovementComponent;
import com.lordkadoc.server.game.engine.system.InputSystem;
import com.lordkadoc.server.game.engine.system.MovementSystem;
import com.lordkadoc.server.game.factory.EntityFactory;
import com.lordkadoc.server.game.loop.GameLoop;
import com.lordkadoc.server.game.map.GameMap;

public class Game extends Observable {
	
	private GameMap gameMap;
	
	private GameLoop gameLoop;
	
	private List<Entity> updatableEntities;
	
	private Map<String, Entity> players;
	
	private Engine engine;
	
	public Game() {
		this.updatableEntities = new ArrayList<Entity>();
		this.players = new HashMap<String, Entity>();
		this.gameMap = new GameMap(10, 10);
	}
	
	private void initEngine() {
		this.engine = new Engine();
		this.engine.addSystem(new InputSystem());
		this.engine.addSystem(new MovementSystem());
		for(Entity entity : this.updatableEntities) {
			this.engine.addEntity(entity);
		}
	}
	
	private void startGameLoop() {
		this.gameLoop = new GameLoop(this, 50);
		new Thread(this.gameLoop).start();
	}
	
	public void startGame() {
		this.initEngine();
		this.startGameLoop();
	}
	
	public boolean addPlayer(String playerName) {
		Entity player = this.players.get(playerName);
		if(player != null) {
			return false;
		}
		player = EntityFactory.createPlayer(100, 100);
		this.players.put(playerName, player);
		this.addEntity(player);
		return true;
	}
	
	public void processPlayerInput(String playerName, JsonObject inputData) {
		Entity player = this.players.get(playerName);
		if(player == null){
			return;
		}
		JsonObject mouse = inputData.getJsonObject("mouse");
		JsonObject keyboard = inputData.getJsonObject("keyboard");
		InputComponent inputComponent = new InputComponent();
		inputComponent.setMousePressed(mouse.getBoolean("left"));
		inputComponent.setKeyboard(MovementComponent.NORTH, keyboard.getBoolean("up"));
		inputComponent.setKeyboard(MovementComponent.SOUTH, keyboard.getBoolean("bottom"));
		inputComponent.setKeyboard(MovementComponent.WEST, keyboard.getBoolean("left"));
		inputComponent.setKeyboard(MovementComponent.EAST, keyboard.getBoolean("right"));
		inputComponent.setMousePressed(mouse.getBoolean("left"));
		
		player.add(inputComponent);
	}
	
	public void addEntity(Entity entity) {
		this.updatableEntities.add(entity);
	}
	
	public void update(long delay) {
		this.engine.update(delay);
		this.notifyObservers();
	}
	
	@Override
	public void notifyObservers() {
		this.setChanged();
		super.notifyObservers();
	}
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}
	
	public List<Entity> getEntities() {
		return updatableEntities;
	}

}
