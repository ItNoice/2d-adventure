import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyhandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    // FPS tracking
    private long lastCheck = 0;
    private int frames = 0;
    private int currentFPS = 0;

    public void updateFPS() {
        frames++;
        if(System.currentTimeMillis() - lastCheck >= 1000) {
            currentFPS = frames;
            frames = 0;
            lastCheck = System.currentTimeMillis();
        }
    }

    public int getCurrentFPS() {
        return currentFPS;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this implementation
    }
}
