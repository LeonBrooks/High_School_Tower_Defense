
/**
 * Ein Gegner der sich und andere beschleunigt
 * 
 * @author Leon Brooks 
 * @version 06.07.2017
 */
class SpeedZ extends Gegner
{
    private int radius;
    private Spielablauf spielA;
    /**
     * Konstruktor für Objekte der Klasse SpeedZ
     */
    SpeedZ(int x_, int y_, int[] weg_, Speicher s_, Spielablauf spiel)
    {
        super( x_, y_, weg_,s_);
        leben = 40;
        speed = 15;
        radius = 2;
        spielA = spiel;
        geld = 50;
    }
    
    public void bilderSetzen() {
        bildo = s.bild(18);
        bildr = s.bild(19);
        bildu = s.bild(20);
        freezeo = s.bild(21);
        freezer = s.bild(22);
        freezeu = s.bild(23);
    }

    public void update()
    {
        super.update();
 
         for(int i=spielA.gegnerAnzahl;i>0;i--)
           {
             if(((spielA.g[i-1].x<=x+90+radius*90)&&(spielA.g[i-1].x>=x-radius*90))&&((spielA.g[i-1].y<=y+90+radius*90)&&(spielA.g[i-1].y>=y-radius*90)))
               {
                    spielA.g[i-1].speedup=true;
                    spielA.g[i-1].cf=10;
               }
            }
    }
}
