package view;

import controller.EventController;
import model.Direction;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ArrowPanel extends JPanel implements ActionListener {

    private JButton eastBut;
    private JButton northBut;
    private JButton westBut;
    private JButton southBut;
    private JButton increment;
    private JButton decrement;
    private JButton launchArrow;
    private JTextField distanceText;
    private JTextField arrowOutcome;
    private EventController listener;

    private final Dimension inputSize = new Dimension(140, 50);

    ArrowPanel() {
        super();
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.GRAY);


        eastBut = new JButton("East ");
        eastBut.setActionCommand("East");

        northBut = new  JButton("North");
        northBut.setActionCommand("North");

        westBut = new  JButton("West");
        westBut.setActionCommand("West");

        southBut = new JButton("South");
        southBut.setActionCommand("South");

        launchArrow = new JButton("Fire Arrow");
        launchArrow.setActionCommand("Fire Arrow");

        JPanel distancePanel = new JPanel();
        distanceText = new JTextField();

        increment = new BasicArrowButton(BasicArrowButton.NORTH);
        increment.setActionCommand("Larger Distance");
        decrement = new BasicArrowButton(BasicArrowButton.SOUTH);
        increment.setActionCommand("Smaller Distance");

        Border border = BorderFactory.createTitledBorder("Arrow\nDistance");
        distancePanel.setBorder(border);
        distancePanel.setPreferredSize(inputSize);
        distanceText.setPreferredSize(new Dimension(20, 20));
        distanceText.setText("0");
        distanceText.setEditable(false);
        distancePanel.add(distanceText);
        distancePanel.add(increment);
        distancePanel.add(decrement);

        this.add(northBut);
        this.add(southBut);
        this.add(eastBut);
        this.add(westBut);
        this.add(distancePanel);
        this.add(launchArrow);

        //this.setActionListener(this);






    }



    private void setActionListener(ActionListener listener) {
        this.eastBut.addActionListener(listener);
        this.northBut.addActionListener(listener);
        this.westBut.addActionListener(listener);
        this.southBut.addActionListener(listener);
        this.increment.addActionListener(listener);
        this.decrement.addActionListener(listener);
        this.launchArrow.addActionListener(listener);

    }

    void setEventListener(EventController listener) {
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
