package soldiers.rule;

import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;
import soldiers.entity.ArmedUnitSquad;
import soldiers.entity.Wall;

public class PacmanMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {

	public void moveBlockerRule(ArmedUnitSquad a, Wall w) throws IllegalMoveException {
		// The default case is when a ghost is active and not able to cross a
		// wall
		//if (a.isActive()) {
			throw new IllegalMoveException();
		//	// When a ghost is not active, it is able to cross a wall
		}
	}

