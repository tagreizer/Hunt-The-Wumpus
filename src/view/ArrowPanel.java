package view;

import controller.EventController;
import model.Direction;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the panel that handles arrows when playing Hunt the Wumpus. Displays the amount of
 * arrows left, and gives options for shooting the arrows.
 */
final class ArrowPanel extends JPanel implements ActionListener {

  private final JFrame parentPanel;

  private final JButton eastBut;
  private final JButton northBut;
  private final JButton westBut;
  private final JButton southBut;
  private final JButton increment;
  private final JButton decrement;
  private final JButton launchArrow;
  private final JTextField distanceText;
  private final JTextField arrowAmountText;
  private EventController listener;
  private Direction arrowDir;
  private int arrowDistance;


  /**
   * Creates the arrow panel. Is given the parent panel so it can refocus to that panel after
   * clicks.
   *
   * @param parentPanel the parent panel.
   */
  ArrowPanel(JFrame parentPanel) {
    super();
    this.parentPanel = parentPanel;
    this.setPreferredSize(new Dimension(200, 210));
    this.setBackground(Color.GRAY);
    this.arrowDistance = 1;
    this.arrowDir = null;


    eastBut = new JButton("East ");
    eastBut.setActionCommand("East");

    northBut = new JButton("North");
    northBut.setActionCommand("North");

    westBut = new JButton("West");
    westBut.setActionCommand("West");

    southBut = new JButton("South");
    southBut.setActionCommand("South");

    launchArrow = new JButton("Fire Arrow");
    launchArrow.setActionCommand("Fire Arrow");

    JPanel distancePanel = new JPanel();
    distanceText = new JTextField();
    this.setUpTextBox(distancePanel, distanceText, "Arrow\nDistance",
            new Dimension(105, 50), new Dimension(20, 20),
            String.valueOf(this.arrowDistance));

    JPanel amountPanel = new JPanel();
    arrowAmountText = new JTextField();
    this.setUpTextBox(amountPanel, arrowAmountText, "Arrow\nAmount",
            new Dimension(100, 50), new Dimension(20, 20),
            String.valueOf(0));


    increment = new BasicArrowButton(BasicArrowButton.NORTH);
    increment.setActionCommand("Larger Distance");
    decrement = new BasicArrowButton(BasicArrowButton.SOUTH);
    decrement.setActionCommand("Smaller Distance");


    distancePanel.add(increment);
    distancePanel.add(decrement);

    this.add(northBut);
    this.add(southBut);
    this.add(eastBut);
    this.add(westBut);
    this.add(distancePanel);
    this.add(amountPanel);
    this.add(launchArrow);


    this.setActionListener(this);


  }

  /**
   * Sets up text boxes.
   * @param container the box container
   * @param textField the text panel
   * @param title the title of the box
   * @param containerSize the size of the container
   * @param textFieldSize the text field size
   * @param text the text inside
   */
  private void setUpTextBox(JPanel container, JTextField textField,
                            String title, Dimension containerSize, Dimension textFieldSize, String text) {
    Border border = BorderFactory.createTitledBorder(title);
    container.setBorder(border);
    container.setPreferredSize(containerSize);
    textField.setPreferredSize(textFieldSize);
    textField.setText(text);
    textField.setEditable(false);
    container.add(textField);
  }


  /**
   * Sets the action listener for all the buttons.
   * @param listener the listener for the buttons.
   */
  private void setActionListener(ActionListener listener) {
    this.eastBut.addActionListener(listener);
    this.northBut.addActionListener(listener);
    this.westBut.addActionListener(listener);
    this.southBut.addActionListener(listener);
    this.increment.addActionListener(listener);
    this.decrement.addActionListener(listener);
    this.launchArrow.addActionListener(listener);

  }

  /**
   * Sets the eventcontroller for to notify for arrows fired.
   * @param listener the listener for the buttons.
   */
  void setEventListener(EventController listener) {
    this.listener = listener;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "North":
        this.arrowDir = Direction.NORTH;
        this.directionButtonManager(northBut);
        break;
      case "South":
        this.arrowDir = Direction.SOUTH;
        this.directionButtonManager(southBut);
        break;
      case "West":
        this.arrowDir = Direction.WEST;
        this.directionButtonManager(westBut);
        break;
      case "East":
        this.arrowDir = Direction.EAST;
        this.directionButtonManager(eastBut);
        break;
      case "Fire Arrow":
        this.listener.shootArrow(arrowDir, arrowDistance);
        break;
      case "Larger Distance":
        this.arrowDistance++;
        distanceText.setText(String.valueOf(this.arrowDistance));
        break;
      case "Smaller Distance":
        this.arrowDistance--;
        if (this.arrowDistance < 0) {
          this.arrowDistance = 0;
        }
        distanceText.setText(String.valueOf(this.arrowDistance));
        break;
      default:

    }

    // Keystrokes can only be logged by the focused frame. JFrames cannot have their focus locked,
    // and if another panel is interacted with the frame loses focus.
    // Therefore this is needed to reset the focus to the main frame to allow keystrokes to be registered

    this.parentPanel.requestFocus();

  }

  private void directionButtonManager(JButton oddButtonOut) {
    List<JButton> directionButtons = new ArrayList<>(Arrays.asList(northBut, eastBut, westBut, southBut));

    directionButtons.remove(oddButtonOut);
    for (JButton button : directionButtons) {
      button.setEnabled(true);
    }
    oddButtonOut.setEnabled(false);

  }

  void setArrowAmount(int arrowAmount) {
    this.arrowAmountText.setText(String.valueOf(arrowAmount));
  }
}
