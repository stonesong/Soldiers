package soldiers.weapon;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;

import soldiers.entity.Soldier;

public class SoldierWithSword extends SoldierArmed<Sword> {
	
	public boolean belongToArmy;

	public SoldierWithSword(Soldier s, boolean b, Canvas canvas) {
		super(s, new Sword(), canvas);
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
