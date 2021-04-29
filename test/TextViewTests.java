import controller.IMazeController;
import controller.TextMazeController;

import model.IMaze;
import model.Maze;
import view.IMazeView;
import view.TextMazeView;


import org.junit.Test;


import java.io.StringReader;


import static org.junit.Assert.assertEquals;

/**
 * Runs tests for the text view version of the maze.
 */
public class TextViewTests {

  @Test
  public void tryingBadInputs() {
    StringReader in = new StringReader("a sadf f 3 m " +
            " oiu null w  23 m 23 n s asf  w -1 s e 1 w 1 s w 1");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(8, 8, true, 0, 0, 7, 7, 10, 10, 325, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new TextMazeController(m, view);
    controller.runGame();
    assertEquals("Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (0,0)\n"
        + "You can move: North\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter S for Shoot and M for Move.\n"
        + "\n"
        + "Please enter S for Shoot and M for Move.\n"
        + "\n"
        + "Please enter S for Shoot and M for Move.\n"
        + "\n"
        + "Please enter S for Shoot and M for Move.\n"
        + "\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (7,0)\n"
        + "There is the stench of Wumpus in the air...\n"
        + "You can move: North, South, East, West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North, South, East, West\n"
        + "\n"
        + "Please enter the first letter of: North, South, East, West\n"
        + "\n"
        + "How far will you shoot your arrow?\n"
        + "Please enter an integer value: \n"
        + "You must shoot an arrow greater than 1 units far\n"
        + "Player1's turn:\n"
        + "You are in cave (7,0)\n"
        + "There is the stench of Wumpus in the air...\n"
        + "You can move: North, South, East, West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North, South, East, West\n"
        + "\n"
        + "How far will you shoot your arrow?\n"
        + "Please enter an integer value: \n"
        + "Player1's Effects:\n"
        + "Clank...\n"
        + "You hear your arrow echo off stone! You Missed!\n"
        + "Player1's turn:\n"
        + "You are in cave (7,0)\n"
        + "There is the stench of Wumpus in the air...\n"
        + "You can move: North, South, East, West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 1 arrows left.\n"
        + "Please enter S for Shoot and M for Move.\n"
        + "\n"
        + "Please enter S for Shoot and M for Move.\n"
        + "\n"
        + "Please enter the first letter of: North, South, East, West\n"
        + "\n"
        + "How far will you shoot your arrow?\n"
        + "Please enter an integer value: \n"
        + "Player1's Effects:\n"
        + "RAWRRRRRrrrr...\n"
        + "You hear the screams of pain from a Wumpus pierced with an arrow!\n"
        + "Player1's Outcome:\n"
        + "You Win!\n"
        + "Congratulations!\n"
        + "\n", out.toString());
  }

  @Test
  public void playerTransportedBySuperBatAndOutOfArrows() {
    StringReader in = new StringReader("m n m e s w 1 s w 1");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(8, 8, true, 0, 0, 7, 7, 10, 10, 325, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new TextMazeController(m, view);
    controller.runGame();
    assertEquals("Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (0,0)\n"
        + "You can move: North\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (7,0)\n"
        + "There is the stench of Wumpus in the air...\n"
        + "You can move: North, South, East, West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North, South, East, West\n"
        + "\n"
        + "Player1's Effects:\n"
        + "WHOOOOSH...\n"
        + "A bat grabbed you and flew you to another cave!\n"
        + "Player1's turn:\n"
        + "You are in cave (7,3)\n"
        + "You can move: West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: West\n"
        + "\n"
        + "How far will you shoot your arrow?\n"
        + "Please enter an integer value: \n"
        + "Player1's Effects:\n"
        + "Clank...\n"
        + "You hear your arrow echo off stone! You Missed!\n"
        + "Player1's turn:\n"
        + "You are in cave (7,3)\n"
        + "You can move: West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 1 arrows left.\n"
        + "Please enter the first letter of: West\n"
        + "\n"
        + "How far will you shoot your arrow?\n"
        + "Please enter an integer value: \n"
        + "Player1's Effects:\n"
        + "Clank...\n"
        + "You hear your arrow echo off stone! You Missed!\n"
        + "RAWRRRRRRRRR...\n"
        + "You're out of arrows and the Wumpus knows!\n"
        + "He came to eat you!\n"
        + "Player1's Outcome:\n"
        + "Game Over!\n"
        + "Better luck next time!\n"
        + "\n", out.toString());

  }

  @Test
  public void playerFallingIntoPit() {
    StringReader in = new StringReader("m s");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(5, 5, true, 0, 0, 4, 4, 5, 20, 325, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new TextMazeController(m, view);
    controller.runGame();
    assertEquals("Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (0,0)\n"
        + "A cold wind blows...\n"
        + "You can move: East, South\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: East, South\n"
        + "\n"
        + "Player1's Effects:\n"
        + "AHHHHH....\n"
        + "You fell into a pit and died!\n"
        + "Player1's Outcome:\n"
        + "Game Over!\n"
        + "Better luck next time!\n"
        + "\n", out.toString());

  }

  @Test
  public void eatenByWumpus() {
    StringReader in = new StringReader("m n m w");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(8, 8, true, 0, 0, 7, 7, 10, 10, 325, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new TextMazeController(m, view);
    controller.runGame();
    assertEquals("Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (0,0)\n"
        + "You can move: North\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (7,0)\n"
        + "There is the stench of Wumpus in the air...\n"
        + "You can move: North, South, East, West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North, South, East, West\n"
        + "\n"
        + "Player1's Effects:\n"
        + "CHOMP CHOMP CHOMP...\n"
        + "You have been eaten by the Wumpus!\n"
        + "Player1's Outcome:\n"
        + "Game Over!\n"
        + "Better luck next time!\n"
        + "\n", out.toString());

  }

  @Test
  public void killedWumpus() {
    StringReader in = new StringReader("m n s w 1");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(8, 8, true, 0, 0, 7, 7, 10, 10, 325, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new TextMazeController(m, view);
    controller.runGame();
    assertEquals("Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (0,0)\n"
        + "You can move: North\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Player1's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (7,0)\n"
        + "There is the stench of Wumpus in the air...\n"
        + "You can move: North, South, East, West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North, South, East, West\n"
        + "\n"
        + "How far will you shoot your arrow?\n"
        + "Please enter an integer value: \n"
        + "Player1's Effects:\n"
        + "RAWRRRRRrrrr...\n"
        + "You hear the screams of pain from a Wumpus pierced with an arrow!\n"
        + "Player1's Outcome:\n"
        + "You Win!\n"
        + "Congratulations!\n"
        + "\n", out.toString());

  }

  @Test
  public void killedWumpus2Player() {
    StringReader in = new StringReader("m n m n s w 1");
    StringBuilder out = new StringBuilder("");
    IMaze m = new Maze(8, 8, true, 0, 0, 7, 7, 10, 10, 325, 2, 2);
    IMazeView view = new TextMazeView(out, in);
    IMazeController controller = new TextMazeController(m, view);
    controller.runGame();
    assertEquals("Player1's Effects:\n"
        + "Player2's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (0,0)\n"
        + "You can move: North\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Player1's Effects:\n"
        + "Player2's Effects:\n"
        + "Player2's turn:\n"
        + "You are in cave (0,0)\n"
        + "You can move: North\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North\n"
        + "\n"
        + "Player1's Effects:\n"
        + "Player2's Effects:\n"
        + "Player1's turn:\n"
        + "You are in cave (7,0)\n"
        + "There is the stench of Wumpus in the air...\n"
        + "You can move: North, South, East, West\n"
        + "Shoot or Move (S-M)?:\n"
        + "You have 2 arrows left.\n"
        + "Please enter the first letter of: North, South, East, West\n"
        + "\n"
        + "How far will you shoot your arrow?\n"
        + "Please enter an integer value: \n"
        + "Player1's Effects:\n"
        + "RAWRRRRRrrrr...\n"
        + "You hear the screams of pain from a Wumpus pierced with an arrow!\n"
        + "Player2's Effects:\n"
        + "Player1's Outcome:\n"
        + "You Win!\n"
        + "Congratulations!\n"
        + "Player2's Outcome:\n"
        + "Game Over!\n"
        + "Better luck next time!\n"
        + "\n", out.toString());

  }


}
