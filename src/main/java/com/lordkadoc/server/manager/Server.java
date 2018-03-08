package com.lordkadoc.server.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.badlogic.ashley.core.Entity;
import com.lordkadoc.server.game.Game;
import com.lordkadoc.server.game.engine.component.PositionComponent;
import com.lordkadoc.server.game.engine.system.Mapper;
import com.lordkadoc.server.game.map.Cell;
import com.lordkadoc.server.game.map.GameMap;

public class Server implements Observer {
	
	private String name;
	
	private Game game;
	
	private Map<WebSocketSession, String> players;
	
	public Server(String name) {
		this.name = name;
		this.players = new HashMap<WebSocketSession, String>();
		this.game = new Game();
		this.game.addObserver(this);
	}

	public boolean addPlayer(WebSocketSession session, String playerName) {
		boolean playerAccepted = this.game.addPlayer(playerName);
		if(!playerAccepted) {
			return false;
		}
		this.players.put(session, playerName);
		return true;
	}
	
	public void startGame() {
		this.game.startGame();
	}

	@Override
	public void update(Observable o, Object arg) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder entitiesBuilder = Json.createArrayBuilder();
		JsonArrayBuilder cellsBuilder = Json.createArrayBuilder();
		JsonObjectBuilder entityBuilder;
		JsonObjectBuilder cellBuilder;
		List<Entity> entities = this.game.getEntities();
		PositionComponent positionComponent;
		for(Entity entity : entities) {
			positionComponent = Mapper.positionMapper.get(entity);
			entityBuilder = Json.createObjectBuilder();
			entityBuilder.add("x", positionComponent.getX());
			entityBuilder.add("y", positionComponent.getY());
			entitiesBuilder.add(entityBuilder.build());
		}
		GameMap gameMap = this.game.getGameMap();
		Cell[][] cells = gameMap.getCells();
		Cell cell;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cell = cells[i][j];
				cellBuilder = Json.createObjectBuilder();
				cellBuilder.add("x", cell.getX());
				cellBuilder.add("y", cell.getY());
				cellsBuilder.add(cellBuilder.build());
			}
		}
		builder.add("map", cellsBuilder.build());
		builder.add("entities", entitiesBuilder.build());
		for(WebSocketSession session : this.players.keySet()) {
			this.sendMessageAsynchronously(session, "update", builder.build());
		}
	}
	
	public void sendMessageAsynchronously(WebSocketSession session, String type, JsonObject message) {
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObject json = factory.createObjectBuilder().add("type", type).add("data", message).build();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				if(session.isOpen()){
					synchronized(session) {
						try {
							session.sendMessage(new TextMessage(json.toString()));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		new Thread(r).start();
	}

	public void sendUpdate(WebSocketSession session, JsonObject data) {
		String playerName = this.players.get(session);
		if(playerName != null) {
			this.game.processPlayerInput(playerName, data);
		}
	}
	
}
