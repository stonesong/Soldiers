package soldiers;

import gameframework.game.GameLevel;

import java.util.ArrayList;

import myframework.MyGameDefaultImpl;

public class Main {
	public static void main(String[] args) {
		MyGameDefaultImpl g = new MyGameDefaultImpl();
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		levels.add(new GameLevelOne(g)); // only one level is available at this time
		
		g.setLevels(levels);
		g.start();
	}
}
