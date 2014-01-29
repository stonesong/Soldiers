package soldiers.utils;

import java.awt.Canvas;

import soldiers.entity.Horseman;
import soldiers.entity.InfantryMan;
import soldiers.entity.Soldier;
import soldiers.entity.SoldierAbstract;
import soldiers.weapon.*;

public class MiddleAgeFactory implements AgeFactory {
	public SoldierAbstract getSimpleSoldier(String name, Canvas defaultCanvas) {
		return new InfantryMan(name, defaultCanvas, true);
	}
 
	public SoldierAbstract getComplexSoldier(String name, Canvas defaultCanvas) {
		return new Horseman(name, defaultCanvas, true);
	}
 
	public Soldier getDefensiveWeapon(Soldier s, Canvas defaultCanvas) {
		return new SoldierWithShield(s, true, defaultCanvas);
	}
 
	public Soldier getOffensiveWeapon(Soldier s, Canvas defaultCanvas) {
		return new SoldierWithSword(s, true, defaultCanvas);
	}

}
