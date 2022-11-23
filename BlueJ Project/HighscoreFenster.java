import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.lang.NullPointerException;
import java.awt.*;
import java.io.File;

/**
 * Das Fenster zeigt die Highscores an
 * 
 * @author Oliver Schneider
 * @version 05.07
 */
public class HighscoreFenster extends JPanel
{
    private JFrame frame;
    private String s;
    private String[] e;
    private String[][] highscoresL, highscoresO;
    private Spielablauf spiel;
    private int breite, hoehe;
    private Image bild;
    private TDClient client;
    private boolean online;
    
    /**
     * Konstruktor für das Highscorefenster
     * 
     * @param spiel Der Spielablauf
     */
    public HighscoreFenster(Spielablauf spiel){
        File data = new File("daten/");
        if (!data.exists()) {
            data.mkdirs();
        }
        s = "daten/highscore.txt";
        highscoresL = new String[10][2];
        highscoresO = new String[10][2];
        this.spiel = spiel;
        
        breite = 750;
        hoehe = 350;
        setPreferredSize(new Dimension(breite, hoehe));
        
        frame = new JFrame("Highscores");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setIconImage(spiel.speicher.bild(2));
        
        //Da unerklärlicher Rand rechts und oben
        frame.setSize(breite+6, hoehe+29);
        
        frame.setLocationRelativeTo(null);
        
        bild = spiel.speicher.bild(24);
        
        FileReader fr = null;
        try{
            fr = new FileReader("daten/IP.txt");
            
            BufferedReader br = new BufferedReader(fr);
            
            client = new TDClient(br.readLine());
            br.close();
        }catch(FileNotFoundException f){
            System.out.println("FEHLER! KEINE IP");
            
            client = new TDClient("127.0.0.1");
        }catch(IOException e) {
            e.printStackTrace();
        }
        
        start();
    }
    
    /**
     * Zeigt das Fenster an
     */
    public void anzeigen() {
        client.verbindungHerstellen();
        if(client.connected) {
            onlineScores();
            client.beenden();
            online = true;
        } else {
            online = false;
        }
        repaint();
        frame.setVisible(true);
    }
    
    /**
     * Versteckt das Fenster
     */
    public void verstecken() {
        frame.setVisible(false);
    }
    
    /**
     * Zeichnet die Highscores
     * 
     * @param g Das Graphics Objekt
     */
    public void paint(Graphics g) {
        g.drawImage(bild,0,0,breite,hoehe,null);
        g.setColor(Color.BLACK);
        Font small = new Font("Showcard Gothic", Font.TRUETYPE_FONT,25);
        Font n = new Font("Showcard Gothic", Font.TRUETYPE_FONT,30);
        for(int i=0;i<10;i++) {
            if(online) {
                g.setFont(small);
                g.drawString("Highscores - Online",208,25);
                g.setFont(n);
                g.drawString(i+1 + ". " + highscoresO[i][0] + ": " + highscoresO[i][1],25,i*30+55);
            }else {
                g.setFont(small);
                g.drawString("Highscores - Offline",208,25);
                g.setFont(n);
                g.drawString(i+1 + ". " + highscoresL[i][0] + ": " + highscoresL[i][1],25,i*30+55);
            }
        }
    }
    
    /**
     * Fügt eine Punktzahl hinzu (online oder offline)
     * 
     * @param name Der Name des Spielers
     * @param score Die Punktzahl des Spielers
     */
    public void scoreHinzufuegen(String name, int score) {
        client.verbindungHerstellen();
        if(client.connected) {
            client.neueScore(new String[]{name,Integer.toString(score)});
            client.beenden();
        }else {
            offlineHighscoreHinzufuegen(name, score);
        }
    }
    
    /**
     * Fügt eine Score offline hinzu
     * 
     * @param name Der Name des Spielers
     * @param score Die Punktzahl des Spielers
     */
    public void offlineHighscoreHinzufuegen(String name, int score) {
        int temp = 10;
        for(int i=highscoresL.length-1;i>=0;i--) {
            if(score > Integer.parseInt(highscoresL[i][1])) {
                temp = i;
            }
        }
        
        if(temp!=10) {
            for(int i=9;i>temp;i--) {
                highscoresL[i][0] = highscoresL[i-1][0];
                highscoresL[i][1] = highscoresL[i-1][1];
            }
            highscoresL[temp][0] = name;
            highscoresL[temp][1] = ""+score;
        }
    }
    
    /**
     * Lässt den Client die Highscores auslesen und speichert sie
     */
    public void onlineScores() {
        if(client.connected) {
            client.highscoresLesen();
            for(int i=0;i<10;i++) {
                highscoresO[i][0] = client.highscores[i][0];
                highscoresO[i][1] = client.highscores[i][1];
            }
        }
    }
    
    /**
     * Setzt die Variablen auf die gespeicherten Werte (Offline)
     */
    public void start() {
        e = readAll();
        tokenize();
    }
    
    /**
     * Liest die Scores aus der Datei
     */
    public String[] readAll()
    {
        String[] temp = new String[10];
        PrintWriter pW;
        FileReader fr = null;
        try{
            fr = new FileReader(s);
        }catch(FileNotFoundException f){
            try{
                pW = new PrintWriter(new BufferedWriter(new FileWriter(s)));
                for(int i=0;i<10;i++)
                {
                    pW.println("NoScore;0");
                }
                
                pW.close();
                try{
                    fr = new FileReader(s);
                }catch(FileNotFoundException fi){
                }
            }catch(IOException i){
            }
        }
            
        BufferedReader br = new BufferedReader(fr);
        
        for(int i=0;i<10;i++)
        {
            try{
                temp[i] = br.readLine();
            }catch(IOException x){}    
        }
        
        try{
            br.close();
        }catch(IOException x){
                 
        }
        
        return temp;
    }
    
    /**
     * Trennt die gelesenen Strings beim ;
     */
    public void tokenize()
    {
        for(int i=0;i<10;i++)
        {
            StringTokenizer t = new StringTokenizer(e[i],";");
            highscoresL[i][0] = t.nextToken();
            highscoresL[i][1] = t.nextToken();
        }
    }
    
    /**
     * Speichert die Highscores in der Datei
     */
    public void writeAll()
    {
        PrintWriter pW = null;
        try{
            pW = new PrintWriter(new BufferedWriter(new FileWriter(s)));
        }catch(IOException i){}
        
        for(int i=0;i<10;i++)
        {
            pW.println(highscoresL[i][0] + ";" + highscoresL[i][1]);
        }
        
        pW.close();
    }
}
