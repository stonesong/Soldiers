package soldiers.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

public class Health implements Drawable, GameEntity,
Overlappable{

	protected static DrawableImage image = null;
	protected Point position;
	protected boolean movable = true;
	protected boolean active = true;
	public static final int RENDERING_SIZE = 16;
	protected float healthPower;
	
public Health(Canvas defaultCanvas, Point pos){
	image = new DrawableImage("images/health.gif", defaultCanvas);
	position = pos;

	}


public Point getPosition() {
	return position;
}

public void draw(Graphics g) {
	g.drawImage(image.getImage(), (int) getPosition().getX(),
			(int) getPosition().getY(), RENDERING_SIZE, RENDERING_SIZE,
			null);

}


public Rectangle getBoundingBox() {
	return (new Rectangle((int) position.getX(), (int) position.getY(), RENDERING_SIZE, RENDERING_SIZE));
}

	
}
