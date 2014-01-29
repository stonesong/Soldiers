package soldiers.entity;


import java.awt.Canvas;
import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

public abstract class SoldierAbstract extends GameMovable implements Drawable, GameEntity,
Overlappable, Soldier{
	
	protected String name;
	protected float healthPoints;
	protected float force;
	protected static DrawableImage image = null;
	protected boolean movable = true;
	protected boolean active = true;


	public SoldierAbstract(String nom, float healthPoints, float force, Canvas defaultCanvas) {
		
		this.name = nom;
		this.healthPoints = healthPoints;
		this.force = force;
	}

	public String getName() {
		return name;
	}

	public float getHealthPoints() {
		return healthPoints;
	}

	public boolean alive() {
		return getHealthPoints() > 0;
	}

	public boolean parry(float force) { //default: no parry effect
		healthPoints = (getHealthPoints() > force) ? 
				               getHealthPoints() - force : 0;
	    return healthPoints > 0;
	}

	public float strike() {
		return alive() ? force : 0; 
	} 
	
	
}
