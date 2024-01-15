package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Key extends Entity {
        
    public OBJ_Key(GamePanel gp) {

        super(gp);

        type = type_consumable;
        name = "Clé";
        down1 = setup("../res/objets/key",gp.tileSize,gp.tileSize);
        description = "[" + name + "]\nPour ouvrir une porte.";


    }
}
