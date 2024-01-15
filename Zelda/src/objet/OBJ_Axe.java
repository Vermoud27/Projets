package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Axe extends Entity {
    
    public OBJ_Axe(GamePanel gp) {

        super(gp);

        type = type_axe;
        name = "Hache";
        down1 = setup("../res/objets/axe",gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nPour couper du bois.";
    }
}