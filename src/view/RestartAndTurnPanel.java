package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;
import javax.swing.border.Border;

import controller.EventController;
import model.MazeBuilder;

public class RestartAndTurnPanel extends JPanel implements ActionListener {

  private JFrame parentPanel;
  private JButton restart;
  private JButton newGame;
  private JTextField playerTurn;
  private EventController listener;

  public RestartAndTurnPanel(JFrame parentPanel) {
    super();
    this.parentPanel = parentPanel;
    this.setPreferredSize(new Dimension(200, 100));
    this.setBackground(Color.GRAY);
    restart = new JButton("Restart Game");
    restart.setActionCommand("Restart");
    restart.addActionListener(this);
    newGame = new JButton("New Game");
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

  void setEventListener(EventController listener) {
    this.listener = listener;

  }

  void setPlayerTurn(int playerTurn) {
    this.playerTurn.setText("Player"+playerTurn);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Restart":
        listener.restartGame();
        parentPanel.requestFocus();
        break;
      case "New Game":
        //parentPanel.dispatchEvent(new WindowEvent(parentPanel, WindowEvent.WINDOW_CLOSING));
        //parentPanel.setVisible(false);



          listener.newGame();


          break;
      default:
        parentPanel.requestFocus();

    }


  }
}
