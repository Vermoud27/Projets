package entity;

import jeu.GamePanel;
import jeu.KeyHandler;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Player extends Entity {
    
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    private int nbSaut;
    private int compteurSaut;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);
        
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        this.nbSaut = 0;
        this.compteurSaut = 0;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){

        worldX = gp.tileSize * 8;
        worldY = gp.tileSize * 9 -1;
        xSpeed = 4;
        direction = "right";
    }

    public void getPlayerImage(){

        left1 = setup("../res/Player/link_left1",gp.tileSize,gp.tileSize);
        left2 = setup("../res/Player/link_left2",gp.tileSize,gp.tileSize);
        right1 = setup("../res/Player/link_right1",gp.tileSize,gp.tileSize);
        right2 = setup("../res/Player/link_right2",gp.tileSize,gp.tileSize);
    }

    public void update(){
    
    if(worldY < 430 && collisionOn == false){
            worldY += 2;
            direction = "down";
    }

    if(keyH.spacePressed == true){
        compteurSaut++;
        nbSaut++;

        if(compteurSaut < 20 && nbSaut < 2){
            worldY -= 6;
            direction = "up";
        }
        else{
            compteurSaut = 0;
            nbSaut = 0;
            keyH.spacePressed = false;
        }
    }
    
    if(keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){
        if(keyH.leftPressed == true){
            direction = "left";
        }
        if(keyH.rightPressed == true){
            direction = "right";
        }

        collisionOn = false;
        gp.cChecker.checkTuile(this);

        if(collisionOn == false){

            switch(direction){
            case "left": worldX -= xSpeed;break;
            case "right": worldX += xSpeed;break;
            }
        }

        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

        if(invicible == true){
            invicibleCounter++;
            if(invicibleCounter > 60){
                invicible = false;
                invicibleCounter = 0;
            }
        }
        
    }  

    public void contactMonster(int i){
       
    }

    public void damageMonster(int i){

    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction){
        case "up":
            if(spriteNum == 1){image = left1;}
            if(spriteNum == 2){image = right1;}
            break;
        case "down":
            if(spriteNum == 1){image = left2;}
            if(spriteNum == 2){image = right2;}
            break;
        case "left":
            if(spriteNum == 1){image = left1;}
            if(spriteNum == 2){image = left2;}
            break;
        case "right":
            if(spriteNum == 1){image = right1;}
            if(spriteNum == 2){image = right2;}
            break;
        }
        
        if(invicible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); 
    }
}

