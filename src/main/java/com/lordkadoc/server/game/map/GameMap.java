package com.lordkadoc.server.game.map;

public class GameMap {
	
	public final static int PIXEL_PER_CELL = 32;
	
	public final static int VIEW_RADIUS = 10 * PIXEL_PER_CELL;
	
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
	
	public Cell getCellFromPixelCoordinates(double x, double y) {
		int pX = (int)x;
		int pY = (int)y;
		pX/=PIXEL_PER_CELL;
		pY/=PIXEL_PER_CELL;
		if(pX >= 0 && pX < width && pY >= 0 && pY < height) {
			return this.cells[pX][pY];
		}
		return null;
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
