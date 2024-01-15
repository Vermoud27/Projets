package Tuile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import jeu.GamePanel;
import jeu.UtilityTool;

public class TuileManager {
    
    GamePanel gp;
    public Tuile[] tuile;
    public int mapTuileNum[][];
    
    public TuileManager(GamePanel gp){

        this.gp = gp;

        tuile = new Tuile[55];
        mapTuileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTuileImage();
        loadMap("../map/map1.txt");
    }

    public void getTuileImage(){

        // SOL
        setup(10,"ground",true);
        setup(11,"ground2",true);

        // CIEL
        setup(19,"fakesky",true);
        setup(20,"sky",false);
        setup(21,"start_arrow",false);

        // TUYAU
        setup(30, "pipe1", true);
        setup(31, "pipe2", true);
        setup(32, "pipe3", true);

    }

    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try {
            tuile[index] = new Tuile();
            tuile[index].image = ImageIO.read(getClass().getResourceAsStream("./Tuiles/"+imageName+".png"));
            tuile[index].image = uTool.scaleImage(tuile[index].image, gp.tileSize, gp.tileSize);
            tuile[index].colision = collision;


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                while(col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTuileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(IOException e) {

        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            
            int tuileNum = mapTuileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tuile[tuileNum].image, screenX, screenY, null);

            }
            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }

        }

    }
}

