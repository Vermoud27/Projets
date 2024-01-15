package jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.Entity;
import entity.Player;

import javax.swing.JPanel;

import Tuile.TuileManager;

public class GamePanel extends JPanel implements Runnable {
    
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 66;
    public final int maxWorldRow = 62;

    int FPS = 60;

    TuileManager tuileM = new TuileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity pnj[] = new Entity[10];
    public Entity monster[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){

        aSetter.setObjet();
        aSetter.setPNJ();
        aSetter.setMonster();

        //playMusic(0);
        gameState = titleState; 
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        
        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }

            if(timer >= 1000000000){
                timer = 0;
            }
        }

    }

    public void update(){
        
        if(gameState == playState){
            player.update();

            for(int i =0; i < pnj.length; i++){
                if(pnj[i] != null){
                    pnj[i].update();
                }
            }
            for(int i =0; i < monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].alive == true && monster[i].dying == false){
                        monster[i].update();
                    }
                    if(monster[i].alive == false){
                        monster[i] = null;
                    }
                }
            }
        }
        if(gameState == pauseState){

        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }
        
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
            
            tuileM.draw(g2);

            entityList.add(player);

            for(int i=0; i < pnj.length; i++){
                if(pnj[i] != null){
                    entityList.add(pnj[i]);
                }
            }

            for(int i=0; i< obj.length;i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            for(int i=0; i< monster.length;i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
                
            });

            for(int i = 0; i < entityList.size();i++){
                entityList.get(i).draw(g2);
            }

            entityList.clear();

            ui.draw(g2);
        }

        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: "+passed, 10,400);
            System.out.println("Draw Time:"+ passed);
        }
        



        g2.dispose();
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){

        music.stop();
    }
    public void playSE(int i){

        se.setFile(i);
        se.play();
    }
}
