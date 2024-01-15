package objet;

import entity.Entity;
import jeu.GamePanel;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    int value = 5;
    
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = "Potion rouge";
        down1 = setup("../res/objets/potion_red",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nPotion de santé(+" + value + ").";
    }

    public void use(Entity entity){

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Vous buvez une " + name + "!\nVotre santé augmente de " + value + ".";
        entity.life += value;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(3);

    }
    
}
