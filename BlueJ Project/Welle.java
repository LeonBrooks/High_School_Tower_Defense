import java.util.Random;
/**
 * Erstellt Gegner in bestimmten Zeitabständen
 * 
 * @author Leon Brooks, Oliver Schneider
 * @version 05.07
 */
public class Welle
{
    private Spielablauf s;
    private int x,y,wellenZahl,gegnerStelle, gegnerZahl, cooldownG, cooldownW, warteW, warteG;
    private int[][]welle;
    private int [] wartezeitG, wartezeitW, gegner;
    private Random random;
    
    /**
     * Der Konstruktor der Klasse Welle
     *
     * @param s Der Spielablauf
     * @param x Die x-Koordinate (linke obere Ecke), an der die Gegner erstellt werden sollen
     * @param y Die y-Koordinate (linke obere Ecke), an der die Gegner erstellt werden sollen
     */
    public Welle(Spielablauf s, int x_, int y_)
    {
        this.s = s;
        
        //in einer Liste {Anzahl Typ 0, Anzahl Typ 1, ...} ist eine Welle
       welle = new int[][]{{1},{0,5},{5},{5,5},{10},{5},{0,0,1},{5},{0,20},{10,15},{10},{0,0,1},{0,10},{0,0,1},{10,10}, {0,0,0,1}, {0,0,0,2}, {1}};
        
        //die Wartezeit zwischen den jeweiligen Gegnern innerhalb der Welle
        wartezeitG = new int[]{1,1,1,1,1,1,1,1};
        
        //die Wartezeit vor dieser Welle
        wartezeitW = new int[]{10,50,75,75,100,100,0,0,100,150,175,0,0,0,0,225, 225,900,900,};
        x = x_;
        y = y_;
        
        reset();
        
        random = new Random();
    }
    
    /**
     * Erstellt nach einer Anzahl an Aufrufen neue Gegner
     */
    public void update()
    {
        if(gegnerZahl >= gegner[gegnerStelle]) {
            gegnerZahl=0;
            gegnerStelle++;
        }
        
        if(gegnerStelle >= gegner.length) {
            gegnerStelle = 0;
            wellenZahl ++;
            cooldownW = 0;
            
            if(wellenZahl<welle.length) {
                gegner = welle[wellenZahl];
                warteW = wartezeitW[wellenZahl];
                //warteG = wartezeitG[wellenZahl];
                warteG = 3;
            }else {
                gegner = zufallsWelle(3);
            }
        }
        
        if(cooldownW >= warteW)
        {   
             if(cooldownG >= warteG)
             {
                cooldownG = 0;
                s.neuerGegner(gegnerStelle, x, y);
                int ran = random.nextInt(wellenZahl*3+1);
                s.g[s.gegnerAnzahl-1].lebenHinzufuegen(ran);
                gegnerZahl++;

             }
             else
             {
                cooldownG ++;
             }
         }
         else
         {
            cooldownW++;
         }
    }
    
    /**
     * Setzt die Variablen auf ihre Startwerte zurück
     */
    public void reset()
    { 
        wellenZahl = 0;
        gegnerStelle = 0;
        gegnerZahl = 0;
        cooldownG = 0 ;
        cooldownW = 0;
        gegner = welle[0];
        warteW = wartezeitW[0];
        warteG = 5;
        //warteG = wartezeitG[0];
    }
    
    /**
     * Erstellt eine neue zufällige Welle
     * 
     * @param Wie viele GegnerTypen es gibt
     * @return Die Welle
     */
    public int[] zufallsWelle(int g) {
        int[] temp = new int[g];
        for(int i=0;i<g;i++) {
            temp[i] = random.nextInt(10+wellenZahl);
        }
        warteW = random.nextInt(50) + 150 - wellenZahl * 5;
        warteG = random.nextInt(5) + 3;
        return temp;
    }
}
