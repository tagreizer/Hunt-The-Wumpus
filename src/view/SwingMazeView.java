package view;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class SwingMazeView extends JFrame implements IMazeView, KeyListener, ActionListener{
  private final NodePanel nodePanel;
  private final ArrowPanel arrowPanel;
  private boolean firstRender;
  private EventController listener;
  private List<PlayerEffect> playerEffects;


  public SwingMazeView() {
    this.nodePanel = new NodePanel();
    this.arrowPanel = new ArrowPanel(this);
    firstRender = true;


    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setTitle("Hunt The Wumpus");
    this.addKeyListener(this);


  }

  private void setUpPanels(List<List<IReadableNode>> nodes) {
    nodePanel.setPreferredSize(new Dimension(nodes.get(0).size() * 64, nodes.size() * 64));

    JScrollPane scrollBarAndPane = new JScrollPane(nodePanel,
            VERTICAL_SCROLLBAR_AS_NEEDED,
            HORIZONTAL_SCROLLBAR_AS_NEEDED);
    //this.setSize(new Dimension(1000, 1000));
    this.add(arrowPanel, BorderLayout.WEST);
    this.add(scrollBarAndPane, BorderLayout.CENTER);
    this.pack();
    this.setLocationRelativeTo(null); // center the frame
    this.firstRender = false;
    this.setFocusable(true);
  }

  @Override
  public void setNodes(List<List<IReadableNode>> nodes) {
    this.nodePanel.setNodes(nodes);
    //Allows the window to resize when the node list has a definitive size
    if (firstRender) {
      this.setUpPanels(nodes);
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
    this.playerEffects = effects;
    if (this.playerEffects.contains(PlayerEffect.MISSED_WUMPUS)) {
      JOptionPane.showMessageDialog(this, "That arrow missed the Wumpus",
              "Missed",
              JOptionPane.PLAIN_MESSAGE);
    }

  }

  @Override
  public void setArrowAmount(int arrowAmount) {
    arrowPanel.setArrowAmount(arrowAmount);

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
    String titlemsg = "";
    String effectMsg = "";
    for (PlayerEffect effect : this.playerEffects) {
      switch (effect) {
        case FELL_INTO_PIT:
          effectMsg = "AHHHHH....\nYou fell into a pit and died!";
          titlemsg = "Game Over!\nBetter luck next time!";
          break;
        case RAN_INTO_WUMPUS:
          effectMsg = "CHOMP CHOMP CHOMP...\nYou have been eaten by the Wumpus!";
          titlemsg = "Game Over!\nBetter luck next time!";
          break;
        case NO_ARROWS:
          effectMsg = "RAWRRRRRRRRR...\nYou're out of " +
                  "arrows and the Wumpus knows!\nHe came to eat you!";
          titlemsg = "Game Over!\nBetter luck next time!";
          break;

        case SHOT_WUMPUS:
          titlemsg = "You Win!\nCongratulations!";

          effectMsg = "RAWRRRRRrrrr...\nYou hear the " +
                  "screams of pain from a Wumpus pierced with an arrow!";
          break;
        default:
          //others do not have a game over msg.

      }
    }

    JOptionPane.showMessageDialog(this, effectMsg,
            titlemsg,
            JOptionPane.PLAIN_MESSAGE);

  }

  @Override
  public void setEventController(EventController listener) {
    this.listener = listener;
    this.arrowPanel.setEventListener(listener);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //not used
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
      default:
        System.out.println(e.toString());
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //not used
  }


  @Override
  public void actionPerformed(ActionEvent e) {

  }



}
