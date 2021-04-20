package view;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.*;

import controller.EventController;
import model.Direction;
import model.IReadableNode;
import model.PlayerEffect;
import model.Position;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class SwingMazeView extends JFrame implements IMazeView, KeyListener {
  private final NodePanel nodePanel;
  private boolean firstRender;
  private EventController listener;

  public SwingMazeView() {
    this.nodePanel = new NodePanel();
    firstRender = true;
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setTitle("Hunt The Wumpus");
    this.addKeyListener(this);


  }

  @Override
  public void setNodes(List<List<IReadableNode>> nodes) {
    this.nodePanel.setNodes(nodes);
    //Allows the window to resize when the node list has a definitive size
    if (firstRender) {
      nodePanel.setPreferredSize(new Dimension(nodes.size() * 64, nodes.get(0).size() * 64));
      JScrollPane scrollBarAndPane = new JScrollPane(nodePanel,
              VERTICAL_SCROLLBAR_AS_NEEDED,
              HORIZONTAL_SCROLLBAR_AS_NEEDED);
      this.setSize(new Dimension(1000, 1000));
      this.add(scrollBarAndPane, BorderLayout.CENTER);
      this.pack();
      this.setLocationRelativeTo(null); // center the frame
      this.firstRender = false;
    }
  }

  @Override
  public void setPlayerPos(Position pos) {

  }

  @Override
  public void setPossibleMoves(List<Direction> directions) {

  }

  @Override
  public void setPlayerEffects(List<PlayerEffect> effects) {

  }

  @Override
  public void displayError(String error) {

  }

  @Override
  public void animate() {
    this.setVisible(true);
    this.repaint();
  }

  @Override
  public void animateGameOver() {
    this.setVisible(true);
    this.repaint();

  }

  @Override
  public void setEventController(EventController listener) {
    this.listener = listener;
    this.nodePanel.setListener(listener);
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        listener.movePlayer(Direction.NORTH);
        break;
      case KeyEvent.VK_LEFT:
        listener.movePlayer(Direction.WEST);
        break;
      case KeyEvent.VK_DOWN:
        listener.movePlayer(Direction.SOUTH);
        break;
      case KeyEvent.VK_RIGHT:
        listener.movePlayer(Direction.EAST);
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
