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
import com.lordkadoc.server.game.engine.component.TextureComponent;
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
		TextureComponent textureComponent;
		
		PositionComponent positionComponent;
		PlayerComponent playerComponent;
		ImmutableArray<Entity> players = this.game.getPlayers();
		for(Entity player : players) {
			positionComponent = Mapper.positionMapper.get(player);
			playerComponent = Mapper.playerMapper.get(player);
			playerBuilder = Json.createObjectBuilder();
			playerBuilder.add("x", positionComponent.getX());
			playerBuilder.add("y", positionComponent.getY());
			playerBuilder.add("name", playerComponent.getName());
			playersBuilder.add(playerBuilder.build());
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
				if(!cell.isEmpty()) {
					textureComponent = Mapper.textureMapper.get(cell.getEntity());
					cellBuilder.add("obstacle", textureComponent.getTextureId());
				}
				cellsBuilder.add(cellBuilder.build());
			}
		}
		builder.add("map", cellsBuilder.build());
		builder.add("players", playersBuilder.build());
		return builder.build();
	}

}
