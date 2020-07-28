package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import Spiel.Spielfeld;

class testSpielfeld {

	static Spielfeld sf;
	static int[] testArray = new int[14];
	
	
	@Before
	static void setUp() {
		sf = new Spielfeld();
		for (int i = 0; i< testArray.length; i++) {
			testArray[i] = 5;
		}
	}
	
	@Test
	void testKonstruktor() {
		assertNotNull(sf);
	}
	
	@Test
	void testSetSteine() {
		assertArrayEquals(testArray, sf.getMulden());
	}
	
	@Test
	void testSetzeStein() {
		sf.setzeStein(0);
		assertEquals(6,sf.getMulden()[0]);
	}
	
	@Test
	void testGetIndexGegenueber() {
		assertEquals(3, sf.getIndexGegenueber(10));
	}

}
