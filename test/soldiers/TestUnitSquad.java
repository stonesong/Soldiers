package soldiers;

import static org.junit.Assert.assertEquals;

import java.awt.Canvas;

import org.junit.Before;
import org.junit.Test;

import soldiers.entity.ArmedUnit;
import soldiers.entity.ArmedUnitSoldier;
import soldiers.entity.ArmedUnitSquad;
import soldiers.utils.AgeFactory;
import soldiers.utils.MiddleAgeFactory;

public class TestUnitSquad {
	ArmedUnitSquad squad;
	ArmedUnit sf1, sf2, sc1, sc2, s;
	AgeFactory age;
	Canvas canvas;
	
	@Before
	public void setUp() throws Exception {
		age = new MiddleAgeFactory();
		squad = new ArmedUnitSquad(age, "Lincoln", canvas, "images/soldier.gif");
		sf1 = new ArmedUnitSoldier(age, "Simple", "Gogol1", canvas);
		sf2 = new ArmedUnitSoldier(age, "Simple", "Gogol2", canvas);
		sc1 = new ArmedUnitSoldier(age, "Complex", "Sanchez1", canvas);
		sc2 = new ArmedUnitSoldier(age, "Complex", "Sanchez2", canvas);
		s = new ArmedUnitSoldier(age, "Complex", "St Georges", canvas);

		squad.addUnit(sf1);
		squad.addUnit(sf2);
		squad.addUnit(sc1);
		squad.addUnit(sc2);
	}

	@Test
	public void combats() {
		int i;
		s.addEquipment("Offensive", canvas);
 		for (i = 0; squad.parry(s.strike()); i++) {
 			;
 		}
 		assertEquals("Unexpected death of squad " + squad.getName(), i, 12);

		squad.heal();
		squad.addEquipment("Defensive", canvas);
		s.heal();

 		for (i = 0; squad.parry(s.strike()); i++) {
 			;
 		}
 		assertEquals("Unexpected death of squad " + squad.getName()
 				+ " with shield", i, 102);
	}
}
