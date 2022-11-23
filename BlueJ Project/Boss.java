import java.awt.*;
/**
 * Beschreiben Sie hier die Klasse Boss.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Boss extends Gegner
{

    /**
     * Konstruktor für Objekte der Klasse Boss
     */
    public Boss(int x_, int y_, int[] weg_, Speicher s_)
    {
        super(x_,y_,weg_,s_);
        speed = 5;
        leben = 8000;
        geld = 2000;
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public void update()
    {
        
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
            speed_=speed*2;
            cf = cf-1;
        }
        if(slowed==true && speedup==true)
        {
            speed_=speed;
            cs = cs-1;
            cf = cf-1;
        }
        if(slowed==false && speedup==false)
        {
            speed_=speed;
        }
        
        x += speed_;
        if(x==1440&&y==360) {
            ziel = true;
        }
    }
    
    public void paint(Graphics g)
    {
        if(slowed) {
            g.drawImage(freezer, x-10, y-10, 90+20, 90+20, null);
        } else {
            g.drawImage(bildr, x-10, y-10, 90+20, 90+20, null);
        }
    }
}

