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

public class SwingMazeView extends JFrame implements IMazeView, KeyListener, ActionListener {
    private final NodePanel nodePanel;
    private final ArrowPanel arrowPanel;
    private final RestartAndTurnPanel restartAndTurnPanel;
    private boolean firstRender;
    private EventController listener;
    private List<List<PlayerEffect>> playerEffects;
    //convoluted names for variables that help correct dispaly for multiple players.
    private boolean[] haveDisplayedGameOverForPlayerNumber;
    private boolean[] haveDisplayedMissedArrows;
    private int turnNum;


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

    private void setUpPanels(List<List<IReadableNode>> nodes) {
        nodePanel.setPreferredSize(new Dimension(nodes.get(0).size() * 64, nodes.size() * 64));

        JScrollPane scrollBarAndPane = new JScrollPane(nodePanel,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //this.setSize(new Dimension(1000, 1000));
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

    }

    @Override
    public void setPossibleMoves(List<Direction> directions) {

    }

    @Override
    public void setPlayerEffects(List<List<PlayerEffect>> effects) {
        this.playerEffects = effects;
        this.setUpDisplayedArrays(effects.size());
        this.displayArrowMissedIfNeeded();


    }

    private void displayArrowMissedIfNeeded() {
        for (int i = 0; i < this.playerEffects.size(); i++) {
            List<PlayerEffect> effectList = this.playerEffects.get(i);
            if (effectList.contains(PlayerEffect.MISSED_WUMPUS) && !this.haveDisplayedMissedArrows[i]) {
                JOptionPane.showMessageDialog(this, "PLayer" + (i+1)
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

    private void setUpDisplayedArrays(int size) {
        if (this.haveDisplayedGameOverForPlayerNumber == null || this.modelRestarted()) {
            this.haveDisplayedGameOverForPlayerNumber = new boolean[size];
        }


        if (this.haveDisplayedMissedArrows == null || this.modelRestarted()) {
            this.haveDisplayedMissedArrows = new boolean[size];
        }
    }

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
    public void open() {
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocus();
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
