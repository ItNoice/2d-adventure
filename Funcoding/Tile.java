import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;
    
    public Tile(String imagePath, boolean collision) {
        try {
            this.image = ImageIO.read(new java.io.File(imagePath));
            this.collision = collision;
        } catch(IOException e) {
            System.out.println("Error loading tile image: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 