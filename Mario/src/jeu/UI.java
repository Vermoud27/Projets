package jeu;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import entity.Entity;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    public int commandeNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;

        try{    
            InputStream is = getClass().getResourceAsStream("../res/police/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        
    }
    
    public void drawTitleScreen(){

        g2.setColor(new Color(70,120,80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "Mario Bros";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;

        g2.drawImage(gp.player.right1, x, y, gp.tileSize*2, gp.tileSize*2,null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        text = "CHARGER LA PARTIE";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandeNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "NOUVELLE PARTIE";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandeNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUITTER";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandeNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    
    public void drawPauseScreen(){

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSE";
        int x = getXforCenteredText(text);

        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }


    public void drawSubWindow(int x, int y, int width, int height){

        Color c =new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public int getXforCenteredText(String text){
        
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;

        return x;
    }
    public int getXforAlignToRightText(String text, int tailX){
        
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;

        return x;
    }
}
