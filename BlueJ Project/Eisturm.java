
/**
 * AOE-Turm der Gegner verlangsamt
 * 
 * @author Leon Brooks 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Eisturm extends Turm
{
    /**
     * Konstruktor für Objekte der Klasse Eisturm
     */
    public Eisturm(Spielablauf s_, int x_, int y_)
    {
       super(s_, x_, y_);
       schaden = 1;
       radius = 1;
       speed = 5; 
    }
    
    protected void bildSetzen() {
        bild = s.speicher.bild(10);
        angriffsbild = s.speicher.bild(11);
    }
    
    public void effekt(int i)
    {
        s.g[i].slowed=true;
        s.g[i].cs=10;
    }
}
