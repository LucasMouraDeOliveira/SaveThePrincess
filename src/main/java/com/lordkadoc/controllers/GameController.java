package com.lordkadoc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import com.lordkadoc.WebSocketConfig;
import com.lordkadoc.game.ServerManager;
import com.lordkadoc.message.CreateServerMessage;
import com.lordkadoc.message.JoinServerMessage;
import com.lordkadoc.message.ServerCreationOkMessage;

@EnableScheduling
@Controller
public class GameController {
	
	private final long delay = 500;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private ServerManager serverManager;
	
	@MessageMapping("/join")
	public void joinGame(JoinServerMessage message) {
		if(message != null) {
			boolean success = this.serverManager.joinServer(message.getServerId(), message.getPlayerToken());
			if(success) {
				this.send("joinOk", null);
			}
		}
	}
	
	@MessageMapping("/create")
	public void createGame(CreateServerMessage message) {
		if(message != null) {
			Integer serverId = this.serverManager.createServer(message.getServerName());
			if(serverId != null) {
				ServerCreationOkMessage scom = new ServerCreationOkMessage();
				scom.setServerId(serverId);
				this.send("createOk", scom);
			}
		}
	}
	
	@MessageMapping("/update")
	public void updatePlayer(String data) {
		System.out.println("Data received from client : " + data);
	}
	
	private void send(String address, Object data) {
		template.convertAndSend(WebSocketConfig.CLIENT_MESSAGE_PREFIX + "/" + address, data);
	}
	
}
