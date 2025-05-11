import javax.swing.JFrame;

public class GameWindow {
    private JFrame window;
    private GamePanel gamePanel;

    public GameWindow() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("2D Adventure");

        gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        new GameWindow();
    }
} 