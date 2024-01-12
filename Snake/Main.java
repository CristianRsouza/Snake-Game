package Snake;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        int BordHeight = 600;
        int BordWidth = BordHeight;


        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(BordHeight, BordWidth);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(BordHeight, BordWidth);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
