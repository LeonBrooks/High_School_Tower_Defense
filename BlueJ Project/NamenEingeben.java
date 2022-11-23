import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Eingeben des Namens für die Highscores
 *
 * @author Oliver Schneider
 * @version 05.07
 */
public class NamenEingeben extends JPanel
{
    private JFrame frame;
    private int breite, hoehe, score;
    private String name;
    private Spielablauf s;
    private Image bild;

    /**
     * Konstruktor für die Klasse
     */
    public NamenEingeben(Spielablauf s)
    {
        this.s = s;
        breite = 1000;
        hoehe = 100;
        setPreferredSize(new Dimension(breite,hoehe));
        
        frame = new JFrame("Neue Highscore!");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setIconImage(s.speicher.bild(2));
        
        //Da unerklärlicher Rand rechts und oben
        frame.setSize(breite+6, hoehe+29);
        
        frame.setLocationRelativeTo(null);
        
        name = "name";
        
        addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent e) {}
            
            public void keyPressed(KeyEvent e) {
                if(name.equals("name")){
                    name = "";
                }else if(name.equals("dev")) {
                    name = "[DEV]";
                }
                if(e.getKeyCode()==10) {
                    s.scoreHinzufuegen(name, score);
                    name = "name";
                    frame.setVisible(false);
                    repaint();
                }
            }
            
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode()!=8&&name.length()<20&&Character.isLetter(e.getKeyChar())) {
                    name += e.getKeyChar();
                    repaint();
                }else {
                    name = name.substring(0, name.length()-1);
                    repaint();
                }
            }
            
        });
        requestFocusInWindow();
        
        bild = s.speicher.bild(25);
    }
    
    /**
     * Zeichnet den geschriebenen Namen
     */
    public void paint(Graphics g) {
        g.drawImage(bild,0,0,breite,hoehe,null);
        g.setColor(Color.BLACK);
        Font small = new Font("Showcard Gothic", Font.TRUETYPE_FONT,50);
        g.setFont(small);
        g.drawString(name,27,73);
    }
    
    /**
     * Zeigt das Fenster
     * 
     * @param score Die Punktzahl des Spielers
     */
    public void anzeigen(int score) {
        this.score = score;
        frame.setVisible(true);
    }
}
