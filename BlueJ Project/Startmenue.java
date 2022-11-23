import javax.swing.*;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.*;


/**
 * Startmenü
 * 
 * @author Elias Djossou
 * @version 06.07.2017
 */
public class Startmenue extends JPanel {
    private HighscoreFenster f;
    private Spielablauf s;
    private Image image, hilfe1, hilfe2;
    private int seite;
    
    /**
     * Konstruktor für das Startmenü
     * 
     * @param s Der Spielablauf
     * @param breite Die Breite des Menüs
     * @param hoehe Die Höhe des Menüs
     */
    Startmenue (Spielablauf s, int breite, int hoehe)
    {
        setPreferredSize(new Dimension(breite,hoehe));
        f = s.hF;
        
        seite = 0;
        image = s.speicher.bild(9);
        hilfe1 = s.speicher.bild(39);
        hilfe2 = s.speicher.bild(40);
        
        addMouseListener(new MouseListener() {
            
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                
                if((x > 360 && x < 1080) && (y > 270 && y < 360) && seite == 0) {
                    s.spielStart();
                }
                if ((x > 360 && x < 1080) && (y > 450 && y < 540) && seite == 0) {
                    s.exit();
                }
                if ((x > 360 && x < 1080) && (y > 630 && y < 720) && seite == 0) {
                    f.anzeigen();
                }
                if (x > 1350 && y < 180 && seite == 0) {
                    seite = 1;
                    repaint();
                }
                if (x > 1260 && y > 810 && seite < 2) {
                    seite++;
                    repaint();
                }
                if (x < 180 && y > 810 && seite > 0) {
                    seite--;
                    repaint();
                }
            }
            
            public void mouseExited(MouseEvent e) {}
            
            public void mouseEntered(MouseEvent e) {}
            
            public void mouseClicked(MouseEvent e) {}
            
            public void mouseReleased(MouseEvent e) {}
        });
    }
    
    /**
     * Zeichnet das Menü
     * 
     * @param g Das Graphics Objekt
     */
    public void paint(Graphics g) {
        switch(seite) {
            case 0:
                g.drawImage(image,0,0,90*16,90*10,null);
                break;
            case 1:
                g.drawImage(hilfe1,0,0,90*16,90*10,null);
                break;
            case 2:
                g.drawImage(hilfe2,0,0,90*16,90*10,null);
                break;
        }
    }
}
