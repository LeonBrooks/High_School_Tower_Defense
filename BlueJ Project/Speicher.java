import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Alle Bilddateien
 * 
 * @author Oliver Schneider
 * @version 05.07
 */
public class Speicher{
    public Image[] bilder;
    public String[] dateien;
    
    /**
     * Der Konstruktor der Klasse Speicher.
     * Lädt alle Bilder und speichert sie in einer Liste ab.
     */
    public Speicher() {
        bilder = new Image[41];
        dateien = new String[41];
        
        
        dateien[0] = "bilder/Enemy1_facing_up.png";
        dateien[1] = "bilder/Enemy1_facing_right.png";
        dateien[2] = "bilder/Enemy1_facing_down.png";
        dateien[3] = "bilder/Tower_default_above.png";
        dateien[4] = "bilder/Tower_default_attack.png";
        dateien[5] = "bilder/Enemy2_facing_up.png";
        dateien[6] = "bilder/Enemy2_facing_right.png";
        dateien[7] = "bilder/Enemy2_facing_down.png";
        dateien[8] = "bilder/Map.png";
        dateien[9] = "bilder/Startmenu.png";
        dateien[10] = "bilder/Tower_ice_above.png";
        dateien[11] = "bilder/Tower_ice_attack.png";
        dateien[12] = "bilder/Enemy1freeze_facing_up.png";
        dateien[13] = "bilder/Enemy1freeze_facing_right.png";
        dateien[14] = "bilder/Enemy1freeze_facing_down.png";
        dateien[15] = "bilder/Enemy2freeze_facing_up.png";
        dateien[16] = "bilder/Enemy2freeze_facing_right.png";
        dateien[17] = "bilder/Enemy2freeze_facing_down.png";
        dateien[18] = "bilder/Enemy3_facing_up.png";
        dateien[19] = "bilder/Enemy3_facing_right.png";
        dateien[20] = "bilder/Enemy3_facing_down.png";
        dateien[21] = "bilder/Enemy3freeze_facing_up.png";
        dateien[22] = "bilder/Enemy3freeze_facing_right.png";
        dateien[23] = "bilder/Enemy3freeze_facing_down.png";
        dateien[24] = "bilder/Highscores.png";
        dateien[25] = "bilder/Name.png";
        dateien[26] = "bilder/trap_closed.png";
        dateien[27] = "bilder/trap_opened.png";
        dateien[28] = "bilder/Tower_fire_above.png";
        dateien[29] = "bilder/Tower_fire_attack.png";
        dateien[30] = "bilder/Tower_longrange_above.png";
        dateien[31] = "bilder/Tnt.png";
        dateien[32] = "bilder/Tnt_attack.png";
        dateien[33] = "bilder/Tower_thorn_above.png";
        dateien[34] = "bilder/Thornup.png";
        dateien[35] = "bilder/Thornright.png";
        dateien[36] = "bilder/Thorndown.png";
        dateien[37] = "bilder/Thornleft.png";
        dateien[38] = "bilder/Overlay.png";
        dateien[39] = "bilder/hilfe1.png";
        dateien[40] = "bilder/hilfe2.png";
        
        for(int i=0;i<dateien.length;i++) {
            try{
                bilder[i] = ImageIO.read(new File(dateien[i]));
            }catch(IOException e) {
                System.out.println("Fehler: " + dateien[i]);
            }
        }
    }
    
    /**
     * Aufrufen, um ein Bild aus dem Speicher zu holen
     * 
     * @param bildnummer Die festgelegte Nummer des Bildes
     * @return Das Bild der zugehörigen Nummer
     */
    public Image bild(int bildnummer) {
        return bilder[bildnummer];
    }
}
