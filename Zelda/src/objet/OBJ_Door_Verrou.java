package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Door_Verrou extends Entity{
        
    public OBJ_Door_Verrou(GamePanel gp) {

        super(gp);
        
        name = "Door_Verrou";
        down1 = setup("../res/objets/door_verrou",gp.tileSize,gp.tileSize);

        colision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
