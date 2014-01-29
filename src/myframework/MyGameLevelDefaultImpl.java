package myframework;

import gameframework.base.ObservableValue;
import gameframework.game.Game;
import gameframework.game.GameLevel;
import gameframework.game.GameUniverse;
import gameframework.game.GameUniverseViewPort;

import java.util.Date;

/**
 * To be implemented with respect to a specific game. Expected to initialize the
 * universe and the gameBoard
 */

public abstract class MyGameLevelDefaultImpl extends Thread implements GameLevel {
	private static final int MINIMUM_DELAY_BETWEEN_GAME_CYCLES = 100;
	protected GameUniverse universe;
	protected GameUniverseViewPort gameBoard;
	protected ObservableValue<Integer> score[];
	protected ObservableValue<Integer> life[];
	protected ObservableValue<Boolean> endOfGame;
	protected ObservableValue<String> timer_obs[]; /*AJOUT DU TIMER*/
	protected int minutes, seconds;

	protected Date timer;
	boolean stopGameLoop;
	protected final Game g;

	protected abstract void init();

	public MyGameLevelDefaultImpl(Game g) {
		this.g = g;
		this.score = g.score();
		this.life = g.life();
		this.timer_obs = ((MyGameDefaultImpl)g).timer_obs();
		this.timer = new Date();
		this.minutes = 2;
		this.seconds = 59;
	}

	@Override
	public void start() {
		endOfGame = g.endOfGame();
		init();
		super.start();
		try {
			super.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int i = 0;
		stopGameLoop = false;
		// main game loop :
		long start;
		while (!stopGameLoop && !this.isInterrupted()) {
			start = new Date().getTime();
			gameBoard.paint();
			universe.allOneStepMoves();
			universe.processAllOverlaps();
			if(i%9==0){
				timer_obs[0].setValue(String.valueOf(minutes)+":"+String.valueOf(seconds-1));
				seconds--;
				if(seconds == 0){
					minutes--;
					seconds = 59;
				}
				}
				if(minutes == 0 && seconds == 1)
					end();
			try {
				long sleepTime = MINIMUM_DELAY_BETWEEN_GAME_CYCLES
						- (new Date().getTime() - start);
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (Exception e) {
			}
			i++;
		}
	}

	public void end() {
		stopGameLoop = true;
	}

	protected void overlap_handler() {
	}

}
