package soldiers;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.base.MoveStrategyRandom;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapProcessorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;

import myframework.MyGameLevelDefaultImpl;
import soldiers.entity.ArmedUnitSoldier;
import soldiers.entity.ArmedUnitSquad;
import soldiers.entity.Health;
import soldiers.entity.InfantryMan;
import soldiers.entity.Shield;
import soldiers.entity.SoldierAbstract;
import soldiers.entity.Sword;
import soldiers.entity.TeleportPairOfPoints;
import soldiers.entity.Wall;
import soldiers.rule.ArmyMovableDriver;
import soldiers.rule.SoldiersMoveBlockers;
import soldiers.rule.SoldiersOverlapRules;
import soldiers.utils.AgeFactory;
import soldiers.utils.MiddleAgeFactory;

public class GameLevelOne extends MyGameLevelDefaultImpl {
	Canvas canvas;

	//  5:empty; 1:Walls; 2:Neutral-Infantrymen; 3:Health; 6:sword; 7:shield;
	// Note: teleportation points are not indicated since they are defined by
	// directed pairs of positions.
	static int[][] tab = { 
		    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 2, 2, 2, 2, 2, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 3, 5, 5, 2, 2, 2, 2, 1, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 6, 7, 3, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 2, 2, 2, 2, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 1, 5, 5, 1, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 1 },
			{ 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 1, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 1, 1, 5, 5, 1 },
			{ 1, 2, 2, 2, 6, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2, 2, 2, 6, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 1, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 1, 5, 5, 1, 5, 5, 1, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 1, 5, 5, 5, 2, 2, 2, 2, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 1, 5, 5, 1, 3, 5, 5, 2, 2, 2, 2, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 5, 5, 5, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 1, 5, 5, 3, 1 },
			{ 1, 2, 2, 2, 6, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 2, 2, 2, 6, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 1, 5, 5, 1, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 7, 2, 2, 2, 5, 5, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 1, 2, 2, 2, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 5, 5, 5, 1, 5, 5, 1, 1, 1, 5, 5, 1, 5, 5, 1, 5, 7, 2, 2, 2, 1, 5, 2, 2, 2, 2, 5, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 1, 5, 5, 1, 1, 2, 2, 2, 1 },
			{ 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 7, 2, 2, 2, 1, 5, 5, 1, 1, 1, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 1, 5, 5, 1, 1, 2, 2, 2, 1 },
			{ 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 6, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5 },
			{ 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 1, 1, 1, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5 },
			{ 1, 2, 2, 2, 6, 5, 5, 1, 5, 5, 5, 1, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 2, 2, 2, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 1, 5, 5, 1, 1, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 2, 2, 2, 2, 1, 5, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 7, 2, 2, 2, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 2, 2, 2, 2, 1, 5, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 1, 5, 5, 1, 2, 2, 2, 2, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 1, 5, 5, 1, 2, 2, 2, 2, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 1, 1, 1, 1, 5, 5, 1, 1, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 1, 1, 5, 5, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 3, 5, 5, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 1, 5, 3, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 5, 5, 1, 2, 2, 2, 2, 5, 5, 5, 1, 5, 5, 1, 5, 5, 1, 1, 1, 5, 5, 1, 5, 5, 5, 5, 1, 5, 5, 1 },
			{ 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 1, 7, 5, 5, 1, 1, 5, 5, 5, 5, 5, 5, 1 },
			{ 1, 2, 2, 2, 6, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 1, 2, 2, 2, 6, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 1, 5, 5, 5, 1, 1, 5, 5, 5, 5, 5, 5, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 1, 1, 1, 1, 5, 5, 1, 2, 2, 2, 2, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 1, 5, 5, 5, 1, 1, 5, 5, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2, 2, 2, 2, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1 },
			{ 1, 2, 2, 2, 6, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 1, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 1, 5, 5, 1, 5, 5, 1, 1, 1, 5, 5, 1, 1, 5, 5, 1, 5, 5, 5, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 1, 1, 5, 5, 1, 5, 5, 5, 1 },
			{ 1, 2, 2, 2, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 3, 5, 5, 2, 2, 2, 2, 2, 5, 5, 5, 5, 5, 1, 6, 5, 5, 5, 5, 5, 5, 1, 1, 5, 5, 1, 5, 5, 3, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
	
	

	public static final int SPRITE_SIZE = 16;
	public static final int NUMBER_OF_SOLDIERS = 0;
	public static final int NUMBER_OF_ARMED_SOLDIERS = 0;
	public static final int SIZE_SQUAD_1 = 3;
	public static final int SIZE_SQUAD_2 = 6;

	private static final int NB_SQUADS = 12;
	
	@Override
	protected void init() {
		
	
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new SoldiersMoveBlockers());
		
		SoldiersOverlapRules overlapRules = new SoldiersOverlapRules(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE),
				new Point(14 * SPRITE_SIZE, 15 * SPRITE_SIZE), life[0], score[0], endOfGame, canvas);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < 31; ++i) {
			for (int j = 0; j < 56; ++j) {
				
				if (tab[i][j] == 1) {
					universe.addGameEntity(new Wall(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}

				if (tab[i][j] == 2) {
					SoldierAbstract mySoldier;
					mySoldier = new InfantryMan("John Doe", canvas, false);
					mySoldier.setPosition(new Point(j * SPRITE_SIZE, i * SPRITE_SIZE));
					universe.addGameEntity(mySoldier);
					(overlapRules).addSoldier(mySoldier);
				}

				if (tab[i][j] == 3) {
					universe.addGameEntity(new Health(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				
				if (tab[i][j] == 6) {
					universe.addGameEntity(new Sword(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				
				if (tab[i][j] == 7) {
					universe.addGameEntity(new Shield(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
			}

		}


		// Two teleport points definition and inclusion in the universe
		// (west side to east side)
		universe.addGameEntity(new TeleportPairOfPoints(new Point(-2 * SPRITE_SIZE, 13 * SPRITE_SIZE), new Point(
				55 * SPRITE_SIZE, 13 * SPRITE_SIZE)));

		// (east side to west side)
		universe.addGameEntity(new TeleportPairOfPoints(new Point(58 * SPRITE_SIZE, 13 * SPRITE_SIZE), new Point(
				0 * SPRITE_SIZE, 13 * SPRITE_SIZE)));


		


		
		AgeFactory age;
		age = new MiddleAgeFactory();
		
		ArmedUnitSquad myTeam;
		GameMovableDriverDefaultImpl armyDriv = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard();
		armyDriv.setStrategy(keyStr);
		armyDriv.setmoveBlockerChecker(moveBlockerChecker);
		myTeam = new ArmedUnitSquad(age, "My Team ", canvas, "images/soldier.gif");
		for(int j=0; j<SIZE_SQUAD_1; j++)
			myTeam.addUnit(new ArmedUnitSoldier(age, "Simple", "John" + j, canvas));
		canvas.addKeyListener(keyStr);
		myTeam.setDriver(armyDriv);
		myTeam.setPosition(new Point(14 * SPRITE_SIZE, 23 * SPRITE_SIZE));
		universe.addGameEntity(myTeam);
		(overlapRules).addSoldier(myTeam);

		ArmedUnitSquad enemyTeam;
		for(int i=0; i<NB_SQUADS; i++){
			GameMovableDriverDefaultImpl squadDriv = new ArmyMovableDriver();
			MoveStrategyRandom ranStr = new MoveStrategyRandom();
			squadDriv.setStrategy(ranStr);
			squadDriv.setmoveBlockerChecker(moveBlockerChecker);
			enemyTeam = new ArmedUnitSquad(age, "Team " + (i + 2) + " ", canvas, "images/soldier1.gif");
			for(int j=0; j<SIZE_SQUAD_2 ; j++)
				enemyTeam.addUnit(new ArmedUnitSoldier(age, "Complex", "Mike" + j, canvas));
			enemyTeam.setDriver(squadDriv);
			if (i%2 == 0)
				enemyTeam.setPosition(new Point(13 * SPRITE_SIZE, 15 * SPRITE_SIZE));
			else
				enemyTeam.setPosition(new Point(40 * SPRITE_SIZE, 15 * SPRITE_SIZE));
			universe.addGameEntity(enemyTeam);
			(overlapRules).addSoldier(enemyTeam);
		}
	}

	public GameLevelOne(Game g) {
		super(g);
		canvas = g.getCanvas();
	}

}
