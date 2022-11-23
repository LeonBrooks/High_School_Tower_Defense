
/**
 * zweiter Gegner der schneller läuft und weniger Leben hat
 * 
 * @author Oliver Schneider 
 * @version 06.07.2017
 */
public class Gegner2 extends Gegner
{

    public Gegner2(int x_, int y_, int[] weg_, Speicher s_)
    {
        super(x_,y_,weg_,s_);
        speed = 30;
        leben = 50;
        geld = 10;
    }
    
    public void bilderSetzen() {
        bildo = s.bild(5);
        bildr = s.bild(6);
        bildu = s.bild(7);
        freezeo = s.bild(15);
        freezer = s.bild(16);
        freezeu = s.bild(17);
    }
}
