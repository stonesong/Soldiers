package soldiers;

import static org.junit.Assert.assertEquals;

import java.awt.Canvas;

import org.junit.Before;
import org.junit.Test;

import soldiers.entity.ArmedUnit;
import soldiers.entity.ArmedUnitSoldier;
import soldiers.entity.UnknownSoldierTypeException;
import soldiers.utils.AgeFactory;
import soldiers.utils.MiddleAgeFactory;


public class TestUnitSoldier {
	ArmedUnit sf, sc;
    AgeFactory age; 
    Canvas canvas;
    
	@Before
	public void setUp() throws Exception {
		age = new MiddleAgeFactory();
		sf = new ArmedUnitSoldier(age, "Simple", "Gogol", canvas);
		sc = new ArmedUnitSoldier(age, "Complex", "Sanchez", canvas);
	}

	@Test(expected = UnknownSoldierTypeException.class)
	public void combat() {
		int i;
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of squad " + sf.getName(), i, 4);

		sf.heal();
 		sf.addEquipment("Defensive", canvas);
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of  " + sf.getName() + " with shield", i, 9);

		sf.heal();
		sf.addEquipment("Offensive", canvas);
		for (i = 0; sf.parry(sc.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of " + sf.getName() + " with shield and sword", i, 11);
 		 
        sf.heal();
		for (i = 0; sc.parry(sf.strike()); i++) {
			;
		}
		assertEquals("Unexpected death of " + sc.getName(), i, 3);
 
 		new ArmedUnitSoldier(age, "Poilu", "Gogol", canvas); //exception raised : unknown soldier type
	}
}
