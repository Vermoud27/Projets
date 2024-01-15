import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Agario extends JFrame {
    
    private JPanel gamePanel;
    private Timer gameTimer;
    private Timer spawnTimer;
    private List<Joueur> joueurs;
    private List<Point> points;
    private Color[] couleurs = {Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.PINK, Color.CYAN};
    private int mouseX;
    private int mouseY;
    private int currentPlayerX;
    private int currentPlayerY;

    public Agario() {
        setTitle("Agario");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g);
            }
        };

        spawnTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = (int) (Math.random() * 900);
                int y = (int) (Math.random() * 600);
                int c = (int) (Math.random() * 6);

                Point p = new Point(x, y, couleurs[c]);
                points.add(p);
            }
        });

        gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                gamePanel.repaint();
            }
        });

        joueurs = new ArrayList<>();
        points  = new ArrayList<>();

        int x = (int) (Math.random() * 900);
        int y = (int) (Math.random() * 600);
        int c = (int) (Math.random() * 6);

        Joueur j = new Joueur("m", x, y, 10, couleurs[c]);
        joueurs.add(j);

        gamePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        add(gamePanel);
        setVisible(true);
        gameTimer.start();
        spawnTimer.start();
    }

    public void draw(Graphics g) {
        for (Joueur joueur : joueurs) {
            g.setColor(joueur.getCouleur());
            g.fillOval(joueur.getCoordX() - joueur.getRayon() / 2, joueur.getCoordY() - joueur.getRayon() / 2, joueur.getRayon(), joueur.getRayon());
        }
        
        for (int cpt = 0; cpt < points.size(); cpt++) {
            g.setColor(points.get(cpt).getCouleur());
            g.fillOval(points.get(cpt).getCoordX(), points.get(cpt).getCoordY(), 4, 4);
        }
    }

    private void update() {
        for (Joueur joueur : joueurs) {
            int targetX = mouseX;
            int targetY = mouseY;

            int dx = targetX - currentPlayerX;
            int dy = targetY - currentPlayerY;

            int speedFactor = 2; // Contrôle la vitesse du joueur
            currentPlayerX += dx * speedFactor / 100;
            currentPlayerY += dy * speedFactor / 100;

            joueur.setCoordX(currentPlayerX);
            joueur.setCoordY(currentPlayerY);
        }
    }

    public static void main(String args[]) {
        new Agario();
    }
}
