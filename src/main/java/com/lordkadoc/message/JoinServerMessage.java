package com.lordkadoc.message;

public class JoinServerMessage {
	
	private String playerToken;
	
	private int serverId;
	
	public String getPlayerToken() {
		return playerToken;
	}
	
	public void setPlayerToken(String playerToken) {
		this.playerToken= playerToken;
	}
	
	public int getServerId() {
		return serverId;
	}
	
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

}
