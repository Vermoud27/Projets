package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Bouclier en Bois";
        down1 = setup("../res/objets/shield_wood",gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nFait avec du bois.";


    }
    
}
