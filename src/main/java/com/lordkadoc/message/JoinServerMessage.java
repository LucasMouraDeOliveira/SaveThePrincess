package com.lordkadoc.message;

public class JoinServerMessage {
	
	private int playerId;
	
	private int serverId;
	
	public int getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public int getServerId() {
		return serverId;
	}
	
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

}
