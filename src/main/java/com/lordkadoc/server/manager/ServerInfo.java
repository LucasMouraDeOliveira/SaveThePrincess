package com.lordkadoc.server.manager;

public class ServerInfo {
	
	private final String name;
	
	private final int playerCount;
	
	private final int maxPlayers;
	
	public ServerInfo(String name, int playerCount, int maxPlayers) {
		this.name = name;
		this.playerCount = playerCount;
		this.maxPlayers = maxPlayers;
	}

	public String getName() {
		return name;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
}
