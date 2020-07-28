package Spiel;
/**
 * Die Klasse Spieler dient als Datencontainer für das Spiel. 
 * Sie bietet funktionen, die mit dem Spiel im Zusammenhang stehen.
 *
 */
public class Spieler implements Cloneable {
	private String name;
	private int anzahlSteine;
	private boolean istOben;
	
	/**
	 * Erschafft einen neunen Spieler.
	 * @param name Name des Spielers.
	 * @param anzahlSteine Initiale Anzahl der Steine des Spielers
	 * @param istOben Bestimmt, ob Spieler oben oder unten ist. 
	 */
	public Spieler(String name, int anzahlSteine, boolean istOben) {
		setName(name);
		this.anzahlSteine = anzahlSteine;
		setIstOben(istOben);
	}
	
	/**
	 * Gibt den Namen des Spielers zurueck.
	 * @return gibt den Namen des Spielers zurueck.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setzt den Namen des Spielers.
	 * @param name Name, den der Spieler haben soll
	 */
	public void setName(String name) {
		if(name == null) {
			throw new RuntimeException("Name existiert nicht!");
		}
		this.name = name;
	}
	
	/**
	 * Gibt die Anzahl der eigenen zurueck.
	 * @return Anzahl der Steine des Spielers.
	 */
	public int getAnzahlSteine() {
		return anzahlSteine;
	}
	
	/**
	 * Setzt die Anzahl der Steine
	 * @param anzahlSteine Wert, auf den die Anzahl gesetzt werden soll.
	 */
	public void setAnzahlSteine(int anzahlSteine) {
		this.anzahlSteine = anzahlSteine;
	}
	
	/**
	 * Erhoeht die Anzahl der Steine.
	 * @param anzahlSteine um wie viele Steine erhoeht werden soll.
	 */
	public void erhoeheAnzahlSteine(int anzahlSteine) {
		this.anzahlSteine += anzahlSteine;
	}
	/**
	 * Gibt das istOben-Attribut zurück.
	 * @return wahr/false, je nachdem, ob oben/unten
	 */
	public boolean isIstOben() {
		return istOben;
	}
	
	/**
	 * Setzt das istOben-Attribut.
	 * @param istOben
	 */
	public void setIstOben(boolean istOben) {
		this.istOben = istOben;
	}
	
	/**
	 * Gibt eine String-Information des Spielers zurueck.
	 */
	@Override
	public String toString() {
		return"";
	}
	
	/**
	 * Klont einen Spieler. 
	 */
	@Override
	public Spieler clone() {
		return new Spieler(this.getName(), this.getAnzahlSteine(), this.isIstOben());
	}
	
	/**
	 * Ueberprueft, ob zwei Spieler gleich sind. 
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(o.getClass() != this.getClass()) return false;
		
		Spieler sp = (Spieler) o;
		return this.getName().equals(sp.getName());
	}
	
}
