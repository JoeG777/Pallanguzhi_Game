package Spiel;

public class Main {

	/**
	 * Die Main-Methode dient zum Ausfuehren des Spiels.
	 * 
	 * @param args Das Default Parameter der Main Methode
	 */
	public static void main(String[] args) {
		UI ui = new UI();
		ui.willkommen();
		ui.startMenue();
		ui.gameLoop();
	}

}

