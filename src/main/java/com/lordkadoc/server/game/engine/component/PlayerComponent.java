package com.lordkadoc.server.game.engine.component;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
	
	private final String name;
	
	public PlayerComponent(String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
