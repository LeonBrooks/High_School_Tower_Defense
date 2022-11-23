import java.awt.*;
/**
 * Der erste Gegner!!
 * 
 * @author Leon Brooks
 * @version 17.07.17
 */
public class Gegner
{
    public int x, y, speed, speed_, leben, wegplatz, laufrichtung, cs, cf, geld;
    protected Speicher s;
    protected Image bildo, bildu, bildr, freezeo, freezeu, freezer;
    private int[] weg;
    protected boolean slowed, speedup, ziel;
    public Rectangle bounds;
    
    public Gegner(int x_, int y_, int[] weg_, Speicher s_)
    {
        slowed = false;
        speedup = false;
        cs = 0;
        cf = 0;
        
        geld = 30;
        
        ziel = false;
        
        s = s_;
        x = x_;
        y = y_;
        weg = weg_;
        speed = 15;
        leben = 100;
        wegplatz = 0;
        laufrichtung = 0;

        bounds = new Rectangle(x, y, 90, 90);
        
        bilderSetzen();
    }
    
    public void bilderSetzen() {
        bildo = s.bild(0);
        bildr = s.bild(1);
        bildu = s.bild(2);
        freezeo = s.bild(12);
        freezer = s.bild(13);
        freezeu = s.bild(14);
    }
    
    public void paint(Graphics g)
    {
        if(slowed) {
            switch (laufrichtung)
            {
                case 0: g.drawImage(freezeo, x, y, 90, 90, null); break;
                case 1: g.drawImage(freezer, x, y, 90, 90, null); break;
                case 2: g.drawImage(freezeu, x, y, 90, 90, null); break;
            }
        }else {
            switch (laufrichtung)
            {
                case 0: g.drawImage(bildo, x, y, 90, 90, null); break;
                case 1: g.drawImage(bildr, x, y, 90, 90, null); break;
                case 2: g.drawImage(bildu, x, y, 90, 90, null); break;
            }
        }
    }
    
    public void update()
    {
        int platz = weg[wegplatz];
        int yweg = (platz/16) * 90;
        int xweg = (platz%16) * 90;
        
        if (cs==0)
        {
            slowed=false;
        }
        if(cf==0)
        {
            speedup=false;
        }
        if(slowed==true)
        {
            speed_=speed/2;
            cs = cs-1;
        }
        if(speedup==true)
        {
            speed_=speed*3;
            cf = cf-1;
        }
        if(slowed==true && speedup==true)
        {
            speed_=speed*2;
            cs = cs-1;
            cf = cf-1;
        }
        if(slowed==false && speedup==false)
        {
            speed_=speed;
        }
        
        if(x==xweg&&y==yweg) {
            wegplatz++;
        }
        
        if(wegplatz==weg.length) {
            ziel = true;
        }else {
            platz = weg[wegplatz];
            yweg = (platz/16) * 90;
            xweg = (platz%16) * 90;
            if(x<xweg)
            {
                if(xweg-x<speed_) {
                    int temp = xweg-x;
                    x += temp;
                }else {
                    x+=speed_;
                }
                laufrichtung=1;
            }
            else if(y<yweg)
            {
                if(yweg-y<speed_) {
                    int temp = yweg-y;
                    y += temp;
                }else {
                    y+=speed_;
                }
                laufrichtung=2;
            }
            else if(y>yweg)
            {
                if(y-yweg<speed_) {
                    int temp = y-yweg;
                    y -= temp;
                }else {
                    y-=speed_;
                }
                laufrichtung=0;
            }
            bounds.x = x;
            bounds.y = y;
        }
    }
    
    /**
     * Eine Methode, um den Gegner zu stärken
     * 
     * @param a Die Anzahl der Leben
     */
    public void lebenHinzufuegen(int a) {
        leben += a;
    }
}
