package view;

import model.IMaze;
import model.MazeBuilder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMazeCreator extends JFrame implements ActionListener{
    private boolean stillCreating;
    private MazeBuilder builder;
    private EntryBox[] entryBoxes;
    private JCheckBox perfect;
    private JCheckBox wrapping;
    private JCheckBox players2;
    private JButton create;

    public SwingMazeCreator(MazeBuilder builder) {


        this.builder = builder;
        this.stillCreating = true;
        this.setPreferredSize(new Dimension(450, 350));

        GridLayout myLayout = new GridLayout(4, 4);
        this.setLayout(myLayout);

        this.entryBoxes = new EntryBox[]{new EntryBox("Rows", true, this),
                new EntryBox("Cols", true, this),
                new EntryBox("StartRow", true, this),
                new EntryBox("StartCol", true, this),
                new EntryBox("GoalRow", true, this),
                new EntryBox("GoalCol", true, this),
                new EntryBox("Bats", true, this),
                new EntryBox("Pits", true, this),
                new EntryBox("WallsRemaining", false, this),
                new EntryBox("ArrowCount", false, this),
                new EntryBox("Seed", true, this)
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

        create = new JButton("Create");
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

    public IMaze create() {
        IMaze model = null;
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

    private IMaze build() {
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
        //ADD TWO PLAYER

        return builder.build();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("create Maze")) {
            this.stillCreating = false;
        }

    }


    private class EntryBox extends JPanel implements ActionListener {
        private String name;
        private JTextField textField;
        private JCheckBox checkBox;
        private boolean allowRandom;
        private boolean isRandom;
        private SwingMazeCreator parent;

        EntryBox(String name, boolean allowRandom, SwingMazeCreator parent) {
            this.name = name;

            this.allowRandom = allowRandom;
            this.parent = parent;
            this.isRandom = false;

            this.textField = new JTextField();
            this.checkBox = new JCheckBox();
            this.checkBox.addActionListener(this);

            this.setUpTextBox(
                    new Dimension(50, 40), new Dimension(30, 20));

        }
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
