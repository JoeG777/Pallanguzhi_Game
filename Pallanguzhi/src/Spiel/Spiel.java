package Spiel;

import java.util.ArrayList;

/**
 * <h1>Spiel</h1>
 * Die Klasse Spiel implementiert das zentrale Objekt fuer das
 * Spiel Pallanguzhi.
 * 
 */
public class Spiel implements Cloneable{
	private Spieler aktiverSpieler;
	private Spielfeld spielfeld;
	private Spieler[] spieler;

	/**
	 * Konstruktor der Klasse Spiel.
	 * 
	 */
	public Spiel () {
		spieler = new Spieler[2];
		schaffeNeuesSpielfeld();
	}
	
	/**
	 * Erschafft ein neues Spiel (erschafft für clone-Methode).
	 * 
	 * @param aktiverSpieler Spieler, der am Zug ist.
	 * @param spielfeld Spielfeld, das verwendet werden soll. 
	 * @param spieler Spieler, die im Spiel sind. 
	 */
	private Spiel(Spieler aktiverSpieler, Spielfeld spielfeld, Spieler[] spieler) {
		this.aktiverSpieler = aktiverSpieler;
		this.spielfeld = spielfeld;
		this.spieler = spieler;
	}
	
	/**
	 * Gibt den aktiven Spieler zurueck.
	 * 
	 * @return Spieler, der am Zug ist.
	 */
	public Spieler getAktiverSpieler() {
		return aktiverSpieler;
	}
	
	/**
	 * Setzt den aktiven Spieler.
	 * 
	 * @param aktiverSpieler Spieler, der jetzt aktiver Spieler sein soll.
	 */
	public void setAktiverSpieler(Spieler aktiverSpieler) {
		this.aktiverSpieler = aktiverSpieler;
	}
	
	/**
	 * Gibt das Spielfeld-Attribut zurueck.
	 * 
	 * @return das Spielfeld-Attribut.
	 */
	public Spielfeld getSpielfeld() {
		return spielfeld;
	}
	
	/**
	 * Setzt das Spielfeld-Attribut auf ein neues Spielfeld.
	 * 
	 */
	public void schaffeNeuesSpielfeld() {
		this.spielfeld = new Spielfeld();
	}
	
	/**
	 * Gibt die Spieler zurueck.
	 * 
	 * @return Spieler-Array mit mitspielenden Spielern.
	 */
	public Spieler[] getSpieler() {
		return this.spieler;
	}
	
	/**
	 * Erschafft eine tiefe Kopie des Spiels.
	 * 
	 */
	@Override
	public Spiel clone() {
		Spieler[] spielerKlone = new Spieler[this.spieler.length];
		Spieler aktiverGeklonterSpieler = null;
		
		for(int i = 0; i < this.spieler.length; i++) {
			spielerKlone[i] = this.spieler[i].clone();
			if(aktiverSpieler.getName().equals(spielerKlone[i].getName())) {
				aktiverGeklonterSpieler = spielerKlone[i];
			}
		}
		
		return new Spiel(aktiverGeklonterSpieler, this.spielfeld.clone(), spielerKlone);
	}
	
	/**
	 * Legt den Spieler mit angegebenem Namen an und entscheidet, ob dieser unten
	 * oder oben im Spielfeld ist.
	 * 
	 * @param name Gewaehlter Name des anzulegenden Spielers
	 */
	public void spielerAnlegen(String name, boolean ki) {
		if(!ki) {
			if (name == null || name.length()<2) {
				throw new IllegalArgumentException("Kein gultiger Name!");
			}

			if (spieler[0] == null ) {
				spieler[0] = new Spieler(name, 0, true);
			}
			else if(spieler[1] == null ) {
				spieler[1] = new Spieler(name, 0, false);
			}
			else {
				throw new RuntimeException("Spielplaetze sind bereits belegt");
			}

		}
		else {
			if(name.equals("KI(einfach)")) {
				spieler[1] = new Computer(0, false, SchwierigkeitsEnum.EINFACH);
			}
			else if(name.equals("KI(schwer)")) {
				spieler[1] = new Computer(0, false, SchwierigkeitsEnum.SCHWER);
			}
		}
	}

