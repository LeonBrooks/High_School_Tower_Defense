import java.awt.*;
/**
 * Verschluckt in regelmäßigen Abständen einen Gegner
 * 
 * @author Leon Brooks 
 * @version 06.07.2017
 */
public class Falltuer extends Turm
{
    
    public Falltuer(Spielablauf s_, int x_, int y_)
    {
        super(s_, x_, y_);
        radius = 0;
        schaden = 1000;
        speed = 150;
        angriffdauer = 75;
        
        cooldown = speed;
    }
    
    protected void bildSetzen() {
        bild = s.speicher.bild(27);
        angriffsbild = s.speicher.bild(26);
    }
    
    protected void angriff() {
        for(int i=0;i<s.gegnerAnzahl;i++)
        {
            if(s.g[i].x==x && s.g[i].y==y)
            {
                effekt(i);
                a = true;
                cooldown = 0;
                angriffcooldown = 0;
            }
        }
    }
    
    
}
