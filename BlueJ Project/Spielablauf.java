import javax.swing.*;
import java.awt.*;

/**
 * Spielablauf: Überklasse zum Projekt Tower Defense
 * 
 * @author Oliver Schneider
 * @version 05.07
 */
public class Spielablauf extends Thread {
    private boolean paused, running, playing;
    public int geld, lebenBase, turmAnzahl, gegnerAnzahl, projektilAnzahl, punkte;
    public int[] weg, turmKosten;
    public Speicher speicher;
    
    private int updateZeit;
    
    private final int breite = 90*16;
    private final int hoehe = 90*10;
    
    private JFrame frame;
    private JPanel karten;
    private CardLayout layout;
    
    private Startmenue stM;
    private Spielfeld spielF;
    
    public HighscoreFenster hF;
    private NamenEingeben namenE;
    
    public Turm[] t;
    public Gegner[] g;
    public Projektil[] p;
    private Welle w;
    
    public Thread thread;
    
    /**
     * Konstruktor für die Hauptklasse
     */
    public Spielablauf () {
        weg = new int[]{64,65,81,97,98,99,83,67,51,35,19,20,21,22,23,39,55,71,87,103,104,105,89,73,57,41,42,43,59,75,91,107,108,109,93,77,78,79};
        
        turmKosten = new int[]{50,200,300,400,300,200,50};
        
        w = new Welle(this, 0, 360);
        speicher = new Speicher();
        
        hF = new HighscoreFenster(this);
        namenE = new NamenEingeben(this);
        
        updateZeit = 100;
        geld = 100;
        
        frame = new JFrame("Tower Defense - Q11");
        
        stM = new Startmenue(this, breite, hoehe);
        spielF = new Spielfeld(this, breite, hoehe);
        
        layout = new CardLayout();
        karten = new JPanel(layout);
        karten.add(stM);
        karten.add(spielF);
        
        thread = new Thread(this);
        running = true;
        thread.start();
        
        reset();
        
        frame.setResizable(false);
        frame.add(karten);
        frame.pack();
        frame.setIconImage(speicher.bild(2));
        
        //Da unerklärlicher Rand rechts und oben
        frame.setSize(breite+6, hoehe+29);
        
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);
    }
    
    /**
     * Die Spiel-Schleife, wird nicht direkt aufgerufen
     */
    public void run() {
        running = true;
        while(running) {
            while(playing) {
                long time1 = System.currentTimeMillis();
                if(!paused) {
                    update();
                    spielF.repaint();
                }
                if(lebenBase<=0) {
                    spielEnde();
                }
                long time2 = System.currentTimeMillis();
                long delay = updateZeit - (time2-time1);
                try{
                    Thread.sleep(delay);
                }catch (Exception e){}
            }
            try{
                Thread.sleep(500);
            }catch (Exception e){}
        }
    }
    
    /**
     * Alle Gegner und Türme updaten
     */
    public void update() {
        punkte += 1;
        for(int i=0;i<turmAnzahl;i++) {
            t[i].update();
        }
        for(int i=0;i<projektilAnzahl;i++) {
            p[i].update();
        }
        for(int i=0;i<gegnerAnzahl;i++) {
            g[i].update();
            if(g[i].leben<=0) {
                geld += g[i].geld;
                gegnerAnzahl--;
                g[i] = g[gegnerAnzahl];
                g[gegnerAnzahl] = null;
                i--;
                punkte += 100;
            }else if(g[i].ziel) {
                gegnerAnzahl--;
                g[i] = g[gegnerAnzahl];
                g[gegnerAnzahl] = null;
                i--;
                lebenBase--;
            }
        }
        w.update();
    }
    
    /**
     * Das Spiel pausieren
     */
    public void pause() {
        paused = true;
    }
    
    /**
     * Das Spiel weiterlaufen lassen
     */
    public void unpause() {
        updateZeit = 100;
        paused = false;
    }
    
    /**
     * Das Spiel in doppelter Geschwindigkeit laufen lassen
     */
    public void vorspulen() {
        updateZeit = 25;
    }
    
    /**
     * Erstellt einen neuen Turm
     *
     * @param typ Gibt an, welcher Turm platziert werden soll
     * @param x Die x-Koordinate (linke obere Ecke)
     * @param y Die y-Koordinate (linke obere Ecke)
     */
    public void neuerTurm(int typ, int x, int y) {
        boolean istKeiner = true;
        boolean istWeg = false;
        for(int i=0;i<turmAnzahl;i++) {
            if(t[i].x == x && t[i].y == y) {
                istKeiner = false;
            }
        }
        for(int i=0;i<weg.length;i++) {
            int tempy = weg[i]/16*90;
            int tempx = weg[i]%16*90;
            if(x == tempx && y == tempy) {
                istWeg = true;
            }
        }
        if(istKeiner&&!istWeg) {
            switch (typ) {
                case 0:
                    if(geld>=turmKosten[typ]) {
                        t[turmAnzahl] = new Laser(this, x, y);
                        turmAnzahl++;
                        geld = geld-turmKosten[typ];
                    }
                    break;
                case 1:
                    if(geld>=turmKosten[typ]) {
                        t[turmAnzahl] = new Eisturm(this, x, y);
                        turmAnzahl++;
                        geld = geld-turmKosten[typ];
                    }
                    break;
                case 2:
                    if(geld>=turmKosten[typ]) {
                        t[turmAnzahl] = new LaserErster(this, x, y);
                        turmAnzahl++;
                        geld = geld-turmKosten[typ];
                    }
                    break;
                case 3:
                    if(geld>=turmKosten[typ]) {
                        t[turmAnzahl] = new Dornturm(this, x, y);
                        turmAnzahl++;
                        geld = geld-turmKosten[typ];
                    }
                    break;
                case 4:
                    if(geld>=turmKosten[typ]) {
                        t[turmAnzahl] = new Turm(this, x, y);
                        turmAnzahl++;
                        geld = geld-turmKosten[typ];
                    }
                    break;
            }
            spielF.repaint();
        }else if(istKeiner) {
            switch (typ) {
                case 5:
                    if(geld>=turmKosten[typ]) {
                        t[turmAnzahl] = new Falltuer(this, x, y);
                        turmAnzahl++;
                        geld = geld-turmKosten[typ];
                    }
                    break;
                case 6:
                    if(geld>=turmKosten[typ]) {
                        t[turmAnzahl] = new TNT(this, x, y);
                        turmAnzahl++;
                        geld = geld-turmKosten[typ];
                    }
                    break;
            }
            spielF.repaint();
        }
    }
    
    /**
     * Entfernt einen Turm vom Spielfeld
     *
     * @param turm Ein bestimmter Turm
     */
    public void turmEntfernen(Turm turm) {
        for(int i=0;i<turmAnzahl;i++) {
            if(t[i] == turm) {
                turmAnzahl--;
                t[i] = t[turmAnzahl];
                t[turmAnzahl] = null;
                spielF.repaint();
            }
        }
    }
    
    /**
     * Entfernt einen Turm vom Spielfeld
     *
     * @param x Die x-Koordinate (linke obere Ecke)
     * @param y Die y-Koordinate (linke obere Ecke)
     */
    public void turmEntfernen(int x, int y) {
        for(int i=0;i<turmAnzahl;i++) {
            if((t[i].x == x) && (t[i].y == y)) {
                turmAnzahl--;
                t[i] = t[turmAnzahl];
                t[turmAnzahl] = null;
                spielF.repaint();
            }
        }
    }
    
    /**
     * Erstellt einen neuen Gegner
     *
     * @param typ Gibt an, welcher Gegner erstellt werden soll
     * @param x Die x-Koordinate (linke obere Ecke)
     * @param y Die y-Koordinate (linke obere Ecke)
     */
    public void neuerGegner(int typ, int x, int y) {
        switch(typ) {
            case 0:
                g[gegnerAnzahl] = new Gegner(x, y, weg, speicher);
                break;
            case 1:
                g[gegnerAnzahl] = new Gegner2(x, y, weg, speicher);
                break;
            case 2:
                g[gegnerAnzahl] = new SpeedZ(x, y, weg, speicher, this);
                break;
            case 3:
                g[gegnerAnzahl] = new Boss(x, y, weg, speicher);
                break;
        }
        gegnerAnzahl++;
    }
    
    /**
     * Erstellt ein Projektil
     *
     * @param typ Der Typ von Projektil
     * @param x Die x-Koordinate (linke obere Ecke)
     * @param y Die y-Koordinate (linke obere Ecke)
     * @param richtung Die Richtung des Projektils
     * @param speed Die Geschwindigkeit des Projektils
     * @param schaden Der Schaden des Projektils
     */
    public void projektilHinzufuegen(int typ, int x, int y, int richtung, int speed, int schaden) {
        switch(typ) {
            case 0:
                p[projektilAnzahl] = new Projektil(this,x,y,richtung,speed,schaden);
                break;
        }
        projektilAnzahl++;
    }
    
    /**
     * Entfernt eine Projektil vom Spielfeld
     *
     * @param pro Ein bestimmtes Projektil
     */
    public void projektilEntfernen(Projektil pro) {
        for(int i=0;i<projektilAnzahl;i++) {
            if(p[i] == pro) {
                projektilAnzahl--;
                p[i] = p[projektilAnzahl];
                p[projektilAnzahl] = null;
            }
        }
    }
    
    /**
     * Fügt eine Highscore zur Liste hinzu und ordnet sie ein
     *
     * @param name Der Name der Person
     * @param score Die Punktzahl der Person
     */
    public void scoreHinzufuegen(String name, int score) {
        hF.scoreHinzufuegen(name, score);
    }
    
    /**
     * Setzt die Variablen auf ihre Startwerte zurück
     */
    public void reset() {
        paused = true;
        playing = false;
        geld = 100;
        lebenBase = 10;
        turmAnzahl = 0;
        gegnerAnzahl = 0;
        projektilAnzahl = 0;
        punkte = 0;
        
        w.reset();
        
        t = new Turm[16*8];
        g = new Gegner[40];
        p = new Projektil[2000];
    }
    
    /**
     * Startet das Spiel, geht zum Spielfeld
     */
    public void spielStart() {
        layout.next(karten);
        spielF.requestFocusInWindow();
        playing = true;
    }
    
    /**
     * Beendet das Spiel, geht zum Startmenü
     */
    public void spielEnde() {
        playing = false;
        namenE.anzeigen(punkte);
        layout.previous(karten);
        reset();
    }
    
    /**
     * Schließt das Fenster
     */
    public void exit() {
        running = false;
        frame.dispose();
    }
    
    /**
     * Öffnet das Fenster
     */
    public static void main(String[] args) {
        Spielablauf s = new Spielablauf();
    }
}
