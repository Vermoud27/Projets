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

        // AUTRE
        setup(10,"grass",false);
        setup(11,"tree",true);
        setup(12,"plank",false);
        setup(13,"wall",true);
        setup(14,"neph",false);

        // EAU
        setup(20,"water",true);
        setup(21,"water_up",true);
        setup(22,"water_left",true);
        setup(23,"water_down",true);
        setup(24,"water_right",true);
        setup(25,"water_corner1",true);
        setup(26,"water_corner2",true);
        setup(27,"water_corner3",true);
        setup(28,"water_corner4",true);
        setup(29,"water_angle1",true);
        setup(30,"water_angle2",true);
        setup(31,"water_angle3",true);
        setup(32,"water_angle4",true);

        // TERRE
        //setup(30,"dirt",false);
        
        // CHEMIN
        setup(40,"road",false);
        setup(41,"road_up",false);
        setup(42,"road_left",false);
        setup(43,"road_down",false);
        setup(44,"road_right",false);
        setup(45,"road_corner1",false);
        setup(46,"road_corner2",false);
        setup(47,"road_corner3",false);
        setup(48,"road_corner4",false);
        setup(49,"road_angle1",false);
        setup(50,"road_angle2",false);
        setup(51,"road_angle3",false);
        setup(52,"road_angle4",false);

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