	/**
	 * Waehlt einen der beiden Spieler fuer den 1.Zug aus
	 * 
	 * @return beginnenderSpieler Spieler der zufaellig 
	 * ausgewaehlt wurde den ersten Zug zu machen
	 */
	public void beginnerWaehlen() {
		int anfaenger = (int) (Math.random());
		setAktiverSpieler( spieler[anfaenger]);
	}

	/**
	 * Wandelt die Nutzeringabe in einen MuldenIndex um.
	 * 
	 * @param eingabe Eingabe des Nutzers
	 * @return MuldenIndex Ausgewaehlte Mulde
	 */
	public int eingabePruefen(String eingabe) {
		int muldenIndex;
		int[] aktuellesSpielfeld = spielfeld.getMulden();

		if (eingabe.equalsIgnoreCase("exit"))
			return -1;
		else
			muldenIndex = Integer.parseInt(eingabe);

		if(aktuellesSpielfeld[muldenIndex] == 0) {
			throw new IllegalArgumentException("Waehlen Sie eine Mulde, welche Steine enthaelt!");
		}

		if(!eingabeMuldeAufSeiteDesAktivenSpielers(muldenIndex)) {
			throw new IllegalArgumentException("Geben Sie nur Muldenzahlen Ihrer Seite an!");
		}
		return muldenIndex;
	}

	/**
	 * Hilfmethode zur Ermittlung einer gueltigen Eingabe. 
	 * 
	 * @param index Eingabe des Benutzers.
	 * @return Wahrheitswert Ist die eingegebene Mulde auf der Seite des aktiven Spielers.
	 */
	private boolean eingabeMuldeAufSeiteDesAktivenSpielers(int index) {
		return ((getAktiverSpieler().isIstOben() == true) && (index>=7 && index<=13)) || 
				((!getAktiverSpieler().isIstOben()) && (index >=0 && index <=6));
	}
	
	/**
	 * Prueft, ob eine KI am Zug ist und fuehrt dann, je nachdem ob ein Spieler
	 * oder eine KI am Zug ist, den passenden Zug (den uebergebenen oder den
	 * ermittelten Zug der KI) aus.
	 * 
	 * @param mulde die Mulde, die der Nutzer gewaehlt hat oder der default-Wert
	 * fuer eine KI.
	 * @return einen String, der den gesamten Zug beschreibt. 
	 */
	public String ziehe(int mulde) {
		int aktuelleMulde = mulde;
		if(this.aktiverSpieler instanceof Computer) {
			int bester = ((Computer) aktiverSpieler).getZug(this.clone());
			aktuelleMulde = bester;
		}
		
		return ziehenAusfuehren(aktuelleMulde);
	}
	
	/**
	 * Fuehrt den gesamten Zug eines Spielers aus. Mit der Eingabe bis zum Vergeben der Punkte.
	 * 
	 * @param mulde Index der Mulde, aus der die Steine verteilt werden sollen
	 */
	public String ziehenAusfuehren (int mulde) {
		int aktuelleMulde = mulde;

		StringBuilder kompletterSpielzug = new StringBuilder();
		int[] aktuellesSpielfeld = spielfeld.getMulden();

		do {
			aktuelleMulde = verteilen(aktuelleMulde, aktuellesSpielfeld, kompletterSpielzug);
			
			aktuelleMulde = incIndexDerMulden(aktuelleMulde); //in die naechste schauen
		}while(spielfeld.getMulden()[aktuelleMulde] != 0);

		//Steine aus der weiteren Mulde einsacken und der gegenueberligenden
		aktuelleMulde = incIndexDerMulden(aktuelleMulde);
		aktiverSpieler.erhoeheAnzahlSteine(aktuellesSpielfeld[aktuelleMulde]);
		aktuellesSpielfeld[aktuelleMulde] = 0;

		int indexGegenueber = spielfeld.getIndexGegenueber(aktuelleMulde);
		aktiverSpieler.erhoeheAnzahlSteine(aktuellesSpielfeld[indexGegenueber]);
		aktuellesSpielfeld[indexGegenueber] = 0;

		switchAktiverSpieler();
		return kompletterSpielzug.toString();
	}	

