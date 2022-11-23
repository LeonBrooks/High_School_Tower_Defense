import java.awt.*;
/**
 * Schießt einen Laser auf den ersten Gegner, verlangsamt + schaden
 *
 * @author Oliver Schneider
 * @version 05.07
 */
public class LaserErster extends Turm
{
    int zielGegner, zielX, zielY;
    
    /**
     * Konstruktor des Laser Turms
     */
    public LaserErster(Spielablauf s_, int x_, int y_)
    {
        super(s_, x_, y_);
        schaden = 300;
        speed = 40;
        angriffdauer = 2;
    }
    
    protected void bildSetzen() {
        bild = s.speicher.bild(30);
    }
    
    /**
     * Sucht den ersten Gegner, der auf dem Spielfeld ist
     */
    private int ersterGegner() {
        if(s.gegnerAnzahl>0) {
            Gegner erster = s.g[0];
            zielGegner = 0;
            for(int i=1;i<s.gegnerAnzahl;i++) {
                if(s.g[i].wegplatz>erster.wegplatz) {
                    erster = s.g[i];
                    zielGegner = i;
                    zielX = erster.x;
                    zielY = erster.y;
                }else if(s.g[i].wegplatz==erster.wegplatz){
                    int xG = s.g[i].x;
                    int yG = s.g[i].y;
                    if((xG>erster.x)||((erster.laufrichtung==0)&&(yG<erster.y))||((erster.laufrichtung==2)&&(yG>erster.y))){
                        erster = s.g[i];
                        zielGegner = i;
                        zielX = xG;
                        zielY = yG;
                    }
                }
            }
            return zielGegner;
        }
        return -1;
    }
    
    /**
     * Greift nur den ersten Gegner und alle dahinter an
     */
    protected void angriff() {
        int geg = ersterGegner();
        if(geg!=-1) {
            effekt(geg);
            
            a = true;
            cooldown = 0;
            angriffcooldown = 0;
        }
    }
    
    /**
     * Macht Schaden und verlangsamt den Gegner
     * 
     * @param i Der Gegner in der Liste
     */
    public void effekt(int i) {
        s.g[i].slowed=true;
        s.g[i].cs=20;
        s.g[i].leben-=schaden;
    }
    
    /**
     * Malt den Turm und den Laser
     * 
     * @param g Das Graphics Objekt
     */
    public void paint(Graphics g) {
        g.drawImage(bild,x,y,90,90,null);
        if(a) {
            g.setColor(Color.MAGENTA);
            Graphics2D gr = (Graphics2D) g;
            gr.setStroke(new BasicStroke(15));
            gr.drawLine(x+45,y+45,zielX+45,zielY+45);
        }
    }

}
