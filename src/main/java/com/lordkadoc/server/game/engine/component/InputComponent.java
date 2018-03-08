package com.lordkadoc.server.game.engine.component;

import com.badlogic.ashley.core.Component;

public class InputComponent implements Component {
	
	private boolean keyboard[];
	
	private int mouseX;
	
	private int mouseY;
	
	private boolean mousePressed;
	
	public InputComponent() {
		this.keyboard = new boolean[4];
		this.mouseX = 0;
		this.mouseY = 0;
		this.mousePressed = false;
	}
	
	public void setKeyboard(int key, boolean activated) {
		this.keyboard[key] = activated;
	}
	
	public boolean isPressed(int key) {
		return this.keyboard[key];
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

}