	/**
	 * Verteilt bei angegebener Mulde, deren Steine automatische gemaess Regeln
	 * gegen den urzeigersinn auf dem Speilbrett.
	 * 
	 * @param mulde Mulde, deren Steine verteilt werden sollen
	 */
	private int verteilen(int mulde, int[] aktuellesSpielfeld, StringBuilder kompletterZug) {
		int steineInHand = spielfeld.getSteine(mulde);//Steine in die Hand nehmen
		int aktuelleMulde = mulde;// mulde aus der die zu verteilenden Steine genommen wurden
		for (int i = 1; i<= steineInHand;i++) {//in jede Mulde einen Stein legen, bis alle weg
			aktuelleMulde = incIndexDerMulden(aktuelleMulde);
			spielfeld.setzeStein(aktuelleMulde);

			if (aktuellesSpielfeld[aktuelleMulde] == 4) {
				String karuInfo = karu(aktuelleMulde, aktuellesSpielfeld);
				kompletterZug.append(karuInfo);
			}
			kompletterZug.append(getStatus());
		}
		return aktuelleMulde;
	}

	/**
	 * Bei Karu erhaelt der Spieler die Steine einer Mulde auf seiner Seite, in welcher exakt 4
	 * Steine enthalten sind.
	 * 
	 * @param aktuelleMulde Mulde, in welcher das Karu entdeckt wurde
	 * @param aktuellesSpielfeld aktueller Status des Spielfelds, auf dem die Mulden mit 
	 * den Steinen liegen.
	 * @return karuInfos Informationen ueber das Karu (Spieler, Spielsteine).
	 */
	private String karu(int aktuelleMulde, int[] aktuellesSpielfeld) {
		//Karu
		String karuInfo = "\t***KARU***\n";
		if (aktuelleMulde >= 7 && aktuelleMulde <=13) {
			//laut spielerAnlgegen-Methode ist spieler[0] immer isOben() = true
			spieler[0].erhoeheAnzahlSteine(aktuellesSpielfeld[aktuelleMulde]); //Kugel gutschreibem
			aktuellesSpielfeld[aktuelleMulde] = 0; 							//Kugeln entfernen

			karuInfo += "\t" + spieler[0].getName()+ " erhaelt 4 Kugeln aus der Mulde " + aktuelleMulde + "\n";
		}else{ //Index ist unten, spieler[1] istUnten()
			spieler[1].erhoeheAnzahlSteine(aktuellesSpielfeld[aktuelleMulde]); //Kugel gutschreibem
			aktuellesSpielfeld[aktuelleMulde] = 0; 							//Kugeln entfernen

			karuInfo += "\t" + spieler[1].getName()+ " erhaelt 4 Kugeln aus der Mulde " 
			+ aktuelleMulde + "\n";
		}	
		return karuInfo ;
	}

	/**
	 * Alle aktuellen und relevanten Spielinformation zur Darstellung auf der Konsole.
	 * 
	 * @return aktuellenInformationen Anschließende Ausgabe auf der Konsole
	 */
	public String getStatus() {
		StringBuilder status = new StringBuilder();

		int[] aktuellesSpielfeld = spielfeld.getMulden();
		status.append("Spielstand: \n\n\n" + spieler[0].getName() + " : " + spieler[0].getAnzahlSteine() + " Punkte");
		status.append("\n\n 13  12  11  10  9   8   7  \n");
		for(int j = 13; j>=7;j--) {
			status.append("[" + aktuellesSpielfeld[j] + "] " );
		}
		status.append("\n----------------------------\n");
		for (int i = 0; i <= 6; i++) {
			status.append("[" + aktuellesSpielfeld[i] + "] " );
		}
		status.append("\n 0   1   2   3   4   5   6 \n");


		status.append("\n" + spieler[1].getName() + " : " + spieler[1].getAnzahlSteine() + " Punkte");
		status.append("\n" + aktiverSpieler.getName() + " ist am Zug.");
		status.append("\n\n#");
		return status.toString(); 
	}

	/**
	 * Fragt ab, ob eine Seite des Spielfeldes leer ist und somit das Spiel beendet wird.
	 * 
	 * @return Wahrheitswert Ist das Spiel beendet?
	 */
	public boolean spielBeendet() {
		int[] aktuellesSpielfeld = spielfeld.getMulden();
		boolean untenIstLeer = true;
		for (int j = 0; j<7;j++) {
			if(aktuellesSpielfeld[j] != 0)
				untenIstLeer = false;
		}
		if(!untenIstLeer) {
			for (int i = 7; i<aktuellesSpielfeld.length;i++) {
				if(aktuellesSpielfeld[i] != 0)
					return false;
			}
			return true;
		}
		return untenIstLeer;
	}

