import java.awt.*;

public class Joueur {

    private String pseudo;
    private int coordX;
    private int coordY;
    private int rayon;
    private Color couleur;

    public Joueur(String pseudo, int coordX, int coordY, int rayon, Color couleur)
    {
        this.pseudo = pseudo;
        this.coordX = coordX;
        this.coordY = coordY;
        this.rayon  = rayon;
        this.couleur = couleur;
    }

    public String getPseudo()
    {
        return this.pseudo;
    }
    public int getCoordX()
    {
        return this.coordX;
    }
    public int getCoordY()
    {
        return this.coordY;
    }
    public int getRayon()
    {
        return this.rayon;
    }
    public Color getCouleur()
    {
        return this.couleur;
    }

    public void setCoordX(int coordX)
    {
        this.coordX = coordX;
    }
    
    public void setCoordY(int coordY)
    {
        this.coordY = coordY;
    }
}
