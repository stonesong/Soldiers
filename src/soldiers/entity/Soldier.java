package soldiers.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Soldier {
	public String getName();
	public float getHealthPoints();
	public boolean alive();
	public void heal();
	
	/**
	 * @param force the force of the attack the receiver has to parry
	 * @return true iff the receiver is still alive after the attack
	 */
	public boolean parry(float force);
	public float strike();
	public void draw(Graphics g);
	public Rectangle getBoundingBox();
}
