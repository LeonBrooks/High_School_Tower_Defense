import java.awt.*;
import java.util.Random;
/**
 * Explodiert
 * 
 * @author Leon Brooks
 * @version 05.07
 */
public class  TNT extends Turm
{
    private int countx, county;
    private Random r;
    
    /**
     * Konstruktor für Objekte der Klasse Falle
     * 
     * @param s_ Der Spielablauf
     * @param x_ Die x-Koordinate (linke obere Ecke)
     * @param y_ Die y-Koordinate (linke obere Ecke)
     */
    public TNT(Spielablauf s_, int x_, int y_)
    {
        super(s_, x_, y_);
        radius = 2;
        schaden = 300;
        speed = 20;
        angriffdauer = 5;
        
        r = new Random();
        
        countx = x+r.nextInt(180)-45;
        county = y+r.nextInt(180)-45;
    }
    
    protected void bildSetzen() {
        bild = s.speicher.bild(31);
        angriffsbild = s.speicher.bild(32);
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        Font small = new Font("Showcard Gothic", Font.TRUETYPE_FONT,30);
        g.setColor(Color.BLACK);
        g.setFont(small);
        
        if(cooldown == 10||cooldown == 20) {
            int tempx = r.nextInt(180)-90;
            int tempy = r.nextInt(180)-90;
            countx = x+tempx+45;
            county = y+tempy+45;
        }
        
        if(!a) {
            g.drawString((int)((20-cooldown)/10+1)+"",countx,county);
        }
        
        if(a && angriffcooldown == 5) {
            s.turmEntfernen(this);
        }
    }
    
    /**
     * Macht Schaden um sich herum und löscht sich
     */
    public void angriff()
    {
        super.angriff();
        cooldown = 0;
        a = true;
    }
    
    
}
