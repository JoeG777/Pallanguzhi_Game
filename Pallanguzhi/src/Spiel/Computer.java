package Spiel;

import java.util.ArrayList;
/**
 * Ein Spieler, der Methoden bietet, um Zuege zu ermitteln.
 * Er hat eine Schwierigkeit und kann dadurch mit einem geklonten Spiel
 * Simulationen ausführen, um einen guten Zug zu ermitteln. 
 *
 */
public class Computer extends Spieler implements Cloneable{
	private SchwierigkeitsEnum schwierigkeit;
	
	/**
	 * Erstellt einen neunen Computer(gegner), also eine KI.
	 * @param anzahlSteine die Anzahl der Steine, die die KI inital hat
	 * @param istOben die Seite der KI
	 * @param schwierigkeit die Schwierigkeit der KI.
	 */
	public Computer(int anzahlSteine, boolean istOben, SchwierigkeitsEnum schwierigkeit) {
		super("Computer" + ((schwierigkeit.equals(SchwierigkeitsEnum.EINFACH)? "(einfach)" : "(schwer)")), anzahlSteine, istOben);
		this.schwierigkeit = schwierigkeit;
	}
	
	/**
	 * Gibt die Schwierigkeit der KI zurück
	 * @return ein Schwierigkeitsenum, das die Schwierigkeit der KI beschreibt
	 */
	public SchwierigkeitsEnum getSchwierigkeit() {
		return schwierigkeit;
	}


	/**
	 * Setzzt die Schwierigkeit der KI
	 * @param schwierigkeit Schwierigkeitsenum mit Schwierigkeit
	 */
	public void setSchwierigkeit(SchwierigkeitsEnum schwierigkeit) {
		this.schwierigkeit = schwierigkeit;
	}
	
	/**
	 * Gibt in Abhängigkeit der Schwierigkeit der KI einen moeglichen Zug zurueck.
	 * KI-Einfach --> Zufall
	 * KI-Schwer --> leider kein minimax, aber ein bewerteter Zug
	 * @param spiel Ein Spiel, für das ein moeglicher Zug gefunden werden soll.
	 * @return ein moeglicher Zug
	 */
	public int getZug(Spiel spiel) {
		int gewaehltesFeld = -1;
		if(this.getSchwierigkeit() == SchwierigkeitsEnum.EINFACH) {
			gewaehltesFeld = getZugEinfach(spiel);
		}
		else if(this.getSchwierigkeit() == SchwierigkeitsEnum.SCHWER) {
			gewaehltesFeld = getZugSchwer(spiel);
		}
		else {
			throw new RuntimeException("Schwierigkeit ist nicht gesetzt"
					+ "oder ungueltig");
		}
		
		return gewaehltesFeld;
	}
	
	/**
	 * Gibt einen zufaelligen, moeglichen Zug zurück.
	 * @param spiel ein Spiel, für das ein zufälliger Zug ermittelt werden soll.
	 * @return 
	 */
	private int getZugEinfach(Spiel spiel) {
		ArrayList<Integer> waehlbareFelder = spiel.getWaehlbareFelder(spiel.getSpielfeld(), this);
		int gewaehlt = (int) Math.random()*waehlbareFelder.size();
		
		return waehlbareFelder.get(gewaehlt);
	}
	
	/**
	 * Berechnet die Bewertungsfunktion für die KI für ein Spiel.
	 * @param geklontesSpiel ein Spiel, auf dem ein Zug simuliert wurde (oder auch nicht).
	 * @return Eine Bewertung des Zuges.
	 */
	private int berechneBewertung(Spiel geklontesSpiel) {
		Spieler[] spieler = geklontesSpiel.getSpieler();
		int score = 0; 
		for(Spieler einSpieler : spieler) {
			if(einSpieler instanceof Computer) {
				score += einSpieler.getAnzahlSteine();
				try {
					if(geklontesSpiel.hatGewonnen(einSpieler)) {	
					score += 35;
					}
				}catch(Exception e) {
					
				}
			}
			else {
				score -= einSpieler.getAnzahlSteine();
				
				try {
					if(geklontesSpiel.hatGewonnen(einSpieler)) {
					score -= 35;
					}
				}catch(Exception e) {
					
				}
			}
		}
		
		return score;

	}
	
	/**
	 * Berechnet die Bewertung für alle moeglichen Zuege 
	 * des Spiel und waehlt die beste (für die KI).
	 * @param spiel ein Klon des Spiels, das gerade gespielt wird.
	 * @return Bester moeglicher Zug (Tiefe 1), falls keiner gefunden wurde 
	 * einen zufaelligen Zug.
	 */
	private int getZugSchwer(Spiel spiel) {
		ArrayList<Integer> waehlbareFelder = spiel.getWaehlbareFelder(spiel.getSpielfeld(), this);
		int besterZug = -1;
		int besterWert = -1000;
		for(int i : waehlbareFelder) {
			Spiel klon = spiel.clone();
			klon.ziehenAusfuehren(i);
			int val = berechneBewertung(klon) + berechneBewertung(spiel);
			if(val > besterWert) {
				besterZug = i;
				besterWert = val;
			}
		}
		
		if(besterZug == -1) {
			besterZug = getZugEinfach(spiel);
		}
		return besterZug;
	}
	
	
	/**
	 * Klont den Computer und gibt einen neuen zurück.
	 * @return eine tiefe Kopie des urspruenglichen Computers.
	 */
	@Override
	public Computer clone() {
		return new Computer(this.getAnzahlSteine(), 
				this.isIstOben(), 
				this.getSchwierigkeit());
	}

}
