package com.lordkadoc.server.game.engine.component;

import com.badlogic.ashley.core.Component;

public class TextureComponent implements Component {
	
	private int textureId;
	
	public TextureComponent(int textureId) {
		this.textureId = textureId;
	}
	
	public int getTextureId() {
		return textureId;
	}
	
}
