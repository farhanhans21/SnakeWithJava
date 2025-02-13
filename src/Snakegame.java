import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Snakegame extends JPanel implements ActionListener {
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

  // Food
  Tile food;
  Random random;

  //game logic

  Timer gameLoop;
  Snakegame(int boardWidht, int boardHeight) {
    this.boardHeight = boardHeight;
    this.boardWidht = boardWidht;
    setPreferredSize(new Dimension(this.boardWidht, this.boardHeight));
    setBackground(Color.BLACK);

    snekeHead = new Tile(5, 5);

    food = new Tile(10, 10);
    random = new Random();
    placeFood();

    gameLoop = new Timer(100, this);
    gameLoop.start();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }

  public void draw(Graphics g) {
    // Grid
    for (int i = 0; i < boardWidht / tileSize; i++) {
      // (x1,y1,x2,y2)
      g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
      g.drawLine(0, i * tileSize, boardWidht, i * tileSize);
    }

    // food
    g.setColor(Color.red);
    g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

    // snake
    g.setColor(Color.green);
    g.fillRect(snekeHead.x * tileSize, snekeHead.y * tileSize, tileSize, tileSize);
  }

  public void placeFood() {
    food.x = random.nextInt(boardWidht / tileSize);
    food.y = random.nextInt(boardHeight / tileSize);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }
}
