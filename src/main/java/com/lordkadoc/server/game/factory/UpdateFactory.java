package com.lordkadoc.server.game.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.lordkadoc.server.game.Game;
import com.lordkadoc.server.game.engine.component.PlayerComponent;
import com.lordkadoc.server.game.engine.component.PositionComponent;
import com.lordkadoc.server.game.engine.system.Mapper;
import com.lordkadoc.server.game.map.Cell;
import com.lordkadoc.server.game.map.GameMap;

public class UpdateFactory {
	
	private Game game;
	
	public UpdateFactory(Game game) {
		this.game = game;
	}
	
	/**
	 * Retourne une map contenant, pour chaque joueur, le message d'update à envoyer pour la frame courante
	 * @param playerNames la liste de joueurs à mettre à jour
	 * 
	 * @return une map avec l'update de chaque joueur
	 */
	public Map<String, JsonObject> getUpdateForPlayers(List<String> playerNames) {
		
		Map<String, JsonObject> updates = new HashMap<String, JsonObject>();
		JsonObject update;
		for(String playerName : playerNames) {
			update = this.getUpdateForPlayer(playerName);
			if(update != null) {
				updates.put(playerName, update);
			}
		}
		
		return updates;
	}
	
	private JsonObject getUpdateForPlayer(String playerName) {
		
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder playersBuilder = Json.createArrayBuilder();
		JsonArrayBuilder cellsBuilder = Json.createArrayBuilder();
		JsonObjectBuilder playerBuilder;
		JsonObjectBuilder cellBuilder;
		
		PositionComponent positionComponent;
		PlayerComponent playerComponent;
		ImmutableArray<Entity> players = this.game.getPlayers();
		double ownPlayerX = 0;
		double ownPlayerY = 0;
		for(Entity player : players) {
			playerComponent = Mapper.playerMapper.get(player);
			positionComponent = Mapper.positionMapper.get(player);
			playerBuilder = Json.createObjectBuilder();
			playerBuilder.add("x", positionComponent.getX());
			playerBuilder.add("y", positionComponent.getY());
			playerBuilder.add("name", playerComponent.getName());
			//Si c'est le joueur à qui on envoie l'update on ne le met pas dans cette liste
			if(playerComponent.getName().equals(playerName)) {
				ownPlayerX = positionComponent.getX();
				ownPlayerY = positionComponent.getY();
				builder.add("ownPlayer", playerBuilder.build());
			} else {
				playersBuilder.add(playerBuilder.build());
			}
		}
		
		GameMap gameMap = this.game.getGameMap();
		Cell[][] cells = gameMap.getCells();
		Cell cell;
		
		double minX = ownPlayerX - GameMap.VIEW_RADIUS;
		minX = Math.max(0, minX);
		double maxX = minX + GameMap.VIEW_RADIUS * 2;
		maxX = Math.min(cells.length*GameMap.PIXEL_PER_CELL, maxX);
		double minY = ownPlayerY - GameMap.VIEW_RADIUS;
		minY = Math.max(0, minY);
		double maxY = minY + GameMap.VIEW_RADIUS * 2;
		maxY = Math.min(cells[0].length*GameMap.PIXEL_PER_CELL, maxY);
		
		int minCellX = (int)(minX/GameMap.PIXEL_PER_CELL);
		int maxCellX = (int)(maxX/GameMap.PIXEL_PER_CELL);
		int minCellY = (int)(minY/GameMap.PIXEL_PER_CELL);
		int maxCellY = (int)(maxY/GameMap.PIXEL_PER_CELL);
		
		for (int i = minCellX; i < maxCellX; i++) {
			for (int j = minCellY; j < maxCellY; j++) {
				cell = cells[i][j];
				cellBuilder = Json.createObjectBuilder();
				cellBuilder.add("x", cell.getX());
				cellBuilder.add("y", cell.getY());
				//S'il y a un mur, on retourne sa texture
				if(!cell.isEmpty()) {
					cellBuilder.add("wall", cell.getWallTexture());
				}
				cellBuilder.add("floor", cell.getFloorTexture());
				cellsBuilder.add(cellBuilder.build());
			}
		}
		builder.add("map", cellsBuilder.build());
		builder.add("width", cells.length*GameMap.PIXEL_PER_CELL);
		builder.add("height", cells[0].length*GameMap.PIXEL_PER_CELL);
		builder.add("players", playersBuilder.build());
		return builder.build();
	}

}
