import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class GeometryDash extends JFrame {

    private JPanel gamePanel;
    private JPanel scorePanel;
    private JLabel lblSaut, lblScore;
    private Timer gameTimer;
    private int playerX, playerY;
    private int playerWidth, playerHeight;
    private int platformX, platformY;
    private int platformWidth, platformHeight;
    private List<Obstacle> obstacles;
    private boolean isJump;
    private boolean isFastFall;
    private int nbSaut = 0;
    private int Score;
    private Image img;
    private int backgroundX;
    private int backgroundSpeed;
    private String decor;
    private double playerRotation; // Angle de rotation du joueur
    private boolean isExploding; // Indicateur d'explosion du joueur
    private int explosionSize; // Taille de l'explosion
    private int explosionSpeed; // Vitesse de réduction de la taille de l'explosion
    private double targetRotation; // Angle de rotation cible pour l'animation
    private double rotationSpeed; // Vitesse de rotation pour l'animation

    public GeometryDash() {
        setTitle("Geometry Dash");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g);
            }
        };
        scorePanel = new JPanel();
        lblSaut = new JLabel("Nombre de Saut : 0");
        lblScore = new JLabel("Score :  0");

        scorePanel.add(lblScore);
        scorePanel.add(lblSaut);

        double i = Math.random() * 10;
        if (i < 5) {
            img = Toolkit.getDefaultToolkit().createImage("fond.jpg");
            decor = "1";
        } else {
            img = Toolkit.getDefaultToolkit().createImage("fond2.jpg");
            decor = "2";
        }
        isJump = false;
        isFastFall = false;

        playerWidth = 50;
        playerHeight = 50;
        playerX = 100;
        playerY = getHeight() - playerHeight - 100;
        playerRotation = 0.0; // Pas de rotation initiale

        platformWidth = getWidth();
        platformHeight = 100;
        platformX = 0;
        platformY = getHeight() - platformHeight - 50;

        obstacles = new ArrayList<>();
        generateRandomObstacles(3); // Génère 3 obstacles identiques

        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                if (isJump) {
                    jump();
                }
                if (isFastFall) {
                    fastFall();
                }
                gamePanel.repaint();
            }
        });

        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE && playerY == platformY - playerHeight) {
                    isJump = true;
                    nbSaut++;
                    lblSaut.setText("Nombre de Saut : " + nbSaut);
                    rotatePlayer();
                    jump();
                }
                if (keyCode == KeyEvent.VK_DOWN) {
                    isFastFall = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_DOWN) {
                    isFastFall = false;
                }
            }
        });

        add(gamePanel, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.NORTH);
        setVisible(true);
        gameTimer.start();

        backgroundX = 0;
        backgroundSpeed = 1;
        startBackgroundAnimation();

        isExploding = false;
        explosionSize = playerWidth;
        explosionSpeed = 2;

        targetRotation = 0.0;
        rotationSpeed = 3.0;
    }

    private void draw(Graphics g) {
        g.drawImage(img, -backgroundX, 0, null);
        g.drawImage(img, -backgroundX + getWidth(), 0, null);

        Graphics2D g2d = (Graphics2D) g;
        if (decor.equals("1")) {
            GradientPaint p = new GradientPaint(0, 500, Color.red, 900, 800, Color.black);
            g2d.setPaint(p);
        } else {
            GradientPaint p = new GradientPaint(0, 500, Color.blue, 900, 800, Color.black);
            g2d.setPaint(p);
        }
        g2d.fillRect(platformX, platformY, platformWidth, platformHeight);

        if (decor.equals("1")) {
            g.setColor(Color.white);
        } else {
            g.setColor(Color.BLACK);
        }
        for (Obstacle obstacle : obstacles) {
            int[] obstacleXPoints = {obstacle.getX(), obstacle.getX() + obstacle.getWidth() / 2, obstacle.getX() + obstacle.getWidth()};
            int[] obstacleYPoints = {obstacle.getY() + obstacle.getHeight(), obstacle.getY(), obstacle.getY() + obstacle.getHeight()};
            g.fillPolygon(obstacleXPoints, obstacleYPoints, 3);
        }

        g.setColor(Color.WHITE);
        for (Obstacle obstacle : obstacles) {
            int[] obstacleXPoints = {obstacle.getX(), obstacle.getX() + obstacle.getWidth() / 2, obstacle.getX() + obstacle.getWidth()};
            int[] obstacleYPoints = {obstacle.getY() + obstacle.getHeight(), obstacle.getY(), obstacle.getY() + obstacle.getHeight()};
            g.drawPolygon(obstacleXPoints, obstacleYPoints, 3);
        }

        g.setColor(Color.WHITE);
        g.drawLine(0,450,900,450);

        if (!isExploding) {
            g.setColor(Color.GREEN);
            g.translate(playerX + playerWidth / 2, playerY + playerHeight / 2); // Translation vers le centre du joueur
            g2d.rotate(Math.toRadians(playerRotation)); // Rotation du joueur
            g.fillRect(-playerWidth / 2, -playerHeight / 2, playerWidth, playerHeight); // Rectangle centré
        } else {
            g.setColor(Color.RED);
            int explosionX = playerX + playerWidth / 2 - explosionSize / 2;
            int explosionY = playerY + playerHeight / 2 - explosionSize / 2;
            g.fillRect(explosionX, explosionY, explosionSize, explosionSize);
        }
    }

    private void update() {
        playerY += isFastFall ? 4 : 3;
        Score++;
        lblScore.setText("Score : " + Score / 4);

        for (Obstacle obstacle : obstacles) {
            obstacle.setX(obstacle.getX() - 4); // Vitesse de déplacement des obstacles(Vitesse du jeu)
        }

        if (obstacles.get(0).getX() + obstacles.get(0).getWidth() < 0) {
            obstacles.remove(0);
            int lastObstacleX = obstacles.get(obstacles.size() - 1).getX();
            obstacles.add(new Obstacle(lastObstacleX + 300, platformY - 50, 50, 50));
        }

        if (playerY + playerHeight > platformY) {
            //Collision avec la plateforme
            playerY = platformY - playerHeight;
        }

        if (playerY + playerHeight > getHeight()) {
            // Condition de fin de jeu
            gameTimer.stop();
            JOptionPane.showMessageDialog(this, "Game Over\nScore : " + Score / 4);
            System.exit(0);
        }

        for (Obstacle obstacle : obstacles) {
            if (!isExploding && playerX + playerWidth > obstacle.getX() + 20 && playerX < obstacle.getX() + obstacle.getWidth() - 5
                    && playerY + playerHeight > obstacle.getY() && playerY < obstacle.getY() + obstacle.getHeight()) {
                // Collision détectée
                explodePlayer();
                break; // Sort de la boucle pour éviter la détection de collisions multiples
            }
        }

        if (isExploding) {
            explosionSize -= explosionSpeed;
            if (explosionSize <= 0) {
                // Condition de fin de l'explosion
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Game Over\nScore : " + Score / 4);
                System.exit(0);
            }
        }

        // Animation de rotation du joueur
        if (playerRotation < targetRotation) {
            playerRotation += rotationSpeed;
            if (playerRotation > targetRotation) {
                playerRotation = targetRotation;
            }
        } else if (playerRotation > targetRotation) {
            playerRotation -= rotationSpeed;
            if (playerRotation < targetRotation) {
                playerRotation = targetRotation;
            }
        }
    }

    private void jump() {
        playerY -= 7;
        if (playerY <= 300) {
            isJump = false;
        }
    }

    private void fastFall() {
        playerY += 4;
    }

    private void rotatePlayer() {
        targetRotation += 90.0; // Ajoute 90 degrés à l'angle de rotation cible
    }

    private void explodePlayer() {
        isExploding = true;
    }

    private void startBackgroundAnimation() {
        Timer backgroundTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundX += backgroundSpeed;
                if (backgroundX >= getWidth()) {
                    backgroundX = 0;
                }
            }
        });
        backgroundTimer.start();
    }

    private class Obstacle {
        private int x;
        private int y;
        private int width;
        private int height;

        public Obstacle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    private void generateRandomObstacles(int obstacleCount) {
        int obstacleWidth = 50;
        int obstacleHeight = 50;
        int obstacleY = platformY - obstacleHeight;

        obstacles = new ArrayList<>();

        for (int i = 0; i < obstacleCount; i++) {
            int obstacleX = getWidth() + i * 320; // Espacement horizontal entre les obstacles
            obstacles.add(new Obstacle(obstacleX, obstacleY, obstacleWidth, obstacleHeight));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GeometryDash();
            }
        });
    }
}
