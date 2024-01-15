import java.awt.*;

public class Point {

    private int coordX;
    private int coordY;
    private Color couleur;

    public Point(int coordX, int coordY, Color couleur)
    {
        this.coordX = coordX;
        this.coordY = coordY;
        this.couleur = couleur;
    }


    public int getCoordX()
    {
        return this.coordX;
    }
    public int getCoordY()
    {
        return this.coordY;
    }

    public Color getCouleur()
    {
        return this.couleur;
    }
}
