package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Boot extends Entity {
    
    public OBJ_Boot(GamePanel gp) {

        super(gp);

        name = "Boot";
        down1 = setup("../res/objets/boot",gp.tileSize,gp.tileSize);
    }
}
