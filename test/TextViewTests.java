import controller.IMazeController;
import controller.MazeController;
import model.*;
import view.IMazeView;
import view.TextMazeView;

import org.junit.Before;
import org.junit.Test;


import java.io.StringReader;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TextViewTests {

  @Test
  public void playerTransportedBySuperBatAndOutOfArrows() {
    StringReader in = new StringReader("m n 1 m e 1 s w 1 s w 1");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(8, 8, true, 0, 0, 7, 7, 10, 10, 325, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new MazeController(m, view);
    controller.runGame();
    assertEquals("You are in cave (0,0)\n" +
            "You can move: North\n" +
            "Shoot or Move (S-M)?:\n" +
            "Please enter the first letter of: North\n" +
            "\n" +
            "You are in cave (7,0)\n" +
            "There is the stench of Wumpus in the air...\n" +
            "You can move: North, South, East, West\n" +
            "Shoot or Move (S-M)?:\n" +
            "Please enter S for Shoot and M for Move.\n" +
            "\n" +
            "Please enter the first letter of: North, South, East, West\n" +
            "\n" +
            "WHOOOOSH...\n" +
            "A bat grabbed you and flew you to another cave!\n" +
            "You are in cave (7,3)\n" +
            "You can move: West\n" +
            "Shoot or Move (S-M)?:\n" +
            "Please enter S for Shoot and M for Move.\n" +
            "\n" +
            "Please enter the first letter of: West\n" +
            "\n" +
            "How far will you shoot your arrow?\n" +
            "Please enter an integer value: \n" +
            "Clank...\n" +
            "You hear your arrow echo off stone! You Missed!\n" +
            "You are in cave (7,3)\n" +
            "You can move: West\n" +
            "Shoot or Move (S-M)?:\n" +
            "Please enter the first letter of: West\n" +
            "\n" +
            "How far will you shoot your arrow?\n" +
            "Please enter an integer value: \n" +
            "Clank...\n" +
            "You hear your arrow echo off stone! You Missed!\n" +
            "RAWRRRRRRRRR...\n" +
            "You're out of arrows and the Wumpus knows!\n" +
            "He came to eat you!\n" +
            "Game Over!\n" +
            "Better luck next time!\n", out.toString());

  }

  @Test
  public void playerFallingIntoPit() {

  }

  @Test
  public void eatenByWumpus() {
    StringReader in = new StringReader("m n 1 m w 1");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(8, 8, true, 0, 0, 7, 7, 10, 10, 325, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new MazeController(m, view);
    controller.runGame();
    assertEquals("You are in cave (0,0)\n" +
            "You can move: North\n" +
            "Shoot or Move (S-M)?:\n" +
            "Please enter the first letter of: North\n" +
            "\n" +
            "You are in cave (7,0)\n" +
            "There is the stench of Wumpus in the air...\n" +
            "You can move: North, South, East, West\n" +
            "Shoot or Move (S-M)?:\n" +
            "Please enter S for Shoot and M for Move.\n" +
            "\n" +
            "Please enter the first letter of: North, South, East, West\n" +
            "\n" +
            "CHOMP CHOMP CHOMP...\n" +
            "You have been eaten by the Wumpus!\n" +
            "Game Over!\n" +
            "Better luck next time!\n", out.toString());

  }

  @Test
  public void killedWumpus() {
    StringReader in = new StringReader("m n 1 s w 1");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(8, 8, true, 0, 0, 7, 7, 10, 10, 325, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new MazeController(m, view);
    controller.runGame();
    assertEquals("You are in cave (0,0)\n" +
            "You can move: North\n" +
            "Shoot or Move (S-M)?:\n" +
            "Please enter the first letter of: North\n" +
            "\n" +
            "You are in cave (7,0)\n" +
            "There is the stench of Wumpus in the air...\n" +
            "You can move: North, South, East, West\n" +
            "Shoot or Move (S-M)?:\n" +
            "Please enter S for Shoot and M for Move.\n" +
            "\n" +
            "Please enter the first letter of: North, South, East, West\n" +
            "\n" +
            "How far will you shoot your arrow?\n" +
            "Please enter an integer value: \n" +
            "RAWRRRRRrrrr...\n" +
            "You hear the screams of pain from a Wumpus pierced with an arrow!\n" +
            "You Win!\n" +
            "Congratulations!\n", out.toString());

  }
}
