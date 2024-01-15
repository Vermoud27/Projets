package jeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed,rightPressed,enterPressed;

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

        else if(gp.gameState == gp.dialogueState){
            dialogueState(code);
        }

        else if(gp.gameState == gp.characterState){
            characterState(code);
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
                gp.playMusic(0);
            }
            if(gp.ui.commandeNum == 1){
                    
            }
            if(gp.ui.commandeNum == 2){
                System.exit(0);
            }
        }
    }
    public void playState(int code){
        if(code == KeyEvent.VK_Z){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_Q){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
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
    }
    public void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_Z){
            if(gp.ui.slotRow != 0){
                gp.ui.slotRow--;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_Q){
            if(gp.ui.slotCol != 0){
                gp.ui.slotCol--;
                gp.playSE(8);
            } 
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.slotRow != 3){
                gp.ui.slotRow++;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.slotCol != 4){
                gp.ui.slotCol++;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_Q){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}