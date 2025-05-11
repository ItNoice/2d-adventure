import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile (reduced from 32)
    final int scale = 2; // reduced from 3

    public final int titleSize = originalTileSize * scale; //32x32 tile (reduced from 96)
    public final int maxScreenCol = 16; // reduced from 20
    public final int maxScreenRow = 12; // reduced from 16
    public final int screenWidth = titleSize * maxScreenCol; // 512 pixels
    public final int screenHeight = titleSize * maxScreenRow; // 384 pixels

    // Camera offsets
    public float cameraX = 0;
    public float cameraY = 0;
    private float cameraSpeed = 0.3f; // Increased for more responsive following
    private float targetX = 0;
    private float targetY = 0;

    // FPS
    int FPS = 60;
    long lastCheck = 0;
    int frames = 0;
    int currentFPS = 0;

    // Game Thread
    Thread gameThread;

    // Player position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // Key handler
    Keyhandler keyH = new Keyhandler();
    Player player = new Player(this, keyH);
    TileManager tileM = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.requestFocusInWindow(); // Ensure the panel has focus
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while(gameThread != null) {
            // 1 UPDATE: update information such as player position
            update();
            
            // Update FPS
            keyH.updateFPS();
            
            // 2 DRAW: draw the screen with the updated information
            repaint();
            
            try {
                Thread.sleep(1000/FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void update() {
        // Debug key states
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            System.out.println("Keys pressed - Up: " + keyH.upPressed + 
                             " Down: " + keyH.downPressed + 
                             " Left: " + keyH.leftPressed + 
                             " Right: " + keyH.rightPressed);
        }
        
        player.update();
        
        // Calculate target position (where camera should be)
        targetX = player.worldX - (screenWidth/2);
        targetY = player.worldY - (screenHeight/2);
        
        // Follow player more closely with increased speed
        float dx = targetX - cameraX;
        float dy = targetY - cameraY;
        
        // Move camera more quickly when further from player
        float distance = (float)Math.sqrt(dx * dx + dy * dy);
        float currentSpeed = Math.min(cameraSpeed * (1.0f + distance/100.0f), 1.0f);
        
        cameraX += dx * currentSpeed;
        cameraY += dy * currentSpeed;
        
        // Clamp camera to prevent showing outside the map
        float maxCameraX = (maxScreenCol * titleSize) - screenWidth;
        float maxCameraY = (maxScreenRow * titleSize) - screenHeight;
        
        if(cameraX < 0) cameraX = 0;
        if(cameraY < 0) cameraY = 0;
        if(cameraX > maxCameraX) cameraX = maxCameraX;
        if(cameraY > maxCameraY) cameraY = maxCameraY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // Draw tiles first (with camera offset)
        tileM.draw(g2, (int)cameraX, (int)cameraY);
        
        // Draw FPS (screen-relative, no camera offset needed)
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("FPS: " + keyH.getCurrentFPS(), 20, 30);
        
        // Draw player (with camera offset)
        player.draw(g2, (int)cameraX, (int)cameraY);

        g2.dispose();
    }
}
