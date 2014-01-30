package soldiers;

import static org.junit.Assert.*;
import myframework.MyGameDefaultImpl;

import org.junit.Test;

public class SoldierGameTest {

	@Test
	public void testLevelOne() {
		MyGameDefaultImpl g = new MyGameDefaultImpl();
		GameLevelOne level = new GameLevelOne(g);
		assertEquals(level.SPRITE_SIZE, 16);
		assertEquals(level.SIZE_SQUAD_1, 3);
		assertEquals(level.SIZE_SQUAD_2, 6);
		assertEquals(level.NB_SQUADS, 12);
	}

}
