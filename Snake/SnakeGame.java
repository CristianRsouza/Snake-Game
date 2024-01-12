package Snake;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    
    private class Tile {
        int x;
        int y;
        
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    int BordHeight;
    int BordWidth;
    int TileSize = 25;

    Tile SnakeHead;
    ArrayList<Tile> SnakeBody;

    Tile Food;
    Random random;
    Boolean GameOver = false;


    Timer gameLoop;
    int velocityX;
    int velocityY;


    SnakeGame(int BordHeight, int BordWidth) {
        this.BordHeight = BordHeight;
        this.BordWidth = BordWidth;
        setPreferredSize(new Dimension(this.BordHeight, this.BordWidth));
        setBackground(Color.BLACK);
        
        SnakeHead = new Tile(5, 5);
        SnakeBody = new ArrayList<Tile>();

        addKeyListener(this);
        setFocusable(true);
        
        Food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }




    public void draw(Graphics g) {

    //    for(int i = 0; i < BordWidth/TileSize; i++) {
    //         g.drawLine(i * TileSize, 0, i * TileSize, BordHeight);
    //        g.drawLine(0, i * TileSize, BordWidth, i * TileSize );
    //   }
  
        g.setColor(Color.RED);
        g.fillRect(Food.x * TileSize, Food.y * TileSize, TileSize, TileSize);

        g.setColor(Color.GREEN);
        g.fillRect(SnakeHead.x * TileSize, SnakeHead.y * TileSize, TileSize, TileSize);

        for(int i = 0 ; i <  SnakeBody.size(); i++) {
            Tile snakePart = SnakeBody.get(i);
            g.fillRect(snakePart.x * TileSize, snakePart.y * TileSize, TileSize, TileSize);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 16));
            if(GameOver) {
                g.setColor(Color.RED);
                g.drawString("Game Over: " + String.valueOf(SnakeBody.size()), TileSize - 16, TileSize );
            } else {
                g.drawString("Score: " + String.valueOf(SnakeBody.size()), TileSize - 16, TileSize);
            }
    }

    public void placeFood() {
        Food.x = random.nextInt(BordWidth/TileSize);
        Food.y = random.nextInt(BordHeight/TileSize);

    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
     }

     public void move() {
        // Cria uma nova posição para a cabeça da cobra
        Tile newHead = new Tile(SnakeHead.x + velocityX, SnakeHead.y + velocityY);
    
        // Verifica se a nova posição colide com o corpo da cobra
        for (Tile snakePart : SnakeBody) {
            if (collision(newHead, snakePart)) {
                GameOver = true;
                gameLoop.stop(); // Adiciona essa linha para parar o loop do jogo quando GameOver é verdadeiro
                return;  // Sai do método se houver colisão com o próprio corpo
            }
        }
    
        // Move o corpo da cobra
        for (int i = SnakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = SnakeBody.get(i);
            if (i == 0) {
                // Move a cabeça para a nova posição
                snakePart.x = newHead.x;
                snakePart.y = newHead.y;
            } else {
                Tile prevSnakePart = SnakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
    
        // Atualiza a posição da cabeça da cobra
        SnakeHead = newHead;
    
        // Verifica se a cabeça colide com a comida
        if (collision(SnakeHead, Food)) {
            SnakeBody.add(new Tile(Food.x, Food.y));
            placeFood();
        }

        if(SnakeHead.x * TileSize < 0 || SnakeHead.x * TileSize > BordWidth ||
           SnakeHead.y * TileSize < 0 || SnakeHead.y * TileSize > BordHeight      
        ) {
            GameOver = true;
        }

    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        move();
        repaint();
        
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
   
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY !=-1) {
            velocityX = 0;
            velocityY = 1;
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX !=1 ) {
            velocityX = -1;
            velocityY = 0;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX !=-1) {
            velocityX = 1;
            velocityY = 0;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}

}
