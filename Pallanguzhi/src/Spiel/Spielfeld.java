package Spiel;

/**
 * Die Spielfeld-Klasse ist der Bauplan des Spielfeldes
 * von Pallanguzhi und wird vom Spiel verwendet.
 *
 */
public class Spielfeld implements Cloneable{
	private int[] mulden;
	
	/**
	 * Konstruktor der Klasse Spielfeld. Setzt die Steine.
	 * 
	 */
	public Spielfeld() {
		mulden = new int[14];
		setSteine();
	}
	
	/**
	 * Privater Konstruktor des Spielfeldes.
	 * 
	 * @param mulden Mulden des Spielfeldes als int-Array
	 */
	private Spielfeld(int[] mulden) {
		this.mulden = mulden;
	}
	
	/**
	 * Gibt die Steine zu einer Mulde zurueck.
	 * 
	 * @param mulde Mulde, dessen Steine man wissen will.
	 * @return Steine der Mulde
	 */
	public int getSteine(int mulde) {
		int steineInMulde = mulden[mulde];
		mulden[mulde] = 0;
		return steineInMulde;
	}
	
	/**
	 * Setzt die Steine in die einzelnen Mulden.
	 * 
	 */
	private void setSteine() {
		for(int i = 0; i<mulden.length; i++) {
			mulden[i] = 5;
		}
	}
	
	/**
	 * Setzt einen Stein in eine Mulde.
	 * 
	 * @param mulde Mulde, in die man einen Stein setzten will.
	 */
	public void setzeStein(int mulde) {
		mulden[mulde] +=1;
	}
	
	/**
	 * Gibt die Anzahl der Steine innerhalb einer uebergebenen
	 * Mulde zurueck.
	 * 
	 * @param mulde Mulde, dessen Anzahl der Steine man wissen will
	 * @return Anzahl der Steine innerhalb der Mulde
	 */
	public int getAnzahlSteine(int mulde) {
		return mulden[mulde];
	}
	
	/**
	 * Gibt alle Mulden als int-Array zurueck.
	 * 
	 * @return int-Array, mit allen Mulden
	 */
	public int[] getMulden() {
		return mulden;
	}

	/**
	 * Gibt die Anzahl aller Steine auf dem Spielfeld zurueck.
	 * 
	 * @return Anzahl aller Steine auf dem Spielfeld
	 */
	public int getSteineAufSpielfeld() {
		int steineAufDemSpielfeld = 0;
		for (int i = 0; i<mulden.length;i++) {
			steineAufDemSpielfeld += mulden[i];
		}
		
		return steineAufDemSpielfeld;
	}
	
	/**
	 * Hilfsmethode, um die Mulde gegenueber auszu machen.
	 * 
	 * @param index Dessen Gegenueber gefunden werden soll
	 * @return Der gegenueberliegende Index der Mulde
	 */
	public int getIndexGegenueber(int index) {
		index -= 13;
		if(index < 0)
			index*= (-1);
		return index;
	}
	
	/**
	 * Schafft eine Kopie des Spielfeldes.
	 * 
	 */
	@Override
	public Spielfeld clone() {
		int[] mulden = new int[this.getMulden().length];
		
		for(int i = 0; i < this.getMulden().length; i++) {
			mulden[i] = this.getMulden()[i];
		}
		
		return new Spielfeld(mulden);
	}
}
