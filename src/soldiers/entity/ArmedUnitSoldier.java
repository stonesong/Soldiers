package soldiers.entity;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import soldiers.utils.*;


public class ArmedUnitSoldier extends ObservableAbstract<ArmedUnit> implements
		ArmedUnit, Drawable, GameEntity, Overlappable, Soldier {
	public SoldierAbstract soldier;
	protected List<String> equipments = new ArrayList<String>();
	protected AgeFactory age;

	public ArmedUnitSoldier(AgeFactory factory, String soldatType, String name, Canvas defaultCanvas) {		
		this.age = factory;
		String methodName = "get" + soldatType + "Soldier";
		try {
			Method method = factory.getClass().getMethod(methodName,
					String.class, Canvas.class);
 			soldier = (SoldierAbstract) method.invoke(factory, name, defaultCanvas);
		} catch (Exception e) {
			throw new UnknownSoldierTypeException("Unknown soldier type"
					+ e.toString());
		}
	}

	public void addEquipment(String equipmentType, Canvas defaultCanvas) {
		if (alive()) { // XXX "else" not treated
			if (equipments.contains(equipmentType)) {
				return; // decoration not applied
			} else {
				String methodName = "get" + equipmentType + "Weapon";
				try {
					Method method = age.getClass().getMethod(methodName,
							Soldier.class, Canvas.class);
					soldier =  (SoldierAbstract) method.invoke(age, soldier, defaultCanvas);
					equipments.add(equipmentType);
				} catch (Exception e) {
					throw new RuntimeException("Unknown equipment type "
							+ e.toString());
				}
			}
		}
	}

	public String getName() {
		return soldier.getName();
	}

	public float getHealthPoints() {
		return soldier.getHealthPoints();
	}

	public AgeFactory getAge() {
		return age;
	}

	public boolean alive() {
		return soldier.alive();
	}

	public void heal() {
		soldier.heal();
	}

	public float strike() {
		return soldier.strike();
	}

	public boolean parry(float force) {
		notify(this);
		return soldier.parry(force);
	}

	public void accept(VisitorClassicForArmedUnit v) {
		v.visit(this);
	}

	public <T> T accept(VisitorFunForArmedUnit<T> v) {
		return v.visit(this);
	}

	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}


