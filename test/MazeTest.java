import org.junit.Before;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;


import model.Direction;
import model.IMaze;
import model.IReadableNode;
import model.Maze;
import model.Position;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the maze objects.
 */
public class MazeTest {
  IMaze smallPerfectNW;

  private int countEdges(IMaze maze) {
    int edges = 0;
    List<List<IReadableNode>> nodelist = maze.getNodes();

    for (List<IReadableNode> nodes : nodelist) {
      for (IReadableNode node : nodes) {
        edges += node.getConnectedDirs().size();
      }
    }
    // edges will come up twice so divide by two to get real amount.
    return edges / 2;
  }

  @Before
  public void setUp() throws Exception {
    smallPerfectNW = new Maze(3, 4, false, 0, 0, 2, 3, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badRow() {
    new Maze(-1, 4, false, 0, 0, 2, 3, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badCol() {
    new Maze(2, -1, false, 0, 0, 2, 3, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tooSmall() {
    new Maze(1, 1, false, 0, 0, 0, 0, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tooManyWalls() {
    new Maze(5, 5, 100, false, 0, 0, 1, 1, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badSROW() {
    new Maze(5, 5, false, -1, 0, 1, 1, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badSCol() {
    new Maze(5, 5, false, 0, -1, 1, 1, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGRow() {
    new Maze(5, 5, false, 0, 0, -1, 1, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGCol() {
    new Maze(5, 5, false, 0, 0, 1, -1, 234);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sameStartAndEnd() {
    new Maze(5, 5, false, 1, 1, 1, 1, 234);
  }


  @Test
  public void testPerfectConstructor() {
    IMaze m = new Maze(2, 2, true, 0, 0, 1, 1, 67489);
    IMaze m2 = new Maze(2, 2, false, 0, 0, 1, 1, 67489);

    try {
      assertEquals("P 0-\n" +
              "|   \n" +
              "0 G \n" +
              "  | \n", m.toString());
      assertEquals(3, this.countEdges(m));
      assertEquals("P-0 \n" +
              "|   \n" +
              "0-G \n" +
              "    \n", m2.toString());
      assertEquals(3, this.countEdges(m2));

      assertEquals("P-0-0-0 \n" +
              "    |   \n" +
              "0-0-0-T \n" +
              "|       \n" +
              "M-M-0-G \n" +
              "        \n", smallPerfectNW.toString());
      assertEquals(11, this.countEdges(smallPerfectNW));
    } catch (Exception e) {
      fail(e.getMessage());
    }


  }

  @Test
  public void testNonWrappedNonPerfectMaze() {
    IMaze m = new Maze(5, 5, 14, false, 0, 0, 2, 2, 12345);
    assertEquals("P-0-0-M-0 \n" +
            "        | \n" +
            "0-M-0-0-M \n" +
            "      |   \n" +
            "0-0-G-0-0 \n" +
            "| |       \n" +
            "T-0-0-T-0 \n" +
            "      | | \n" +
            "0-0-M-0-M \n" +
            "          \n", m.toString());
    assertEquals(26, this.countEdges(m));
  }

  @Test
  public void testWrappedNonPerfectMaze() {
    IMaze m = new Maze(3, 3, 0, true, 0, 0, 2, 2, 9874353);
    assertEquals("P-0-0-\n" +
            "| | | \n" +
            "0-0-M-\n" +
            "| | | \n" +
            "0-0-G-\n" +
            "| | | \n", m.toString());
    assertEquals(18, this.countEdges(m));
  }


  @Test
  public void sampleGameRunThroughPerfectMaze() {
    //Visits every node
    //Reaches the goal
    assertEquals("P-0-0-0 \n" +
            "    |   \n" +
            "0-0-0-T \n" +
            "|       \n" +
            "M-M-0-G \n" +
            "        \n", smallPerfectNW.toString());
    assertTrue(smallPerfectNW.possiblePlayerMoves().contains(Direction.EAST));
    assertEquals(1, smallPerfectNW.possiblePlayerMoves().size());
    assertEquals(0, smallPerfectNW.getPlayerGold());

    smallPerfectNW.movePlayer(Direction.EAST);

    assertEquals("S-P-0-0 \n" +
            "    |   \n" +
            "0-0-0-T \n" +
            "|       \n" +
            "M-M-0-G \n" +
            "        \n", smallPerfectNW.toString());
    assertTrue(smallPerfectNW.possiblePlayerMoves()
            .containsAll(Arrays.asList(Direction.EAST, Direction.WEST)));
    assertEquals(2, smallPerfectNW.possiblePlayerMoves().size());
    assertEquals(0, smallPerfectNW.getPlayerGold());

    smallPerfectNW.movePlayer(Direction.EAST);

    assertEquals("S-0-P-0 \n" +
            "    |   \n" +
            "0-0-0-T \n" +
            "|       \n" +
            "M-M-0-G \n" +
            "        \n", smallPerfectNW.toString());
    assertTrue(smallPerfectNW.possiblePlayerMoves()
            .containsAll(Arrays.asList(Direction.EAST, Direction.WEST, Direction.SOUTH)));
    assertEquals(3, smallPerfectNW.possiblePlayerMoves().size());
    assertEquals(0, smallPerfectNW.getPlayerGold());
    smallPerfectNW.movePlayer(Direction.EAST);
    smallPerfectNW.movePlayer(Direction.WEST);

    smallPerfectNW.movePlayer(Direction.SOUTH);

    assertEquals("S-0-0-0 \n" +
            "    |   \n" +
            "0-0-P-T \n" +
            "|       \n" +
            "M-M-0-G \n" +
            "        \n", smallPerfectNW.toString());
    assertTrue(smallPerfectNW.possiblePlayerMoves()
            .containsAll(Arrays.asList(Direction.EAST, Direction.WEST, Direction.NORTH)));
    assertEquals(3, smallPerfectNW.possiblePlayerMoves().size());
    assertEquals(0, smallPerfectNW.getPlayerGold());

    smallPerfectNW.movePlayer(Direction.WEST);
    smallPerfectNW.movePlayer(Direction.WEST);
    assertEquals(0, smallPerfectNW.getPlayerGold());
    smallPerfectNW.movePlayer(Direction.SOUTH);
    assertEquals(10, smallPerfectNW.getPlayerGold());
    assertEquals("S-0-0-0 \n" +
            "    |   \n" +
            "0-0-0-T \n" +
            "|       \n" +
            "P-M-0-G \n" +
            "        \n", smallPerfectNW.toString());

    smallPerfectNW.movePlayer(Direction.EAST);
    assertEquals(20, smallPerfectNW.getPlayerGold());
    smallPerfectNW.movePlayer(Direction.WEST);
    smallPerfectNW.movePlayer(Direction.EAST);
    smallPerfectNW.movePlayer(Direction.WEST);
    assertEquals(20, smallPerfectNW.getPlayerGold());
    smallPerfectNW.movePlayer(Direction.NORTH);
    smallPerfectNW.movePlayer(Direction.EAST);
    smallPerfectNW.movePlayer(Direction.EAST);
    assertEquals(20, smallPerfectNW.getPlayerGold());
    smallPerfectNW.movePlayer(Direction.EAST);
    assertEquals(18, smallPerfectNW.getPlayerGold());

    assertEquals("S-0-0-0 \n" +
            "    |   \n" +
            "0-0-0-P \n" +
            "|       \n" +
            "M-M-0-G \n" +
            "        \n", smallPerfectNW.toString());
    assertFalse(smallPerfectNW.isGameOver());
    smallPerfectNW.movePlayer(Direction.WEST);
    smallPerfectNW.movePlayer(Direction.WEST);
    smallPerfectNW.movePlayer(Direction.WEST);
    smallPerfectNW.movePlayer(Direction.SOUTH);
    smallPerfectNW.movePlayer(Direction.EAST);
    assertEquals(18, smallPerfectNW.getPlayerGold());


    assertEquals("S-0-0-0 \n" +
            "    |   \n" +
            "0-0-0-T \n" +
            "|       \n" +
            "M-P-0-G \n" +
            "        \n", smallPerfectNW.toString());

    assertEquals(18, smallPerfectNW.getPlayerGold());

    assertFalse(smallPerfectNW.isGameOver());
    smallPerfectNW.movePlayer(Direction.EAST);
    assertFalse(smallPerfectNW.isGameOver());
    smallPerfectNW.movePlayer(Direction.EAST);

    assertEquals("S-0-0-0 \n" +
            "    |   \n" +
            "0-0-0-T \n" +
            "|       \n" +
            "M-M-0-P \n" +
            "        \n", smallPerfectNW.toString());
    assertTrue(smallPerfectNW.isGameOver());

  }


  @Test
  public void getPlayerLocation() {
    assertEquals(new Position(0, 0), smallPerfectNW.getPlayerLocation());
    smallPerfectNW.movePlayer(Direction.EAST);
    assertEquals(new Position(0, 1), smallPerfectNW.getPlayerLocation());
    smallPerfectNW.movePlayer(Direction.EAST);
    assertEquals(new Position(0, 2), smallPerfectNW.getPlayerLocation());
    smallPerfectNW.movePlayer(Direction.SOUTH);
    assertEquals(new Position(1, 2), smallPerfectNW.getPlayerLocation());
    IMaze m = new Maze(3, 13, true, 2, 11, 0, 0);
    assertEquals(new Position(2, 11), m.getPlayerLocation());

  }

  @Test
  public void getNodes() {
    List<List<IReadableNode>> nodelist = smallPerfectNW.getNodes();
    assertEquals(3, nodelist.size());
    assertEquals(4, nodelist.get(0).size());
    assertEquals(new Position(2, 3), nodelist.get(2).get(3).getPosition());

    IMaze m = new Maze(2, 5, 6, true, 0, 0, 1, 1);

    nodelist = m.getNodes();
    assertEquals(2, nodelist.size());
    assertEquals(5, nodelist.get(0).size());
    assertEquals(new Position(1, 3), nodelist.get(1).get(3).getPosition());
  }

  @Test
  public void wrappedMazeTraverse() {
    IMaze m = new Maze(2, 2, 0, true, 0, 0, 1, 1, 101010);

    assertEquals("P-0-\n" +
            "| | \n" +
            "0-G-\n" +
            "| | \n", m.toString());

    assertTrue(m.possiblePlayerMoves()
            .containsAll(Arrays.asList(Direction.NORTH, Direction.SOUTH,
                    Direction.WEST, Direction.EAST)));
    m.movePlayer(Direction.NORTH);
    assertEquals("S-0-\n" +
            "| | \n" +
            "P-G-\n" +
            "| | \n", m.toString());
    m.movePlayer(Direction.NORTH);
    assertEquals("P-0-\n" +
            "| | \n" +
            "0-G-\n" +
            "| | \n", m.toString());
    m.movePlayer(Direction.EAST);
    assertEquals("S-P-\n" +
            "| | \n" +
            "0-G-\n" +
            "| | \n", m.toString());
    m.movePlayer(Direction.EAST);
    assertEquals("P-0-\n" +
            "| | \n" +
            "0-G-\n" +
            "| | \n", m.toString());
    assertFalse(m.isGameOver());
    m.movePlayer(Direction.WEST);
    assertEquals("S-P-\n" +
            "| | \n" +
            "0-G-\n" +
            "| | \n", m.toString());
    assertFalse(m.isGameOver());
    m.movePlayer(Direction.NORTH);
    assertEquals("S-0-\n" +
            "| | \n" +
            "0-P-\n" +
            "| | \n", m.toString());
    assertTrue(m.isGameOver());
    try {
      m.movePlayer(Direction.NORTH);
      fail("Shouldnt be able to move");

    } catch (IllegalStateException e) {
      assertEquals("The game is over", e.getMessage());
    }
  }

}