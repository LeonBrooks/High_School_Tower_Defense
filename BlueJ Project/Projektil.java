import java.awt.*;
/**
 * Ein einfaches Projektil
 *
 * @author Oliver Schneider
 * @version 05.07
 */
public class Projektil
{
    private double x,y,moveX,moveY;
    private int schaden, richtung;
    private Rectangle bounds;
    private Spielablauf s;
    private Image bildo, bildr, bildu, bildl;

    /**
     * Konstruktor für das Projektil
     * 
     * @param x Die x-Koordinate (linke obere Ecke)
     * @param y Die y-Koordinate (linke obere Ecke)
     * @param richtung Die Flugrichtung: 0 = oben, 1 = rechtsoben, 2 = rechts, ...
     * @param speed Die Geschwindigkeit des Projektils
     * @param schaden Der Schaden des Projektils
     */
    public Projektil(Spielablauf s, int x, int y, int richtung, int speed, int schaden)
    {
        this.s = s;
        this.schaden = schaden;
        
        this.richtung = richtung;
        
        this.x = x;
        this.y = y;
        
        double speedD = Math.sqrt(0.5*speed*speed);
        
        moveX = 0;
        moveY = 0;
        
        switch(richtung) {
            case 0:
                moveY = -speed;
                break;
            case 1:
                moveX = speedD;
                moveY = -speedD;
                break;
            case 2:
                moveX = speed;
                break;
            case 3:
                moveX = speedD;
                moveY = speedD;
                break;
            case 4:
                moveY = speed;
                break;
            case 5:
                moveX = -speedD;
                moveY = speedD;
                break;
            case 6:
                moveX = -speed;
                break;
            case 7:
                moveX = -speedD;
                moveY = -speedD;
                break;
        }
        
        bounds = new Rectangle(x,y,30,30);
        
        bildSetzen();
    }
    
    private void bildSetzen() {
        bildo = s.speicher.bild(34);
        bildr = s.speicher.bild(35);
        bildu = s.speicher.bild(36);
        bildl = s.speicher.bild(37);
    }
    
    public void update() {
        x += moveX;
        y += moveY;
        bounds.x = (int) x;
        bounds.y = (int) y;
        if(x<-30||y<-30||x>1470||y>750) {
            s.projektilEntfernen(this);
        }
        
        boolean hit = false;
        for(int i=0;i<s.gegnerAnzahl;i++) {
            if(bounds.intersects(s.g[i].bounds)&&!hit) {
                s.g[i].leben -= schaden;
                //hit = true;
                //s.projektilEntfernen(this);
            }
        }
    }
    
    public void paint(Graphics g) {
        Image temp = null;
        switch(richtung) {
            case 0:
                temp = bildo;
                break;
            case 2:
                temp = bildr;
                break;
            case 4:
                temp = bildu;
                break;
            case 6:
                temp = bildl;
                break;
        }
        g.drawImage(temp,(int)x,(int)y,30,30,null);
    }

}
