package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import controller.EventController;

/**
 * The panel that contains the buttons to restart and create a new game. Also shows the turn
 * number.
 */
public final class RestartAndTurnPanel extends JPanel implements ActionListener {

  private final JFrame parentPanel;
  private final JTextField playerTurn;
  private EventController listener;

  /**
   * Creates a new panel for restarting and new games. Is given the parent so it can refocus to it.
   *
   * @param parentPanel the panel to refocus to after button clicks.
   */
  public RestartAndTurnPanel(JFrame parentPanel) {
    super();
    this.parentPanel = parentPanel;
    this.setPreferredSize(new Dimension(200, 100));
    this.setBackground(Color.GRAY);
    JButton restart = new JButton("Restart Game");
    restart.setActionCommand("Restart");
    restart.addActionListener(this);
    JButton newGame = new JButton("New Game");
    newGame.setActionCommand("New Game");
    newGame.addActionListener(this);

    JPanel turnContainer = new JPanel();
    playerTurn = new JTextField();

    Border border = BorderFactory.createTitledBorder("Current Turn:");
    turnContainer.setBorder(border);
    turnContainer.setPreferredSize(new Dimension(100, 50));
    playerTurn.setPreferredSize(new Dimension(50, 20));
    playerTurn.setText("Player1");
    playerTurn.setEditable(false);
    turnContainer.add(playerTurn);

    this.add(restart);
    this.add(newGame);
    this.add(turnContainer);
  }

  /**
   * Sets the event listener for the panel. Used to signal to restart and make new games.
   *
   * @param listener the listener.
   */
  void setEventListener(EventController listener) {
    this.listener = listener;

  }

  /**
   * Sets the player turn.
   *
   * @param playerTurn the player turn.
   */
  void setPlayerTurn(int playerTurn) {
    this.playerTurn.setText("Player" + playerTurn);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Restart":
        listener.restartGame();
        parentPanel.requestFocus();
        break;
      case "New Game":
        listener.newGame(ViewStyle.SWING);
        break;
      default:
        parentPanel.requestFocus();

    }


  }
}
