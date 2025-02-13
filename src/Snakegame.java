import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Snakegame extends JPanel implements ActionListener, KeyListener {
  private class Tile {
    int x;
    int y;

    Tile(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  int boardWidht;
  int boardHeight;
  int tileSize = 25;

  // snake
  Tile snekeHead;
  ArrayList<Tile> snakeBody;
  // Food
  Tile food;
  Random random;

  // game logic
  Timer gameLoop;
  int velocityX;
  int velocityY;
  boolean gameOver = false;

  Snakegame(int boardWidht, int boardHeight) {
    this.boardHeight = boardHeight;
    this.boardWidht = boardWidht;
    setPreferredSize(new Dimension(this.boardWidht, this.boardHeight));
    setBackground(Color.BLACK);
    addKeyListener(this);
    setFocusable(true);

    snekeHead = new Tile(5, 5);
    snakeBody = new ArrayList<Tile>();
    food = new Tile(10, 10);
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
    // Grid
    // for (int i = 0; i < boardWidht / tileSize; i++) {
    //   // (x1,y1,x2,y2)
    //   g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
    //   g.drawLine(0, i * tileSize, boardWidht, i * tileSize);
    // }

    // food
    g.setColor(Color.red);
    // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
    g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize,true);

    // snake Head
    g.setColor(Color.green);
    // g.fillRect(snekeHead.x * tileSize, snekeHead.y * tileSize, tileSize, tileSize);
    g.fill3DRect(snekeHead.x * tileSize, snekeHead.y * tileSize, tileSize, tileSize,true);

    // snake Body
    for (int i = 0; i < snakeBody.size(); i++) {
      Tile snakePart = snakeBody.get(i);
      // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
      g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize,true);
    }

    //score
    g.setFont(new Font("Arial", Font.PLAIN, 16));
    if (gameOver) {
      g.setColor(Color.red);
      g.drawString("Game Over : "+ String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
    }
    else{
      g.drawString("Score : " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
    }

  }

  public void placeFood() {
    food.x = random.nextInt(boardWidht / tileSize);
    food.y = random.nextInt(boardHeight / tileSize);
  }

  public boolean collision(Tile tile1, Tile tile2) {
    return tile1.x == tile2.x && tile1.y == tile2.y;
  }

  public void move() {
    // eat food
    if (collision(snekeHead, food)) {
      snakeBody.add(new Tile(food.x, food.y));
      placeFood();
    }

    // snake body
    for (int i = snakeBody.size() - 1; i >= 0; i--) {
      Tile snakePart = snakeBody.get(i);
      if (i == 0) {
        snakePart.x = snekeHead.x;
        snakePart.y = snekeHead.y;
      } else {
        Tile prevSnakePart = snakeBody.get(i - 1);
        snakePart.x = prevSnakePart.x;
        snakePart.y = prevSnakePart.y;
      }
    }

    // Snake head
    snekeHead.x += velocityX;
    snekeHead.y += velocityY;

    // game Over Conditions

    for (int i = 0; i < snakeBody.size(); i++) {
      Tile snakePart = snakeBody.get(i);

      // collide with the snake head

      if (collision(snekeHead, snakePart)) {
        gameOver = true;
      }
    }
    if (snekeHead.x * tileSize < 0 || snekeHead.x * tileSize > boardWidht ||
        snekeHead.y * tileSize < 0 || snekeHead.y * tileSize > boardHeight) {
      gameOver = true;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    move();
    repaint();
    if (gameOver) {
      gameLoop.stop();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
      velocityX = 0;
      velocityY = -1;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
      velocityX = 0;
      velocityY = 1;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
      velocityX = -1;
      velocityY = 0;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
      velocityX = 1;
      velocityY = 0;
    }
  }

  // dont need this
  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
