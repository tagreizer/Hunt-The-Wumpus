package view;

import java.awt.*;

import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import controller.EventController;
import model.Direction;
import model.IReadableNode;
import model.PlayerEffect;
import model.Position;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

/**
 * A view for the hunt the wumpus game that uses JSwing. The maze can be navigated by clicking rooms
 * or using the arrow keys.
 */
public class SwingMazeView extends JFrame implements IMazeView, KeyListener {

  private final NodePanel nodePanel;
  private final ArrowPanel arrowPanel;
  private final RestartAndTurnPanel restartAndTurnPanel;
  private boolean firstRender;
  private EventController listener;
  private List<List<PlayerEffect>> playerEffects;
  //long names for variables that help correct display for multiple players.
  private boolean[] haveDisplayedGameOverForPlayerNumber;
  private boolean[] haveDisplayedMissedArrows;
  private int turnNum;

  /**
   * Creates a new swingview for the hunt the wumpus.
   */
  public SwingMazeView() {
    this.nodePanel = new NodePanel();
    this.arrowPanel = new ArrowPanel(this);
    this.restartAndTurnPanel = new RestartAndTurnPanel(this);
    firstRender = true;
    this.haveDisplayedGameOverForPlayerNumber = null;

    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setTitle("Hunt The Wumpus");
    this.addKeyListener(this);


  }

  /**
   * Sets up all the panels. This is not done in the constructor so that the panels can be sized to
   * the game size before rendering.
   *
   * @param nodes the nodes used to size to.
   */
  private void setUpPanels(List<List<IReadableNode>> nodes) {
    nodePanel.setPreferredSize(new Dimension(nodes.get(0).size() * 64, nodes.size() * 64));

    JScrollPane scrollBarAndPane = new JScrollPane(nodePanel,
        VERTICAL_SCROLLBAR_AS_NEEDED,
        HORIZONTAL_SCROLLBAR_AS_NEEDED);

    this.add(arrowPanel, BorderLayout.WEST);
    this.add(scrollBarAndPane, BorderLayout.CENTER);
    this.add(restartAndTurnPanel, BorderLayout.EAST);
    this.pack();
    this.setLocationRelativeTo(null); // center the frame
    this.firstRender = false;
    this.setFocusable(true);
  }

  @Override
  public void setNodes(List<List<IReadableNode>> nodes) {
    if (nodes == null) {
      return;
    }
    this.nodePanel.setNodes(nodes);

    //Allows the window to resize when the node list has a definitive size
    if (firstRender) {
      this.setUpPanels(nodes);
    }

  }

  @Override
  public void setPlayerPos(Position pos) {
//not used in this view type.
  }

  @Override
  public void setPossibleMoves(List<Direction> directions) {
//not used in this view type.
  }

  @Override
  public void setPlayerEffects(List<List<PlayerEffect>> effects) {
    this.playerEffects = effects;
    this.setUpDisplayedArrays(effects.size());
    this.displayArrowMissedIfNeeded();


  }

  /**
   * Displays if a player missed an arrow on their turn, but avoids displaying it repeatedly.
   */
  private void displayArrowMissedIfNeeded() {
    for (int i = 0; i < this.playerEffects.size(); i++) {
      List<PlayerEffect> effectList = this.playerEffects.get(i);
      if (effectList.contains(PlayerEffect.MISSED_WUMPUS) && !this.haveDisplayedMissedArrows[i]) {
        JOptionPane.showMessageDialog(this, "PLayer" + (i + 1)
                + "'s arrow missed the Wumpus",
            "Missed",
            JOptionPane.PLAIN_MESSAGE);
        this.haveDisplayedMissedArrows[i] = true;
      }
      //checks to see if they had a turn recently and did not fire an arrow
      if (!effectList.contains(PlayerEffect.MISSED_WUMPUS) || this.turnNum == i + 1) {
        this.haveDisplayedMissedArrows[i] = false;
      }
    }
  }

  /**
   * Sets up the arrays for seeing if things should be displayed or not.
   *
   * @param size the amount of players/size of array.
   */
  private void setUpDisplayedArrays(int size) {
    if (this.haveDisplayedGameOverForPlayerNumber == null || this.modelRestarted()) {
      this.haveDisplayedGameOverForPlayerNumber = new boolean[size];
    }

    if (this.haveDisplayedMissedArrows == null || this.modelRestarted()) {
      this.haveDisplayedMissedArrows = new boolean[size];
    }
  }

  /**
   * checks to see if the game is already over for one of the players to determine if the model has
   * restarted.
   *
   * @return if the model has restarted.
   */
  private boolean modelRestarted() {
    return this.haveDisplayedGameOverForPlayerNumber[this.turnNum - 1];
  }

  @Override
  public void setArrowAmount(int arrowAmount) {
    arrowPanel.setArrowAmount(arrowAmount);

  }

  @Override
  public void setTurn(int playerNumber) {
    this.restartAndTurnPanel.setPlayerTurn(playerNumber);
    this.turnNum = playerNumber;
  }

  @Override
  public void displayError(String error) {

  }

  @Override
  public boolean shouldQuit() {
    return false;
  }

  @Override
  public void close() {
    this.setVisible(false);
    this.setFocusable(false);
    this.dispose();
  }


  @Override
  public void animate() {
    this.setVisible(true);
    this.repaint();
    boolean showGameOver = false;
    for (int i = 0; i < this.playerEffects.size(); i++) {
      List<PlayerEffect> effectList = this.playerEffects.get(i);
      StringBuilder titlemsg = new StringBuilder("Player" + (i + 1) + " ");
      String effectMsg = "";
      for (PlayerEffect effect : effectList) {
        switch (effect) {
          case FELL_INTO_PIT:
            effectMsg = "AHHHHH....\nYou fell into a pit and died!";
            titlemsg.append("Game Over!\nBetter luck next time!");
            showGameOver = true;
            break;
          case RAN_INTO_WUMPUS:
            effectMsg = "CHOMP CHOMP CHOMP...\nYou have been eaten by the Wumpus!";
            titlemsg.append("Game Over!\nBetter luck next time!");
            showGameOver = true;
            break;
          case NO_ARROWS:
            effectMsg = "RAWRRRRRRRRR...\nYou're out of " +
                "arrows and the Wumpus knows!\nHe came to eat you!";
            titlemsg.append("Game Over!\nBetter luck next time!");
            showGameOver = true;
            break;

          case SHOT_WUMPUS:
            titlemsg.append("You Win!\nCongratulations!");

            effectMsg = "RAWRRRRRrrrr...\nYou hear the " +
                "screams of pain from a Wumpus pierced with an arrow!";
            showGameOver = true;
            break;
          default:
            //others do not have a game over msg.

        }
      }
      //the other player shot the wumpus first
      if (effectMsg.length() == 0) {
        effectMsg = "Nooooooo....\nThe other player beat you to the Wumpus!";
        titlemsg.append("Game Over!\nBetter luck next time!");

      }
      if (showGameOver && !this.haveDisplayedGameOverForPlayerNumber[i]) {
        JOptionPane.showMessageDialog(this, effectMsg,
            titlemsg.toString(),
            JOptionPane.PLAIN_MESSAGE);
        showGameOver = false;
        this.haveDisplayedGameOverForPlayerNumber[i] = true;
      }


    }
  }

  @Override
  public void animateGameOver() {
    this.animate();

  }


  @Override
  public void setEventController(EventController listener) {
    this.listener = listener;
    this.arrowPanel.setEventListener(listener);
    this.restartAndTurnPanel.setEventListener(listener);
    this.nodePanel.setEventListener(listener);
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


}
