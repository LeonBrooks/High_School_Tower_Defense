
/**
 * Write a description of class Dornturm here.
 *
 * @author Oliver Schneider
 * @version 05.07
 */
public class Dornturm extends Turm
{

    /**
     * Konstruktor für den Dormturm
     */
    public Dornturm(Spielablauf s_, int x_, int y_)
    {
        super(s_,x_,y_);
        
        speed = 20;
        schaden = 5;
    }
    
    protected void bildSetzen() {
        bild = s.speicher.bild(33);
    }
    
    /**
     * Schießt Projektile in alle Richtungen
     */
    protected void angriff() {
        for(int i=0;i<4;i++) {
            s.projektilHinzufuegen(0,x+30,y+30,i*2,15,schaden);
        }
        
        cooldown = 0;
    }
}
