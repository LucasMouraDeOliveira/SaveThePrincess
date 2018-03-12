package com.lordkadoc.server.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.json.JsonObject;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.lordkadoc.server.game.engine.component.InputComponent;
import com.lordkadoc.server.game.engine.component.MovementComponent;
import com.lordkadoc.server.game.engine.component.PlayerComponent;
import com.lordkadoc.server.game.engine.system.Mapper;
import com.lordkadoc.server.game.engine.system.MovementSystem;
import com.lordkadoc.server.game.engine.system.PlayerInputSystem;
import com.lordkadoc.server.game.factory.EntityFactory;
import com.lordkadoc.server.game.factory.GameMapFactory;
import com.lordkadoc.server.game.loop.GameLoop;
import com.lordkadoc.server.game.map.GameMap;

public class Game extends Observable {
	
	private GameMap gameMap;
	
	private GameLoop gameLoop;
	
	private Map<String, Entity> players;
	
	private Engine engine;
	
	public Game() {
		this.players = new HashMap<String, Entity>();
		this.gameMap = GameMapFactory.createEmptyGameMap(10, 10);
	}
	
	private void initEngine() {
		this.engine = new Engine();
		this.engine.addSystem(new PlayerInputSystem());
		this.engine.addSystem(new MovementSystem());
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
		player = EntityFactory.createPlayer(playerName, 100, 100);
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
		InputComponent inputComponent = Mapper.inputMapper.get(player);
		inputComponent.setMousePressed(mouse.getBoolean("left"));
		inputComponent.setKeyboard(MovementComponent.NORTH, keyboard.getBoolean("up"));
		inputComponent.setKeyboard(MovementComponent.SOUTH, keyboard.getBoolean("bottom"));
		inputComponent.setKeyboard(MovementComponent.WEST, keyboard.getBoolean("left"));
		inputComponent.setKeyboard(MovementComponent.EAST, keyboard.getBoolean("right"));
		inputComponent.setMousePressed(mouse.getBoolean("left"));
	}
	
	public void addEntity(Entity entity) {
		this.engine.addEntity(entity);
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
	
	public ImmutableArray<Entity> getEntities() {
		return this.engine.getEntities();
	}
	
	public ImmutableArray<Entity> getPlayers() {
		return this.engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
	}

}
