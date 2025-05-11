import javax.swing.JFrame;

public class Game {

    public static void main(String[] args) {
        //TODO auto-generated method stub
        
        JFrame frame = new JFrame("2D Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setVisible(true);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();
        
        gamePanel.startGameThread();
    }
}
