package piece;

import main.GamePanel;
import main.Type;

public class Rook extends Piece {

    public Rook(int color, int col, int row){
        super(color,col,row);

        type = Type.ROOK;

        if(color == GamePanel.WHITE){
            image = getImage("../res/piece/w-rook");
        }
        else {
            image = getImage("../res/piece/b-rook");
        }
    }
    
    public boolean canMove(int targetCol, int targetRow){

        if(inBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false){
            if(targetCol == preCol || targetRow == preRow){
                if(isValidSquare(targetCol, targetRow) && pieceIsOnStraightLine(targetCol, targetRow) == false){
                    return true;
                }
            }
        }
        
        return false;
    }
}
