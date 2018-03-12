package com.lordkadoc.server.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.lordkadoc.server.game.Game;
import com.lordkadoc.server.game.factory.UpdateFactory;

public class Server implements Observer {
	
	private String name;
	
	private int maxPlayers;
	
	private Game game;
	
	private UpdateFactory updateFactory;
	
	private Map<WebSocketSession, String> players;
	
	public Server(String name, int maxPlayers) {
		this.name = name;
		this.maxPlayers = maxPlayers;
		this.players = new HashMap<WebSocketSession, String>();
		this.game = new Game();
		this.game.addObserver(this);
		this.updateFactory = new UpdateFactory(game);
	}

	public boolean addPlayer(WebSocketSession session, String playerName) {
		
		//On vérifie que la limite de joueurs n'est pas atteinte
		if(this.maxPlayers <= this.players.size()) {
			return false;
		}
		
		//On essaie d'ajouter le joueur à la partie
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
		
		//On récupère les données
		Map<String, JsonObject> updates = this.updateFactory.getUpdateForPlayers(new ArrayList<String>(this.players.values()));
		JsonObject update;
		
		//On envoie les données aux clients
		for(Map.Entry<WebSocketSession, String> sessionEntry : this.players.entrySet()) {
			update = updates.get(sessionEntry.getValue());
			if(update != null) {
				this.sendMessageAsynchronously(sessionEntry.getKey(), "update", update);
			}
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
	
	public String getName() {
		return name;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
}
