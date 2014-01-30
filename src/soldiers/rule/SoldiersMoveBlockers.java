package soldiers.rule;

import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;
import soldiers.entity.ArmedUnitSquad;
import soldiers.entity.Wall;

public class SoldiersMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {

	public void moveBlockerRule(ArmedUnitSquad a, Wall w) throws IllegalMoveException {
			throw new IllegalMoveException();
		}
	}

