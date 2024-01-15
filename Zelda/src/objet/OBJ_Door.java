package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Door extends Entity {
        
    public OBJ_Door(GamePanel gp) {

        super(gp);
        
        name = "Door";
        down1 = setup("../res/objets/door",gp.tileSize,gp.tileSize);
    }
}
