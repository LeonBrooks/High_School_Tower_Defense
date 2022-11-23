import java.awt.*;
/**
 * Schießt einen durchgehenden Laser auf den nächsten Gegner
 *
 * @author Oliver Schneider
 * @version (05.07
 */
public class Laser extends Turm
{
    int zielGegner, zielX, zielY;
    
    /**
     * Konstruktor des Laser Turms
     * 
     * @param s_ Der Spielablauf
     * @param x_ Die x-Koordinate (linke obere Ecke)
     * @param y_ Die y-Koordinate (linke obere Ecke)
     */
    public Laser(Spielablauf s_, int x_, int y_)
    {
        super(s_,x_,y_);
        schaden = 5;
        radius = 3;
        angriffdauer = 1;
        speed = 1;
    }
    
    protected void bildSetzen() {
        bild = s.speicher.bild(3);
        angriffsbild = s.speicher.bild(4);
    }
    
    /**
     * Greift nur den nächsten Gegner an
     */
    protected void angriff() {
        int geg = naechsterGegner();
        if(geg!=-1) {
            effekt(geg);
            
            a = true;
            cooldown = 0;
            angriffcooldown = 0;
        }
    }
    
    /**
     * Sucht den nächsten Gegner am Turm
     */
    private int naechsterGegner() {
        if(s.gegnerAnzahl>0) {
            Gegner naechster = s.g[0];
            zielGegner = 0;
            double abstand = messen(naechster.x,naechster.y);
            for(int i=1;i<s.gegnerAnzahl;i++) {
                double temp = messen(s.g[i].x,s.g[i].y);
                if(temp<abstand) {
                    naechster = s.g[i];
                    abstand = temp;
                    zielGegner = i;
                }
            }
            zielX = naechster.x;
            zielY = naechster.y;
            if(abstand<radius*90) {
                return zielGegner;
            }
        }
        return -1;
    }
    
    /**
     * Misst den Abstand von Gegner zu Turm
     */
    private double messen(int xg, int yg) {
        xg = xg-x;
        yg = yg-y;
        double a = xg*xg + yg*yg;
        a = Math.sqrt(a);
        if(a<0) {
            a = -a;
        }
        return a;
    }
    
    /**
     * Malt den Turm und den Laser
     * 
     * @param g Das Graphics Objekt
     */
    public void paint(Graphics g) {
        g.drawImage(bild,x,y,90,90,null);
        if(a) {
            g.setColor(Color.RED);
            Graphics2D gr = (Graphics2D) g;
            gr.setStroke(new BasicStroke(10));
            gr.drawLine(x+45,y+45,zielX+45,zielY+45);
        }
    }

}
