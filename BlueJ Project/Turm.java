import java.awt.*;
/**
 * Der einfachste Turm, Basis für andere Türme
 * 
 * @author Leon Brooks, Oliver Schneider
 * @version 05.07
 */
public class Turm
{
    public int x, y, schaden, radius;
    protected int angriffdauer, angriffcooldown, cooldown, speed;
    private int xMax, xMin, yMax, yMin;
    protected Rectangle range;
    protected boolean a;
    protected Image bild, angriffsbild;
    protected Spielablauf s;

    /**
     * Der Konstruktor für die Klasse Turm
     * 
     * @param s_ Der Spielablauf
     * @param x_ Die x-Koordinate (linke obere Ecke) des Turms
     * @param y_ Die y-Koordinate (linke obere Ecke) des Turms
     */
    public Turm(Spielablauf s_, int x_, int y_)
    {
       x=x_;
       y=y_;
       s=s_;
       a=false;
       radius=1;
       schaden=20;
       
       xMax = x + 90 + radius*90 -10;
       xMin = x - radius*90 - 10;
       yMax = y + 90 + radius*90 - 10;
       yMin = y - radius*90 - 10;
       
       range = new Rectangle(xMin, yMin, xMax-xMin, yMax-yMin);
       
       speed=10;
       cooldown=0;
       angriffdauer=2;
       angriffcooldown=0;
       
       bildSetzen();
    }
    
    /**
     * Diese Methode setzt das Bild des Turms fest.
     * Überschreiben, um ein neues Bild festzulegen (beim Erben)
     * 
     */
    protected void bildSetzen() {
        bild = s.speicher.bild(28);
        angriffsbild = s.speicher.bild(29);
    }
    
    /**
     * Ruft bei einer bestimmten Anzahl an Aufrufen angriff() auf
     */
    public void update()
    {
        if(cooldown>=speed)
        {
            angriff();
        }else {
            cooldown++;
        }
        
        if(angriffcooldown==angriffdauer)
        {
            a=false;
        }
        else
        {
            angriffcooldown++;
        }
    }
    
    /**
     * Sucht Gegner zum angreifen
     * Überschreiben, um keinen "Area of Effect" zu haben.
     */
    protected void angriff() {
        for(int i=0;i<s.gegnerAnzahl;i++)
        {
            if(range.intersects(s.g[i].bounds))
            {
                effekt(i);
            }
        }
        
        a = true;
        cooldown = 0;
        angriffcooldown = 0;
    }
    
    /**
     * Zeichnet den Turm
     * 
     * @param g Das Graphics Objekt
     */
    public void paint(Graphics g)
    {
        if(a)
        {
            g.drawImage(angriffsbild, x -radius*90, y-radius*90, 90+2*radius*90, 90+2*radius*90, null);
        }
        else
        {
            g.drawImage(bild, x, y, 90, 90, null);
        }
    }
    
    /**
     * Führt den Effekt des Turms aus.
     * Überschreiben, um neuen Effekt hinzuzufügen.
     * 
     * @param i Welcher Gegner in der Gegnerliste
     */
    public void effekt(int i)
    {
        s.g[i].leben=s.g[i].leben-schaden;
    }
}
