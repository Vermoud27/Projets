package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Chest extends Entity{
        
    public OBJ_Chest(GamePanel gp) {

        super(gp);
        
        name = "Chest";
        down1 = setup("../res/objets/chest",gp.tileSize,gp.tileSize);

        colision = true;
    }
}
