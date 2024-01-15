package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import jeu.GamePanel;
import jeu.UtilityTool;

public class Entity {

    GamePanel gp;
    
    public int worldX, worldY;
    public int xSpeed;
    public int gravite;

    public BufferedImage left1,left2,right1,right2;
    public String direction = "right";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invicible = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    public int invicibleCounter =0;
    public BufferedImage image,image2,image3;
    public String name;
    public boolean colision = false;
    public int type; // joueur = 0, pnj = 1, monstre = 2

    public Entity(GamePanel gp){

        this.gp = gp;
    }

    public void setAction(){}
    public void damageReaction(){}

    public void update(){

        setAction();

        collisionOn = false;
        gp.cChecker.checkTuile(this);
        gp.cChecker.checkEntity(this, gp.pnj);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        /*if(this.type == 2 && contactPlayer == true){
            if(gp.player.invicible == false){
                gp.playSE(6);
                
                int damage = attack - gp.player.defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.player.life -= damage;
                gp.player.invicible = true;
            }
        }*/

        if(collisionOn == false){

            switch(direction){
            case "left": worldX -= xSpeed;break;
            case "right": worldX += xSpeed;break;
            }
        }

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

        if(invicible == true){
            invicibleCounter++;
            if(invicibleCounter > 40){
                invicible = false;
                invicibleCounter = 0;
            }
        }
    }
    
    public void draw(Graphics2D g2){
        
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                switch(direction){
                case "up":
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                    break;
                case "down":
                    if(spriteNum == 1){image = right1;}
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

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            changeAlpha(g2, 1f);


        }
    }

    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;

        int i = 5;

        if(dyingCounter <= i){changeAlpha(g2, 0f);}
        if(dyingCounter > i && dyingCounter <= i*2){changeAlpha(g2, 1f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3){changeAlpha(g2, 0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4){changeAlpha(g2, 1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5){changeAlpha(g2, 0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6){changeAlpha(g2, 1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7){changeAlpha(g2, 0f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8){changeAlpha(g2, 1f);}
        if(dyingCounter > i*8){
            dying = false;
            alive = false;
        } 
    }

    public void changeAlpha(Graphics2D g2, float alphaValue){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }

    public BufferedImage setup(String imagePath, int width, int height){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaleImage(image,width,height);

        }catch(Exception e){
            e.printStackTrace();
        }
        return image;

    }

}

