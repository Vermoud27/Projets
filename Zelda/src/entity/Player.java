package entity;

import jeu.GamePanel;
import jeu.KeyHandler;
import objet.OBJ_Key;
import objet.OBJ_Shield_Wood;
import objet.OBJ_Sword_Normal;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Player extends Entity {
    
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<Entity>();
    public final int inventorySize = 20;

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

        //attackArea.width = 36;
        //attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues(){

        worldX = gp.tileSize * 31;
        worldY = gp.tileSize * 29;
        speed = 4;
        direction = "down";

        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1; // + de force = plus de dégat
        dexterity = 1; // + de dexterité = plus de defense
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack(); // Valeur d'attaque total en fonction de la force
        defense = getDefense(); // Valeur de défense total en fonction de la dextérité
    }

    public void setItems(){

        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage(){

        up1 = setup("../res/Player/link_up1",gp.tileSize,gp.tileSize);
        up2 = setup("../res/Player/link_up2",gp.tileSize,gp.tileSize);
        down1 = setup("../res/Player/link_down1",gp.tileSize,gp.tileSize);
        down2 = setup("../res/Player/link_down2",gp.tileSize,gp.tileSize);
        left1 = setup("../res/Player/link_left1",gp.tileSize,gp.tileSize);
        left2 = setup("../res/Player/link_left2",gp.tileSize,gp.tileSize);
        right1 = setup("../res/Player/link_right1",gp.tileSize,gp.tileSize);
        right2 = setup("../res/Player/link_right2",gp.tileSize,gp.tileSize);
    }

    public void getPlayerAttackImage(){
        
        if(currentWeapon.type == type_sword){
            attackDown = setup("../res/Player/link_attack_down",gp.tileSize,gp.tileSize*2);
            attackUp = setup("../res/Player/link_attack_up",gp.tileSize,gp.tileSize*2);
            attackLeft = setup("../res/Player/link_attack_left",gp.tileSize*2,gp.tileSize);
            attackRight = setup("../res/Player/link_attack_right",gp.tileSize*2,gp.tileSize);
        }
        if(currentWeapon.type == type_axe){
            attackDown = setup("../res/Player/link_axe_down",gp.tileSize,gp.tileSize*2);
            attackUp = setup("../res/Player/link_axe_up",gp.tileSize,gp.tileSize*2);
            attackLeft = setup("../res/Player/link_axe_left",gp.tileSize*2,gp.tileSize);
            attackRight = setup("../res/Player/link_axe_right",gp.tileSize*2,gp.tileSize);
        }

    }

    public void update(){
        
        if(attacking == true){
            attacking();
        }
        else if(keyH.upPressed == true || keyH.downPressed == true || 
                keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){
            if(keyH.upPressed == true){
                direction = "up";
            }
            if(keyH.downPressed == true){
                direction = "down";
            }
            if(keyH.leftPressed == true){
                direction = "left";
            }
            if(keyH.rightPressed == true){
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTuile(this);

            int objIndex = gp.cChecker.checkObjet(this, true);
            pickUpObjet(objIndex);

            int pnjIndex = gp.cChecker.checkEntity(this, gp.pnj);
            interactPNJ(pnjIndex);

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            gp.eHandler.checkEvent();

            if(collisionOn == false && keyH.enterPressed == false){

                switch(direction){
                case "up": worldY -= speed;break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed;break;
                case "right": worldX += speed;break;
                }
            }

            if(keyH.enterPressed == true && attackCanceled == false){
                attacking = true;
                spriteCounter = 0;
            }
            else{
                attacking = false;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;

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

    public void attacking(){

        spriteCounter++;

        spriteNum = 1;

        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;

        switch(direction){
        case "up" : worldY -= attackArea.height; break;
        case "down" : worldY += attackArea.height; break;
        case "left" : worldX -= attackArea.width; break;
        case "right" : worldX += attackArea.width; break;
        }

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;

        int monsterindex = gp.cChecker.checkEntity(this, gp.monster);
        damageMonster(monsterindex);

        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;

        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }   

    public void pickUpObjet(int i){
        
        if(i != 999){

            String text;

            if(inventory.size() != inventorySize){

                inventory.add(gp.obj[i]);
                gp.playSE(1);
                text = "Tu as trouvé une " + gp.obj[i].name + " !";
            }
            else{
                text = "Ta sacoche est pleine";
            }
            gp.ui.addMessage(text);
            gp.obj[i] = null;
        }
    }

    public void interactPNJ(int i){

        if(gp.keyH.enterPressed == true){
            if(i != 999){
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.pnj[i].speak();
            }
            else{
                attacking = true;
            }
        }
    }

    public void contactMonster(int i){

        if(i != 999){
            
            if(invicible == false){
                gp.playSE(6);

                int damage = gp.monster[i].attack - defense;
                if(damage < 0){
                    damage = 0;
                }

                life -= damage;
                invicible = true;
            }
            
        }
    }

    public void damageMonster(int i){

        if(i != 999){

            if(gp.monster[i].invicible == false){

                gp.playSE(5);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0){
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " degat!");


                gp.monster[i].invicible = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("Vous avez tué un " + gp.monster[i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp(){

        if(exp >= nextLevelExp){

            level++;
            nextLevelExp = nextLevelExp *2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "Tu es passé niveau " + level + "!";
        }
    }

    public void selectItem(){

        int itemIndex = gp.ui.getItemIndexOnSlot();

        if(itemIndex < inventory.size()){

            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe){

                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){

                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){

                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    } 

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction){
        case "up":
            if(attacking == false){
                if(spriteNum == 1){image = up1;}
                if(spriteNum == 2){image = up2;}
            }
            if(attacking == true){
                tempScreenY = screenY - gp.tileSize;
                if(spriteNum == 1){image = attackUp;}
            }
            break;
        case "down":
            if(attacking == false){
                if(spriteNum == 1){image = down1;}
                if(spriteNum == 2){image = down2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackDown;}
            }
            break;
        case "left":
            if(attacking == false){
                if(spriteNum == 1){image = left1;}
                if(spriteNum == 2){image = left2;}
            }
            if(attacking == true){
                tempScreenX = screenX - gp.tileSize;
                if(spriteNum == 1){image = attackLeft;}
            }
            break;
        case "right":
            if(attacking == false){
                if(spriteNum == 1){image = right1;}
                if(spriteNum == 2){image = right2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackRight;}
            }
            break;
        }
        
        if(invicible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); 
    }
}
