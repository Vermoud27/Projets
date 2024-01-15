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
    public int speed;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public BufferedImage attackUp,attackDown,attackLeft,attackRight;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invicible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;
    public int invicibleCounter =0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    public BufferedImage image,image2,image3;
    public String name;
    public boolean colision = false;
    
    // TYPE
    public int type; // joueur = 0, pnj = 1, monstre = 2
    public final int type_player = 0;
    public final int type_pnj = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;

    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;

    public int attackValue;
    public int defenseValue;
    public String description = "";

    public Entity(GamePanel gp){

        this.gp = gp;
    }

    public void setAction(){}
    public void damageReaction(){}
    public void speak(){
        
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
        case "up":
            direction = "down";
            break;
        case "down":
            direction = "up";
            break;
        case "left":
            direction = "right";
            break;
        case "right":
            direction = "left";
            break;
        }
    }

    public void use(Entity entity){}

    public void update(){

        setAction();

        collisionOn = false;
        gp.cChecker.checkTuile(this);
        gp.cChecker.checkObjet(this, false);
        gp.cChecker.checkEntity(this, gp.pnj);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true){
            if(gp.player.invicible == false){
                gp.playSE(6);
                
                int damage = attack - gp.player.defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.player.life -= damage;
                gp.player.invicible = true;
            }
        }

        if(collisionOn == false){

            switch(direction){
            case "up": worldY -= speed;break;
            case "down": worldY += speed;break;
            case "left": worldX -= speed;break;
            case "right": worldX += speed;break;
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
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum == 2){image = down2;}
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

                if(type == 2 && hpBarOn == true){
                    
                    double oneScale = (double) gp.tileSize/maxLife;
                    double hpBarValue = oneScale*life;
                    
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(screenX - 1, screenY - 11, gp.tileSize + 2, 10);

                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX, screenY - 10, (int) hpBarValue, 8);

                    hpBarCounter++;

                    if(hpBarCounter > 600){
                        hpBarCounter = 0;
                        hpBarOn = false;
                    }
                }

                if(invicible == true){
                    hpBarOn = true;
                    hpBarCounter = 0;
                    changeAlpha(g2, 0.4f);
                }
                if(dying == true){
                    dyingAnimation(g2);
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