package objet;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import jeu.GamePanel;
import jeu.UtilityTool;

public class OBJ_Heart extends Entity{
    
    public OBJ_Heart(GamePanel gp) {

        super(gp);
        UtilityTool uTool = new UtilityTool();
        
        name = "Heart";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("../res/objets/heart.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("../res/objets/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("../res/objets/heart_blank.png"));

            image = uTool.scaleImage(image, gp.tileSize/2, gp.tileSize/2);
            image2 = uTool.scaleImage(image2, gp.tileSize/2, gp.tileSize/2);
            image3 = uTool.scaleImage(image3, gp.tileSize/2, gp.tileSize/2);

        }catch(IOException e){
            e.printStackTrace();
        }

        colision = true;
    }    
}