	/**
	 * Prueft bei gegebenem Spieler, ob er das Spiel gewonnen hat.
	 * 
	 * @param spieler Spieler, der ueberprueft werden soll.
	 * @return Uebergebener Spieler hat das Spiel gewonnen.
	 */
	public boolean hatGewonnen(Spieler spieler) {
		if(spieler.getAnzahlSteine() > 35) {
			return true;
		}
		else {
			return false;
		}
}
	/**
	 * Diese Methode wird aufgerufen, wenn spielBeendet() true ergibt.
	 * Rechnet die restlichen Steine auf dem Brett dem Spieler zu, dessen Seite leer ist.
	 * 
	 */
	public void spielende() {
		boolean untenIstLeer = true;
		for (int j = 0; j<7;j++) {
			if(spielfeld.getMulden()[j] != 0)
				untenIstLeer = false; //oben muss leer sein, durch spielBeendet aufgerufen
		}
		if (untenIstLeer) {
			for (Spieler sp : spieler) {
				if(!sp.isIstOben())
					sp.erhoeheAnzahlSteine(spielfeld.getSteineAufSpielfeld());
			}
		}
		else {
			for (Spieler sp : spieler) {
				if(sp.isIstOben())
					sp.erhoeheAnzahlSteine(spielfeld.getSteineAufSpielfeld());
			}
		}
	}

	/**
	 * Ermittelt nach Beenden des Spiels und Verteilen der 
	 * restlichen Steine den tatsächlichen Gewinner.
	 * 
	 * @return NameDesGewinners Name des Spielers mit den meisten Punkten. 
	 * Bei Remis wird "Niemand" zurueck gegeben.
	 */
	public String gewinnerErmitteln() {
		for (Spieler sp : spieler) {
			if (sp.getAnzahlSteine() == 35)
				return "Niemand";
			else if (sp.getAnzahlSteine() > 35)
				return sp.getName();
		}

		throw new RuntimeException("Die Spielsteinverteilung hat nicht funktioniert");
	}

	/**
	 * Hilfsmethode
	 * Erhoet den Index und beachtet dabei die Groesse des Spielfelds.
	 * 
	 * @param index Aktueller Index, der nun um eins erhoeht wird.
	 * @return Der neue Index.
	 */
	private int incIndexDerMulden(int index) {
		index += 1;
		if(index >=14)
			index = 0;
		return index;
	}

	
	/**
	 * Gibt alle moeglichen Zuege für ein Spielfeld und einen bestimmten Spieler 
	 * zurueck.
	 * 
	 * @param spielfeld Spielfeld, das betrachtet werden soll. 
	 * @param spieler Spieler, dessen Zuege herausgefunden werden sollen.
	 * @return eine ArrayList die alle moeglichen Felder-Indizes enthält.
	 */
	public ArrayList<Integer> getWaehlbareFelder(Spielfeld spielfeld, Spieler spieler) {
		ArrayList<Integer> moeglich = new ArrayList<>(); 
		if(spieler.isIstOben()) {
			for(int i = 7; i < 14; i++) {
				if(spielfeld.getAnzahlSteine(i) > 0) {
					moeglich.add(i);
				}
			}
		}
		else {
			for(int i = 0; i < 7; i++) {
				if(spielfeld.getAnzahlSteine(i) > 0) {
					moeglich.add(i);
				}
			}
		}
		
		return moeglich;
	}
	

	/**
	 * Tauscht den aktiven Spieler, sodass abwechselnd gezogen werden kann.
	 * 
	 */
	private void switchAktiverSpieler() {
		if (aktiverSpieler == null)
			throw new RuntimeException("Kein aktiver SPieler gesetzt");

		if (spieler[0].getName().equals(aktiverSpieler.getName())) {
			setAktiverSpieler(spieler[1]);
		}
		else {
			setAktiverSpieler(spieler[0]);
		}

	}
}
