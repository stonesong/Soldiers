package soldiers.utils;

import java.awt.Canvas;

import soldiers.entity.Soldier;
import soldiers.entity.SoldierAbstract;

public interface AgeFactory {
	SoldierAbstract getSimpleSoldier(String name, Canvas defaultCanvas);
	SoldierAbstract getComplexSoldier(String name, Canvas defaultCanvas);
	Soldier getDefensiveWeapon(Soldier s, Canvas defaultCanvas);
	Soldier getOffensiveWeapon(Soldier s, Canvas defaultCanvas);
}
