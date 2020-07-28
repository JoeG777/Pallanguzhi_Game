package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import Spiel.Spiel;

class testSpiel {
	Spiel sp;
	
	@Before
	void init () {
		sp = new Spiel();
		sp.spielerAnlegen("Johannes", true);
		sp.spielerAnlegen("Buenni", false);
	}

}
