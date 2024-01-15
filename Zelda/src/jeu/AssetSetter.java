package jeu;

import entity.PNJ_OldMan;
import monster.MON_GreenSlime;
import objet.OBJ_Axe;
import objet.OBJ_Door_Verrou;
import objet.OBJ_Key;
import objet.OBJ_Potion_Red;

public class AssetSetter {
    
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObjet(){
        int i = 0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*30;
        gp.obj[i].worldY = gp.tileSize*30;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*32;
        gp.obj[i].worldY = gp.tileSize*30;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize*31;
        gp.obj[i].worldY = gp.tileSize*25;
        i++;
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize*35;
        gp.obj[i].worldY = gp.tileSize*29;
        i++;
    }

    public void setPNJ(){

        gp.pnj[0] = new PNJ_OldMan(gp);
        gp.pnj[0].worldX = gp.tileSize*21;
        gp.pnj[0].worldY = gp.tileSize*21;

    }

    public void setMonster(){

        int i = 0;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*38;
        gp.monster[i].worldY = gp.tileSize*38;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*39;
        gp.monster[i].worldY = gp.tileSize*39;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*39;
        gp.monster[i].worldY = gp.tileSize*40;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*38;
        gp.monster[i].worldY = gp.tileSize*40;
        i++;
    }
}
