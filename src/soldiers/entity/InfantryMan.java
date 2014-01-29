package soldiers.entity;

import gameframework.game.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class InfantryMan extends SoldierAbstract {
	private static final int FORCE = 15;
	private static final int HEALTHPOINTS = 100;
	protected final SpriteManagerDefaultImpl spriteManager;
	public static final int RENDERING_SIZE = 16;
	public boolean belongToArmy;

	public InfantryMan(String nom, Canvas defaultCanvas, boolean b) {
		super(nom, HEALTHPOINTS, FORCE, defaultCanvas);
		spriteManager = new SpriteManagerDefaultImpl("images/neutral.gif",
				defaultCanvas, RENDERING_SIZE, 1);
		spriteManager.setTypes(
				"left");
		belongToArmy = b;
	}

	public void heal() { //XXX resurrection allowed
		healthPoints = HEALTHPOINTS;
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "left";
		} else if (tmp.getY() == -1) {
			spriteType += "left";
		} else {
			spriteType += "left";
		}

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}
	
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}

}		

