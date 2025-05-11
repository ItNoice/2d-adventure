import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Color;

public class Player extends Entity {
    GamePanel gp;
    Keyhandler keyH;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Player(GamePanel gp, Keyhandler keyH) {
        super(gp.titleSize * 2, gp.titleSize * 2, 2); // Start player in a more visible position with slower speed
        this.gp = gp;
        this.keyH = keyH;
        direction = "down";
        getPlayerImage();
        System.out.println("Player initialized at position: " + worldX + ", " + worldY);
    }

    public void getPlayerImage() {
        try {
            System.out.println("Attempting to load sprites...");
            // Try loading from the project root first
            up1 = ImageIO.read(new java.io.File("res/sprites/player_up_1.png"));
            System.out.println("Loaded up1 sprite");
            up2 = ImageIO.read(new java.io.File("res/sprites/player_up_2.png"));
            System.out.println("Loaded up2 sprite");
            down1 = ImageIO.read(new java.io.File("res/sprites/player_down_1.png"));
            System.out.println("Loaded down1 sprite");
            down2 = ImageIO.read(new java.io.File("res/sprites/player_down_2.png"));
            System.out.println("Loaded down2 sprite");
            left1 = ImageIO.read(new java.io.File("res/sprites/player_left_1.png"));
            System.out.println("Loaded left1 sprite");
            left2 = ImageIO.read(new java.io.File("res/sprites/player_left_2.png"));
            System.out.println("Loaded left2 sprite");
            right1 = ImageIO.read(new java.io.File("res/sprites/player_right_1.png"));
            System.out.println("Loaded right1 sprite");
            right2 = ImageIO.read(new java.io.File("res/sprites/player_right_2.png"));
            System.out.println("Loaded right2 sprite");
            System.out.println("All sprites loaded successfully!");
        } catch(IOException e) {
            System.out.println("Error loading sprites: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;
        speed = 4;
    }

    public void update() {
        if(keyH.upPressed) {
            direction = "up";
            // Check tile collision
            int tileNum = gp.tileM.mapTileNum[worldX/gp.titleSize][(worldY-speed)/gp.titleSize];
            System.out.println("Moving up - Tile: " + tileNum + " Collision: " + gp.tileM.tile[tileNum].collision);
            if(!gp.tileM.tile[tileNum].collision) {
                worldY -= speed;
                System.out.println("New position: " + worldX + ", " + worldY);
            }
        }
        if(keyH.downPressed) {
            direction = "down";
            // Check tile collision
            int tileNum = gp.tileM.mapTileNum[worldX/gp.titleSize][(worldY+speed+gp.titleSize)/gp.titleSize];
            System.out.println("Moving down - Tile: " + tileNum + " Collision: " + gp.tileM.tile[tileNum].collision);
            if(!gp.tileM.tile[tileNum].collision) {
                worldY += speed;
                System.out.println("New position: " + worldX + ", " + worldY);
            }
        }
        if(keyH.leftPressed) {
            direction = "left";
            // Check tile collision
            int tileNum = gp.tileM.mapTileNum[(worldX-speed)/gp.titleSize][worldY/gp.titleSize];
            System.out.println("Moving left - Tile: " + tileNum + " Collision: " + gp.tileM.tile[tileNum].collision);
            if(!gp.tileM.tile[tileNum].collision) {
                worldX -= speed;
                System.out.println("New position: " + worldX + ", " + worldY);
            }
        }
        if(keyH.rightPressed) {
            direction = "right";
            // Check tile collision
            int tileNum = gp.tileM.mapTileNum[(worldX+speed+gp.titleSize)/gp.titleSize][worldY/gp.titleSize];
            System.out.println("Moving right - Tile: " + tileNum + " Collision: " + gp.tileM.tile[tileNum].collision);
            if(!gp.tileM.tile[tileNum].collision) {
                worldX += speed;
                System.out.println("New position: " + worldX + ", " + worldY);
            }
        }

        // Keep player within map boundaries
        if(worldX < 0) worldX = 0;
        if(worldY < 0) worldY = 0;
        if(worldX > (gp.maxScreenCol * gp.titleSize) - gp.titleSize) {
            worldX = (gp.maxScreenCol * gp.titleSize) - gp.titleSize;
        }
        if(worldY > (gp.maxScreenRow * gp.titleSize) - gp.titleSize) {
            worldY = (gp.maxScreenRow * gp.titleSize) - gp.titleSize;
        }

        // Sprite animation
        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            } else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        if (up1 == null) {
            // Fallback rendering when sprites are not available
            g2.setColor(Color.blue);
            g2.fillRect(worldX - cameraX, worldY - cameraY, gp.titleSize, gp.titleSize);
            return;
        }

        BufferedImage image = null;
        
        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        
        g2.drawImage(image, worldX - cameraX, worldY - cameraY, gp.titleSize, gp.titleSize, null);
    }
}
