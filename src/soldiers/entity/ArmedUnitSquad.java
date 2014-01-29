package soldiers.entity;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import soldiers.utils.AgeFactory;
import soldiers.utils.ObservableAbstract;
import soldiers.utils.VisitorClassicForArmedUnit;
import soldiers.utils.VisitorFunForArmedUnit;

public class ArmedUnitSquad extends GameMovable implements Drawable, GameEntity,
Overlappable, ArmedUnit {
	public List<ArmedUnit> armedUnitList = new ArrayList<ArmedUnit>();
	protected String name;
	protected AgeFactory age;
	protected static DrawableImage image = null;
	protected boolean movable = true;
	protected boolean active = true;
	protected boolean hasSword = false;
	protected boolean hasShield = false;
	protected boolean hasBoth = false;
	protected final SpriteManagerDefaultImpl spriteManager;
	public static final int RENDERING_SIZE = 32;
	public boolean isStriking = false;

	public ArmedUnitSquad(AgeFactory factory, String name, Canvas defaultCanvas, String spriteName) {
		this.age = factory;
		this.name = name;
		spriteManager = new SpriteManagerDefaultImpl(spriteName,
				defaultCanvas, RENDERING_SIZE, 4);
		spriteManager.setTypes(
				"down",
				"left",
				"right",
				"up",
				"down_sword",
				"left_sword",
				"right_sword",
				"up_sword",
				"down_shield",
				"left_shield",
				"right_shield",
				"up_shield",
				"down_both",
				"left_both",
				"right_both",
				"up_both",
				"explosion");
	}


	public String getName() {
		return name;
	}

	public void addUnit(ArmedUnit s) {
		//if (s.getAge() == age) // historical coherence checked
		armedUnitList.add(s);
		//else
		//throw new BadAgeException();
	}

	public float getHealthPoints() {
		float points = 0;
		for (ArmedUnit s : armedUnitList) {
			points += s.getHealthPoints();
		}
		return points;
	}

	public AgeFactory getAge() {
		return age;
	}

	public boolean alive() {
		boolean alive = false;
		for (ArmedUnit s : armedUnitList) {
			alive = alive || (s.alive());
		}
		return alive;
	}

	public int getStillAliveSoldiers() {
		int stillAlive = 0;
		for (ArmedUnit s : armedUnitList) {
			if (s.alive())
				stillAlive += 1;
		}
		return stillAlive;
	}

	public void heal() {
		for (ArmedUnit s : armedUnitList) {
			s.heal();
		}
	}

	public void addEquipment(String equipmentType, Canvas defaultCanvas) {
		if(equipmentType.equals("Offensive"))
			hasSword = true;
		else if(equipmentType.equals("Defensive"))
			hasShield = true;
		if(hasSword == true && hasShield == true)
			hasBoth = true;
		if (alive()) {
			// Every soldier of a unit gets the same equipment
			for (ArmedUnit s : armedUnitList) {
				if (s.alive())
					s.addEquipment(equipmentType, defaultCanvas);
			}
		}
	}

	public boolean parry(float force) {
		boolean result = false;
		if (alive()) {
			float forcePart = force / getStillAliveSoldiers();
			// Each alive soldier takes an equal part in each strike
			for (ArmedUnit s : armedUnitList) {
				result = (s.parry(forcePart)) || result;
			}
		}
		//notify(this);
		return result;
	}

	public float strike() {
		float result = 0;
		isStriking = true;
		if (alive()) {
			for (ArmedUnit s : armedUnitList) {
				result += s.strike();
			}
		}
		return result;
	}

	public void accept(VisitorClassicForArmedUnit v) {
		v.visit(this);
		for (ArmedUnit s : armedUnitList) {
			s.accept(v);
		}
	}

	public <T> T accept(VisitorFunForArmedUnit<T> v) {
		T result = v.visit(this);
		for (ArmedUnit s : armedUnitList) {
			result = v.compos(result, s.accept(v));
		}
		return result;
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		// TODO Auto-generated method stub

	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		if(isStriking == true){
			spriteType += "explosion";
		}
		
		else if(hasBoth == true){
			if (tmp.getX() == -1) {
				spriteType += "left_both";
			} else if (tmp.getY() == 1) {
				spriteType += "down_both";
			} else if (tmp.getY() == -1) {
				spriteType += "up_both";
			} else {
				spriteType += "right_both";
			}
		}

		else if(hasSword == true){
			if (tmp.getX() == -1) {
				spriteType += "left_sword";
			} else if (tmp.getY() == 1) {
				spriteType += "down_sword";
			} else if (tmp.getY() == -1) {
				spriteType += "up_sword";
			} else {
				spriteType += "right_sword";
			}
		}

		else if (hasShield == true){
			if (tmp.getX() == -1) {
				spriteType += "left_shield";
			} else if (tmp.getY() == 1) {
				spriteType += "down_shield";
			} else if (tmp.getY() == -1) {
				spriteType += "up_shield";
			} else {
				spriteType += "right_shield";
			}
		}

		else if (tmp.getX() == -1) {
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
		
		isStriking = false;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}

}
