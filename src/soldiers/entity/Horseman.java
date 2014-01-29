package soldiers.entity;

import gameframework.game.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Horseman extends SoldierAbstract {
	private static final int FORCE = 20;
	private static final int HEALTHPOINTS = 120;
	protected final SpriteManagerDefaultImpl spriteManager;
	public boolean belongToArmy;
	public static final int RENDERING_SIZE = 16;

	public Horseman(String name, Canvas defaultCanvas, boolean b) {
		super(name, HEALTHPOINTS, FORCE, defaultCanvas );
		spriteManager = new SpriteManagerDefaultImpl("images/ghost.gif",
				defaultCanvas, RENDERING_SIZE, 6);
		spriteManager.setTypes(
				"left",
				"right",
				"up",
				"down");
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
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType += "right";
		}

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}
	
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
	
}