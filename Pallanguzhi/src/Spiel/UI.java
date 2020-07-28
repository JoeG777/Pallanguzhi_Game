package Spiel;

import java.util.Scanner;

/**
 * <h1>UI</h1>
 * Die UI ermoeglicht das Spielen des Spiels.
 * Es stellt die Nutzeroeberfleche dar.
 * 
 */
public class UI {

	private static Scanner sc;
	private static Spiel spiel;

	/**
	 * Konstruktor der UI.
	 * Instanziiert Scanner und Spiel.
	 * 
	 */
	public UI() {
		sc = new Scanner(System.in);
		spiel = new Spiel();
	}

	/**
	 * Willkommensmeldung
	 * 
	 */
	public void willkommen() {
		System.out.println("\nWillkommen zu Pallanguzhi.");
	}

	/**
	 * Das Startmenue zur Auswahl des Spielmodus.
	 * 
	 */
	public void startMenue() {
		boolean b = true;

		while (b) {
			System.out.println("\nBitte waehlen Sie, wie Sie spielen moechten:\n");
			System.out.print("(1) Spieler vs Spieler\n(2) Spieler vs KI\n(3) Spiel beenden\n\n> ");
			String eingabe = sc.nextLine();

			if (eingabe.equals("1")) {
				b = false;
				spielerHinzufuegen(2, 0);
			} else if (eingabe.equals("2")) {
				boolean a = true;

				while (a) {
					System.out.println("\nBitte waehlen Sie eine Schwierigkeitsstufe:\n");
					System.out.print("(1) Leicht\n(2) Schwer\n(3) Zurueck zur Auswahl\n\n> ");

					eingabe = sc.nextLine();
					if (eingabe.equals("1")) {
						b = false;
						a = false;
						spielerHinzufuegen(1,1);
					} else if (eingabe.equals("2")) {
						b = false;
						a = false;
						spielerHinzufuegen(1,2);
					} else if (eingabe.equals("3")) {
						a = false;
						continue;
					} else {
						System.out.println("\nFehlerhafte Eingabe. Bitte 1, 2 oder 3 eingeben.");
					}

				}
			} else if (eingabe.equals("3")) {
				System.out.println("Beenden.");
				System.exit(0);
			} else {
				System.out.println("\nFehlerhafte Eingabe. Bitte 1, 2 oder 3 eingeben.\n");
			}
		}

	}

	/**
	 * Diese Methode fuegt die entsprechenden Spieler hinzu.
	 * 
	 * @param anzahl Anzahl der echten Spieler
	 * @param schwierigkeit Schwierigkeitsgrad der KI, 0 wenn keine KI
	 */
	private void spielerHinzufuegen(int anzahl, int schwierigkeit) {
		if (anzahl == 2) {
			einenSpielerHinzufuegen(true);
			einenSpielerHinzufuegen(false);
		} else if (anzahl == 1) {
			einenSpielerHinzufuegen(true);
			kiAnlegen(schwierigkeit);
		} else {
			throw new RuntimeException("Es darf nur 1 oder 2 Spieler geben!");
		}
		System.out.println();
	}

	/**
	 * Legt einzelnen Spieler an.
	 * 
	 * @param oben gibt an, ob oberer oder unterer Spieler angelegt werden soll
	 */
	private void einenSpielerHinzufuegen(boolean oben) {
		boolean b = true;

		while (b) {
			if (oben) System.out.print("\nBitte geben Sie einen Namen fuer Spieler 1 ein:\n\n> ");
			if (!oben) System.out.print("\nBitte geben Sie einen Namen fuer Spieler 2 ein:\n\n> ");

			String name = sc.nextLine();
			try {
				spiel.spielerAnlegen(name, false);
				b = false;
			} catch (IllegalArgumentException e) {
				System.out.println("Name muss min. 2 Zeichen haben.");
			}
		}
	}

	/**
	 * Legt eine KI an.
	 * @param schwierigkeit Schwierigkeit der KI (1 entspricht einfach, 2 
	 * entspricht schwer). 
	 */
	private void kiAnlegen(int schwierigkeit) {
		if(schwierigkeit == 1) {
			spiel.spielerAnlegen("KI(einfach)", true);
		}
		if(schwierigkeit == 2) {
			spiel.spielerAnlegen("KI(schwer)", true);
		}
	}

	/**
	 * Diese Methode fuehrt den Ablauf des Spieles durch.
	 * 
	 */
	public void gameLoop() {
		spiel.beginnerWaehlen();
		while (!spiel.spielBeendet()) {
			System.out.println(spiel.getStatus());

			if(!(spiel.getAktiverSpieler() instanceof Computer)) {

				System.out.print("Geben Sie die Mulde ein, deren Steine Sie verteilen moechten\n> ");
				String ausgefuehrteZuege = spiel.ziehe(muldenEingabe());
				spielAusgabe(ausgefuehrteZuege);
			} else {
				String ausgefuehrteZuege = spiel.ziehe(-1);
				spielAusgabe(ausgefuehrteZuege);
			}

		}

		spiel.spielende();
		String gewinner = spiel.gewinnerErmitteln();
		if (gewinner.contentEquals("Niemand"))
			System.out.println("Das Spiel endet \"Remis\". Keine Partei hat mehr Spielsteine"
					+ "gesammelt als die andere");
		else {
			System.out.println(spiel.gewinnerErmitteln() + " hat dieses Spiel gewonnen !");		}

		System.out.println("Das Spiel ist beendet!");
	}

	/**
	 * Verwaltet die Eingabe des Nutzers und faengt alle Fehler ab.
	 * 
	 * @return gepruefterMuldenIndex valide Eingabe des Nutzers
	 */
	private int muldenEingabe() {
		String eingabe = sc.nextLine();
		int muldenIndex;
		
		try {
			muldenIndex = spiel.eingabePruefen(eingabe);
			if (muldenIndex == -1) {
				System.out.println("Spiel abgebrochen");
				System.exit(0);
			}
			return muldenIndex;
		} catch (NumberFormatException e) {
			System.out.println("\nEs sind nur Ganzzahlen zwischen 0 und 13 moeglich!\n");
			System.out.print("Geben Sie die Mulde ein, deren Steine Sie verteilen moechten\n> ");
			return muldenEingabe();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.print("\nGeben Sie die Mulde ein, deren Steine Sie verteilen möchten\n> ");
			return muldenEingabe();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("\nEs sind nur Ganzzahlen zwischen 0 und 13 moeglich!\n");
			System.out.print("Geben Sie die Mulde ein, deren Steine Sie verteilen moechten\n> ");
			return muldenEingabe();
		}
	}

	/**
	 * Konsolenausgabe eines gesamten Zuges eines Spielers.
	 * 
	 * @param letzterZug Von der Spiellogik erzeugter String aller gemachten Zuege. Diese
	 * werden dann mit Zeitverzoegerung ausgegeben. Es ist keine Nutzereingabe erforderlich.
	 */
	private void spielAusgabe(String letzterZug) {
		String [] einzelneSpielzuege = letzterZug.split("#");

		for (String zug : einzelneSpielzuege) {
			if(zug.contains("***Karu***")) {
				System.out.println(zug);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.getMessage();
				}
			}
			else
				System.out.println(zug);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
	}
}