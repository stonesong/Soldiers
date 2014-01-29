package soldiers.weapon;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;

import soldiers.entity.Soldier;

public class SoldierWithShield extends SoldierArmed<Shield> {

	public boolean belongToArmy;

	public SoldierWithShield(Soldier s, boolean b, Canvas canvas) {
		super(s, new Shield(), canvas);
		belongToArmy = b;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		// TODO Auto-generated method stub
		
	}


	
	
}
