package jeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed,rightPressed,enterPressed,spacePressed;

    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    
    
    @Override
    public void keyTyped(KeyEvent e){

    }
    
    @Override
    public void keyPressed(KeyEvent e){

        int code = e.getKeyCode();

        if(gp.gameState == gp.titleState){
            titleState(code);
        }
        
        else if(gp.gameState == gp.playState){
            playState(code);
        }

        else if(gp.gameState == gp.pauseState){
            pauseState(code);
        }

    }

    public void titleState(int code){
        if(code == KeyEvent.VK_Z){
            gp.ui.commandeNum--;
            if(gp.ui.commandeNum < 0){
                gp.ui.commandeNum = 2;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandeNum++;
            if(gp.ui.commandeNum > 2){
                gp.ui.commandeNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandeNum == 0){
                gp.gameState = gp.playState;
                //gp.playMusic(0);
            }
            if(gp.ui.commandeNum == 1){
                    
            }
            if(gp.ui.commandeNum == 2){
                System.exit(0);
            }
        }
    }
    public void playState(int code){
        if(code == KeyEvent.VK_Q){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        if(code == KeyEvent.VK_T){
            if(checkDrawTime == false){
                checkDrawTime = true;
            }
            else if(checkDrawTime == true){
                checkDrawTime = false;
            }
        }

        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
    }
    public void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Q){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
