import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
/**
 * Der Client für die Highscore Abfrage im TowerDefense
 *
 * @author Oliver Schneider
 * @version 26.07.17
 */
public class TDClient
{
    private String IP;  //27 Laptop, 42 PC
    private static int port = 123;
    
    private Socket clientSocket;
    private PrintWriter zumServer;
    private BufferedReader vomServer;
    
    public boolean connected;
    
    private String serverBotschaft;
    public String[][] highscores;
    
    /**
     * Der Konstruktor für den Client
     * 
     * @param ip Die IP des Servers
     */
    public TDClient(String ip)
    {
        connected = false;
        IP = ip;
        highscores = new String[10][2];
    }
    
    /**
     * Stellt die Verbindung zum Server her. connected wird gesetzt
     */
    public void verbindungHerstellen() {
        try{
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(IP,port),100);
            zumServer = new PrintWriter(clientSocket.getOutputStream(), true);
            vomServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            connected = true;
        }catch(IOException e) {
            e.printStackTrace();
            connected = false;
        }
    }
    
    /**
     * Beendet die Verbindung zum Server
     */
    private void verbindungBeenden() {
        try{
            vomServer.close();
            clientSocket.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sendet eine Nachricht zum Server
     * 
     * @param nachricht Die Nachricht
     */
    public void nachrichtSenden(String nachricht) {
        zumServer.println(nachricht);
    }
    
    /**
     * Empfängt die Highscores vom Server
     */
    public void highscoresLesen() {
        nachrichtSenden("highscoreslesen");
        try{
            serverBotschaft = vomServer.readLine();
        }catch(IOException e) {
            e.printStackTrace();
        }
        StringTokenizer t = new StringTokenizer(serverBotschaft,":");
        String[] temp = new String[10];
        for(int i=0;i<10;i++) {
            temp[i] = t.nextToken();
        }
        for(int i=0;i<10;i++) {
            StringTokenizer t2 = new StringTokenizer(temp[i],";");
            highscores[i][0] = t2.nextToken();
            highscores[i][1] = t2.nextToken();
        }
    }
    
    /**
     * Sendet eine neue Punktzahl an den Server
     * 
     * @param score der Name und die Punktzahl
     */
    public void neueScore(String[] score) {
        nachrichtSenden("neueScore:" + score[0] + ";" + score[1]);
    }
    
    
    /**
     * Beendet die Verbindung zum Server und informiert ihn davor
     */
    public void beenden() {
        nachrichtSenden("beenden");
        verbindungBeenden();
        connected = false;
    }
}
