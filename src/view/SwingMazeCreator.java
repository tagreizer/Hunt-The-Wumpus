package view;

import model.IMaze;
import model.MazeBuilder;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;

import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * A GUI that allows the user to specify attributes for a new maze, and build it. This runs on its
 * own and does not need a controller.
 */
public final class SwingMazeCreator extends JFrame implements ActionListener {

  private boolean stillCreating;
  private final MazeBuilder builder;
  private final EntryBox[] entryBoxes;
  private final JCheckBox perfect;
  private final JCheckBox wrapping;
  private final JCheckBox players2;

  /**
   * Creates a new creator with the maze builder to make the maze.
   *
   * @param builder the builder that will make the maze.
   */
  public SwingMazeCreator(MazeBuilder builder) {

    this.builder = builder;
    this.stillCreating = true;
    this.setPreferredSize(new Dimension(450, 350));

    GridLayout myLayout = new GridLayout(4, 4);
    this.setLayout(myLayout);

    this.entryBoxes = new EntryBox[]{new EntryBox("Rows", true),
        new EntryBox("Cols", true),
        new EntryBox("StartRow", true),
        new EntryBox("StartCol", true),
        new EntryBox("GoalRow", true),
        new EntryBox("GoalCol", true),
        new EntryBox("Bats", true),
        new EntryBox("Pits", true),
        new EntryBox("WallsRemaining", false),
        new EntryBox("ArrowCount", false),
        new EntryBox("Seed", true)
    };
    for (EntryBox box : this.entryBoxes) {
      this.add(box);
    }
    JPanel perfectCont = new JPanel();
    perfect = new JCheckBox("PerfectMaze");
    perfectCont.add(perfect);

    JPanel wrappingCont = new JPanel();
    wrapping = new JCheckBox("WrappingMaze");
    wrappingCont.add(wrapping);

    JPanel player2Cont = new JPanel();
    players2 = new JCheckBox("2 Player");
    player2Cont.add(players2);

    JButton create = new JButton("Create");
    create.addActionListener(this);
    create.setActionCommand("create Maze");

    this.add(create);
    this.add(perfectCont);
    this.add(wrappingCont);
    this.add(player2Cont);

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setTitle("Hunt The Wumpus Game Creator");

    this.pack();
    this.setLocationRelativeTo(null); // center the frame
  }

  /**
   * The driver for this GUI, it does not use a controller and runs on its own. Once finished this
   * will return a built maze object. If the maze cannot be built according to the spec it will
   * display an error and allow the user to try again.
   *
   * @return the maze it creates
   */
  public IMaze create() {
    IMaze model;
    while (true) {
      this.setVisible(true);
      this.repaint();

      if (!this.stillCreating) {
        try {

          model = this.build();
          break;

        } catch (IllegalArgumentException e) {
          this.stillCreating = true;
          JOptionPane.showMessageDialog(this, e.getMessage(),
              "WHOOPSY",
              JOptionPane.ERROR_MESSAGE);
        }

      }
    }
    this.dispose();
    return model;


  }

  /**
   * Builds the maze based off of the given fields.
   *
   * @return an IMaze object to use.
   */
  private IMaze build() {
    //This method is fairly terse as i didnt know how to abstract it.
    if (this.entryBoxes[0].isRandom) {
      builder.randomizeRows();
    } else {
      builder.setRows(entryBoxes[0].getValue());
    }

    if (this.entryBoxes[1].isRandom) {
      builder.randomizeCols();
    } else {
      builder.setCols(entryBoxes[1].getValue());
    }
    if (this.entryBoxes[2].isRandom) {
      builder.randomizeSRow();
    } else {
      builder.setsRow(entryBoxes[2].getValue());
    }

    if (this.entryBoxes[3].isRandom) {
      builder.randomizeSCol();
    } else {
      builder.setsCol(entryBoxes[3].getValue());
    }
    if (this.entryBoxes[4].isRandom) {
      builder.randomizeGRow();
    } else {
      builder.setgRow(entryBoxes[4].getValue());
    }

    if (this.entryBoxes[5].isRandom) {
      builder.randomizeGCol();
    } else {
      builder.setgCol(entryBoxes[5].getValue());
    }
    if (this.entryBoxes[6].isRandom) {
      builder.randomizeBatsPercentage();
    } else {
      builder.setBatsPercentage(entryBoxes[6].getValue());
    }

    if (this.entryBoxes[7].isRandom) {
      builder.randomizePitsPercentage();
    } else {
      builder.setPitsPercentage(entryBoxes[7].getValue());
    }

    builder.setWallsRemaining(entryBoxes[8].getValue());

    builder.setArrowCount(entryBoxes[9].getValue());

    if (this.entryBoxes[10].isRandom) {
      builder.randomizeSeed();
    } else {
      builder.setSeed(entryBoxes[10].getValue());
    }
    builder.setPerfect(this.perfect.isSelected());
    builder.setWrapping(this.wrapping.isSelected());
    builder.setPlayerCount(this.players2.isSelected() ? 2 : 1);

    return builder.build();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("create Maze")) {
      this.stillCreating = false;
    }

  }

  /**
   * A collection of Jpanels grouped into one that is used as the ibput areas for the maze.
   */
  private class EntryBox extends JPanel implements ActionListener {

    private String name;
    private JTextField textField;
    private JCheckBox checkBox;
    private boolean allowRandom;
    private boolean isRandom;


    /**
     * Creates an entry area with the given options.
     *
     * @param name        the name of the attribute
     * @param allowRandom if this attribute can be randomized
     */
    EntryBox(String name, boolean allowRandom) {
      this.name = name;

      this.allowRandom = allowRandom;
      this.isRandom = false;

      this.textField = new JTextField();
      this.checkBox = new JCheckBox();
      this.checkBox.addActionListener(this);

      this.setUpTextBox(
          new Dimension(50, 40), new Dimension(30, 20));

    }

    /**
     * Sets up the text box used for entry of vaules.
     *
     * @param containerSize the size the outer box should be.
     * @param textFieldSize the size the text field should be.
     */
    private void setUpTextBox(Dimension containerSize, Dimension textFieldSize) {
      Border border = BorderFactory.createTitledBorder(this.name);
      this.setBorder(border);
      this.setPreferredSize(containerSize);
      textField.setPreferredSize(textFieldSize);
      textField.setText("0");
      textField.setEditable(true);
      if (this.allowRandom) {
        this.checkBox.setText("Randomize");
        this.add(this.checkBox);
      }
      this.add(textField);
    }

    /**
     * Gets the value inside the box. If there isnt a valid number this returns 0.
     *
     * @return gets the value in the box.
     */
    private int getValue() {
      int value;
      try {
        value = Integer.parseInt(this.textField.getText());
      } catch (NumberFormatException e) {
        value = 0;
      }
      return value;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals("Randomize")) {
        if (this.isRandom) {
          this.textField.setEditable(true);
          this.textField.setText("0");
          this.isRandom = false;
        } else {
          this.textField.setText("");
          this.textField.setEditable(false);
          this.isRandom = true;
        }
      }
    }
  }

}
