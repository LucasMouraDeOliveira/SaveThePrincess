package com.lordkadoc.server.game.loop;

import com.lordkadoc.server.game.Game;

public class GameLoop implements Runnable {
	
	private Game game;
	
	private long delay;
	
	public GameLoop(Game game, long delay) {
		this.game = game;
		this.delay = delay;
	}

	@Override
	public void run() {
		long updateTime = 0;
		long lastUpdateTime = this.delay;
		while(true) {
			try {
				updateTime = System.currentTimeMillis();
				this.game.update(lastUpdateTime);
				updateTime = (System.currentTimeMillis()-updateTime);
				lastUpdateTime = updateTime;
				Thread.sleep(Math.max(0, this.delay-updateTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
