package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Sword_Normal extends Entity{

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Epee Normal";
        down1 = setup("../res/objets/sword_normal",gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nUne épee des plus normal.";

    }
    

}
