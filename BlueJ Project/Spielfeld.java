import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Hier läuft das Spiel ab. Die Türme und Gegner werden gezeichnet.
 * 
 * @author Elias
 * @version 05.07
 */
public class Spielfeld extends JPanel
{
    private int breite, hoehe, selected;
    private Spielablauf s;
    private Image image, overlay;
    int typ;
    int feldNr;
    
    /**
     * Konstruktor für das Spielfeld
     * 
     * @param s Der Spielablauf
     * @param breite Die Breite des Spielfelds
     * @param hoehe Die Höhe des Spielfelds
     */
    public Spielfeld(Spielablauf s,int breite, int hoehe)
    {
        this.s = s;
        this.breite = breite;
        this.hoehe = hoehe;
        
        setPreferredSize(new Dimension(breite,hoehe));

        image = s.speicher.bild(8); 
        overlay = s.speicher.bild(38);

        addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                typ = 0;
                
                feldNr = feldNrFinder(e.getX(), e.getY());
                
                switch (feldNr) {
                    case 16: {s.unpause();
                              typ = 100;} break;
                    case 17: {s.pause();
                              typ = 100;} break;
                    case 18: {s.vorspulen();
                              typ = 100;} break;
                    case 1: {typ = 0;} break;
                    case 2: {typ = 1;} break;
                    case 3: {typ = 2;} break;
                    case 4: {typ = 3;} break;
                    case 5: {typ = 4;} break;
                    case 6: {typ = 5;} break;
                    case 7: {typ = 6;} break;
                    case 8: {
                        typ = 200;
                    } break;
                    default: typ = 100;
                }
            }
            
            public void mouseExited(MouseEvent e) {}
            
            public void mouseEntered(MouseEvent e) {}
            
            public void mouseClicked(MouseEvent e) {}
            
            public void mouseReleased(MouseEvent e) {
                if ((typ < 100) && (e.getY() < 720) && (e.getX() < 1350)){
                    s.neuerTurm(typ, e.getX()/90*90, e.getY()/90*90);
                }else if(typ == 200) {
                    s.turmEntfernen(e.getX()/90*90, e.getY()/90*90);
                }
            }
            
        });
        
        addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent e) {}
            
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    s.spielEnde();
                }
            }
            
            public void keyTyped(KeyEvent e) {}
            
        });
    }
    
    /**
     * Zeichnet das Spielfeld
     * 
     * @param g Das Graphics Objekt
     */
    public void paint(Graphics g) {
        g.drawImage(image,0,0,90*16,90*10,null);
        
        
        for(int i=0;i<s.turmAnzahl;i++) {
            s.t[i].paint(g);
        }
        for(int i=0;i<s.projektilAnzahl;i++) {
            s.p[i].paint(g);
        }
        for(int i=0;i<s.gegnerAnzahl;i++) {
            s.g[i].paint(g);
        }
        
        g.drawImage(overlay,0,720,1440,180,null);
        
        Font test = new Font("Showcard Gothic", Font.TRUETYPE_FONT,90);
        Font small = new Font("Showcard Gothic", Font.TRUETYPE_FONT,30);
        g.setFont(test);
        g.setColor(Color.YELLOW);
        g.drawString("X  " + s.geld, 455, 890);
        g.setColor(Color.RED);
        g.drawString("X  " + s.lebenBase, 995, 890);
        
        g.setColor(Color.GRAY);
        g.fillRect(1170,0,400,40);
        g.setFont(small);
        g.setColor(Color.BLACK);
        g.drawString("Points: " + s.punkte, 1200, 30);
        
        Font towerKo = new Font("Showcard Gothic", Font.TRUETYPE_FONT,10);
        g.setColor(Color.BLACK);
        
        for(int i=0;i<7;i++) {
            g.drawString(Integer.toString(s.turmKosten[i]),120+i*180,780);
        }
        
        
    }
    
    /**
     * Findet den Platz in der Auswahlleiste
     * 
     * @param xpos Die x-Koordinate
     * @param ypos Die y-Koordinate
     */
    private int feldNrFinder(int xpos, int ypos) {
        
        if ((xpos > 90 && xpos < 180) && (ypos > 810 && ypos < 900)) { // pause Button
            return 17;
        } else if ((xpos > 0 && xpos < 90) && (ypos > 810 && ypos < 900)) { // play Button
            return 16;
        } else if ((xpos > 180 && xpos < 270) && (ypos > 810 && ypos < 900)) {
            return 18;
        } else if ((xpos > 0 && xpos < 90) && (ypos > 720 && ypos < 810)) { // Tower1
            return 1;
        } else if ((xpos > 180 && xpos < 270) && (ypos > 720 && ypos < 810)) { // Tower2
            return 2;
        } else if ((xpos > 360 && xpos < 450) && (ypos > 720 && ypos < 810)) { // Tower3
            return 3;
        } else if ((xpos > 540 && xpos < 630) && (ypos > 720 && ypos < 810)) { // Tower4
            return 4;
        } else if ((xpos > 720 && xpos < 810) && (ypos > 720 && ypos < 810)) { // Tower5
            return 5;
        } else if ((xpos > 900 && xpos < 990) && (ypos > 720 && ypos < 810)) { // Tower6
            return 6;
        } else if ((xpos > 1080 && xpos < 1170) && (ypos > 720 && ypos < 810)) { // Tower7
            return 7;
        } else if ((xpos > 1260 && xpos < 1350) && (ypos > 720 && ypos < 810)) { // Tower8
            return 8;
        } else {
            return 100;
        }
       
    }
}
