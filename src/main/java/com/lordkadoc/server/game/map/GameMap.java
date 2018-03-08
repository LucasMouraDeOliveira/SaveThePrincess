package com.lordkadoc.server.game.map;

public class GameMap {
	
	private Cell[][] cells;
	
	private final int width;
	
	private final int height;
	
	public GameMap(int width, int height) {
		this.width = width;
		this.height = height;
		this.initCells();
	}
	
	private void initCells() {
		this.cells = new Cell[width][height];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				this.cells[i][j] = new Cell(i, j);
			}
		}
	}

	public Cell[][] getCells() {
		return cells;
	}
	
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
