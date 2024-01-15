package entity;

import java.util.Random;

import jeu.GamePanel;

public class PNJ_OldMan extends Entity {
    
    public PNJ_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage(){

        up1 = setup("../res/PNJ/oldman_up1",gp.tileSize,gp.tileSize);
        up2 = setup("../res/PNJ/oldman_up2",gp.tileSize,gp.tileSize);
        down1 = setup("../res/PNJ/oldman_down1",gp.tileSize,gp.tileSize);
        down2 = setup("../res/PNJ/oldman_down2",gp.tileSize,gp.tileSize);
        left1 = setup("../res/PNJ/oldman_left1",gp.tileSize,gp.tileSize);
        left2 = setup("../res/PNJ/oldman_left2",gp.tileSize,gp.tileSize);
        right1 = setup("../res/PNJ/oldman_right1",gp.tileSize,gp.tileSize);
        right2 = setup("../res/PNJ/oldman_right2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue(){

        dialogues[0] = "Bonjour, jeune aventurier.";
        dialogues[1] = "Tu es venu sur cette ile pour trouver\nle trésor?";
        dialogues[2] = "Autrefois, j'étais un grand sorcier...\nMais maintenant je suis trop vieux pour\npartir à l'aventure.";
        dialogues[3] = "Eh bien, Bonne chance à toi.";
    }
    
    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if( i <= 25){
                direction = "up";
            }
            if( i > 25 && i<= 50){
                direction = "down";
            }
            if( i > 50 && i<= 75){
                direction = "left";
            }
            if( i > 75 && i <= 100){
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void speak(){
        super.speak();
    }
}
