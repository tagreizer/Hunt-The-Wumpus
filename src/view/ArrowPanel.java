package view;

import model.Direction;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;

class ArrowPanel extends JPanel {
    private Direction arrowDir;
    private int arrowDistance;
    private JButton eArrow;
    private JButton nArrow;
    private JButton wArrow;
    private JButton sArrow;


    ArrowPanel() {
        super();
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.GRAY);
        this.arrowDistance = 1;
        this.arrowDir = null;
        eArrow = new BasicArrowButton(BasicArrowButton.EAST);
        nArrow = new BasicArrowButton(BasicArrowButton.NORTH);
        wArrow = new BasicArrowButton(BasicArrowButton.WEST);
        sArrow = new BasicArrowButton(BasicArrowButton.SOUTH);

//        JPanel centerButtonArea = new JPanel();
//        JPanel eastButtonArea = new JPanel();
//        JPanel northButtonArea = new JPanel();
//        JPanel westButtonArea = new JPanel();
//        JPanel southButtonArea = new JPanel();
//
//        eastButtonArea.add(eArrow);
//        northButtonArea.add(nArrow);
//        westButtonArea.add(wArrow);
//        southButtonArea.add(sArrow);

        this.add(eArrow, BorderLayout.EAST);
        this.add(nArrow, BorderLayout.NORTH);
        this.add(wArrow, BorderLayout.SOUTH);
        this.add(sArrow, BorderLayout.WEST);



    }



    void setActionListener(ActionListener listener) {
        this.eArrow.addActionListener(listener);
        this.nArrow.addActionListener(listener);
        this.wArrow.addActionListener(listener);
        this.sArrow.addActionListener(listener);

    }

}
