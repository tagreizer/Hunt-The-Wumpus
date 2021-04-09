package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Represents a maze of {@link IReadableNode}. There is a starting and ending point, as well as a
 * player that can traverse the maze from the start to the end. Mazes can either wrap(connect on the
 * edges of the map) or not. They can also be a perfect maze(minimum spanning tree) or not.
 */
public class Maze implements IMaze {
  private final IWritableNode[][] board;
  private final List<IEdge> edges;
  private final Player player1;
  private boolean isGameOver;
  private boolean victory;
  private long seed;


  /**
   * Generates a perfect maze with a random seed.
   *
   * @param rows       the rows in the maze
   * @param cols       the cols in the maze
   * @param isWrapping whether or not the maze wraps.
   * @param sRow       the starting point row
   * @param sCol       the starting point col
   * @param gRow       the goal row
   * @param gCol       the goal col
   */
  public Maze(int rows, int cols, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol) {
    this(rows, cols, isWrapping, sRow, sCol, gRow, gCol, new Random().nextLong());
  }


  /**
   * Generates a perfect maze with a given seed.
   *
   * @param rows       the rows in the maze
   * @param cols       the cols in the maze
   * @param isWrapping whether or not the maze wraps.
   * @param sRow       the starting point row
   * @param sCol       the starting point col
   * @param gRow       the goal row
   * @param gCol       the goal col
   * @param seed       the seed to use for GENERATION
   */
  public Maze(int rows, int cols, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol, long seed) {
    this(rows, cols, isWrapping ? (rows * cols) + 1
                    : ((cols * (rows - 1)) + (rows * (cols - 1))) - (rows * cols) + 1,
            isWrapping, sRow, sCol, gRow, gCol, seed);
  }

  /**
   * Generates a Maze using a random seed.
   *
   * @param rows           the rows in the maze
   * @param cols           the cols in the maze
   * @param isWrapping     whether or not the maze wraps.
   * @param wallsRemaining the walls remaining when the maze is built
   * @param sRow           the starting point row
   * @param sCol           the starting point col
   * @param gRow           the goal row
   * @param gCol           the goal col
   */
  public Maze(int rows, int cols, int wallsRemaining, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol) {
    this(rows, cols, wallsRemaining, isWrapping, sRow, sCol, gRow, gCol, new Random().nextLong());
  }

  /**
   * Generates a Maze using a given seed.
   *
   * @param rows           the rows in the maze
   * @param cols           the cols in the maze
   * @param isWrapping     whether or not the maze wraps.
   * @param wallsRemaining the walls remaining when the maze is built
   * @param sRow           the starting point row
   * @param sCol           the starting point col
   * @param gRow           the goal row
   * @param gCol           the goal col
   * @param seed           the seed for the maze to build its edges from
   */
  public Maze(int rows, int cols, int wallsRemaining, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol, long seed) {
    this(rows, cols, wallsRemaining, isWrapping, sRow, sCol, gRow, gCol, 0, 0,seed );

  }

  /**
   * Generates a Maze using a given seed.
   *
   * @param rows           the rows in the maze
   * @param cols           the cols in the maze
   * @param isWrapping     whether or not the maze wraps.
   * @param wallsRemaining the walls remaining when the maze is built
   * @param sRow           the starting point row
   * @param sCol           the starting point col
   * @param gRow           the goal row
   * @param gCol           the goal col
   * @param seed           the seed for the maze to build its edges from
   */
  public Maze(int rows, int cols, int wallsRemaining, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol, int percentBats, int percentPits, long seed) {
    if (rows < 1 || cols < 1 || rows + cols == 2) {
      throw new IllegalArgumentException("You must have a maze of more than 1 room");
    }
    if (sRow >= rows || sRow < 0 || sCol >= cols || sCol < 0
            || gRow >= rows || gRow < 0 || gCol >= cols || gCol < 0) {
      throw new IllegalArgumentException("Start and end must be on the maze board");
    }
    if (sRow == gRow && sCol == gCol) {
      throw new IllegalArgumentException("Start and goal cannot be the same");
    }
    if (percentBats < 0 || percentPits < 0 || percentBats > 100 || percentPits > 100) {
      throw new IllegalArgumentException("Bat and Pit percentages must be valid percentage values");
    }
    this.board = new IWritableNode[rows][cols];
    this.edges = new ArrayList<>();
    int totalWalls;
    if (isWrapping) {
      totalWalls = 2 * (cols * rows);
    } else {
      totalWalls = (cols * (rows - 1)) + (rows * (cols - 1));
    }
    int egdesNeeded = totalWalls - wallsRemaining;
    if (egdesNeeded < (cols * rows) - 1) {
      throw new IllegalArgumentException("There are two many walls remaining " +
              "for the maze to be completable");
    }


    this.generateNodes();
    List<IEdge> edgeWorkList = this.generateEdgeList(isWrapping, seed);

    this.assignEdges(edgeWorkList, egdesNeeded);

    this.board[sRow][sCol].setRoomType(RoomType.START);
    this.board[gRow][gCol].setRoomType(RoomType.WUMPUS);

    this.assignRoomTypeToMaze(RoomType.SUPERBAT, percentBats, seed);
    this.assignRoomTypeToMaze(RoomType.PIT, percentPits, seed);

    this.player1 = new Player(sRow, sCol);
    this.board[sRow][sCol].shouldIContainPlayer(true);
    this.isGameOver = false;
    this.victory = false;
    this.seed = seed;


  }

  /**
   * This takes the given edge list and assigns them to the vertex. It looks to fist make sure every
   * node is connected, then it adds more edges in to meet the amount needed as the user specified.
   *
   * @param edgeWorkList the list of all possible edges
   * @param egdesNeeded  the amount of edges this maze needs
   */
  private void assignEdges(List<IEdge> edgeWorkList, int egdesNeeded) {
    List<IEdge> discardedEdges = new ArrayList<>();
    while (this.edges.size() < (this.board.length * this.board[0].length) - 1) {
      IEdge curEdge = edgeWorkList.get(0);
      if (curEdge.areNodesConnected()) {
        discardedEdges.add(edgeWorkList.remove(0));
      } else {
        this.edges.add(edgeWorkList.remove(0));
        egdesNeeded--;
        curEdge.unionNodes();
      }
    }
    discardedEdges.addAll(edgeWorkList);
    while (egdesNeeded > 0) {
      this.edges.add(discardedEdges.remove(0));
      egdesNeeded--;
    }
    for (IEdge edge : this.edges) {
      edge.assignMySelf();
    }

  }

  /**
   * Generates the nodes that will make up the board.
   */
  private void generateNodes() {

    for (int r = 0; r < this.board.length; r++) {
      for (int c = 0; c < this.board[r].length; c++) {
        this.board[r][c] = new WritableNode(r, c);
      }
    }
  }

  /**
   * Generates all the edges that can possibly be used in the maze.
   *
   * @param isWrapping if the maze is wrapping, this allows for more edges.
   * @return all the possible edges in the maze.
   */
  private List<IEdge> generateEdgeList(boolean isWrapping, long seed) {

    Random rand = new Random(seed);
    List<IEdge> edgeWorkList = new ArrayList<>();
    for (int r = 0; r < this.board.length - 1; r++) {
      for (int c = 0; c < this.board[r].length; c++) {
        IEdge edge = new Edge(rand.nextInt(300), this.board[r][c],
                this.board[r + 1][c], Direction.SOUTH);
        edgeWorkList.add(edge);
      }
    }

    for (int r = 0; r < this.board.length; r++) {
      for (int c = 0; c < this.board[r].length - 1; c++) {
        IEdge edge = new Edge(rand.nextInt(30), this.board[r][c],
                this.board[r][c + 1], Direction.EAST);

        edgeWorkList.add(edge);
      }
    }

    // if the maze is wrapping add Edges to the two left and bottom edges of the board back to
    // the starting row
    if (isWrapping) {
      int maxRow = this.board.length - 1;
      int maxCol = this.board[maxRow].length - 1;
      for (int c = 0; c < this.board[maxRow].length; c++) {
        IEdge edge = new Edge(rand.nextInt(30), this.board[maxRow][c],
                this.board[0][c], Direction.SOUTH);
        edgeWorkList.add(edge);
      }
      for (int r = 0; r < this.board.length; r++) {
        IEdge edge = new Edge(rand.nextInt(30), this.board[r][maxCol],
                this.board[r][0], Direction.EAST);
        edgeWorkList.add(edge);
      }
    }


    //sort edges so that they will be selected in random orders to be placed into the maze
    edgeWorkList.sort(Comparator.comparingInt(IEdge::getWeight));

    return edgeWorkList;

  }

  /**
   * Used to assign different {@link RoomType}s to the maze. Will attempt to assign the amount
   * specified by the weight. However it will not overwrite other rooms so it may not be able to
   * make as many as the weight specifies.
   *
   * @param roomType         the type of room
   * @param weightPercentage the amount of rooms this maze should contain in percentage value
   * @param seed             the seed for the random class.
   */
  private void assignRoomTypeToMaze(RoomType roomType, int weightPercentage, long seed) {
    List<IWritableNode> nodes;
    int totalNodeAmount = (this.board.length * this.board[0].length);
    double workingWeight = 1.0 / ((double) weightPercentage / 100);
    int roomsNeeded = totalNodeAmount / (int) workingWeight;
    nodes = this.getWriteableNodes();
    Collections.shuffle(nodes, new Random(seed));

    while (roomsNeeded > 0 && nodes.size() > 0) {
      IWritableNode node = nodes.remove(0);
      if (node.getRoomType() == RoomType.EMPTY) {
        node.setRoomType(roomType);
        roomsNeeded--;
      }
    }
  }


  /**
   * Provides a way to view the maze for debuging purposes, will be removed once views are
   * implemented.
   *
   * @return the string representation of the maze.
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int r = 0; r < this.board.length; r++) {
      for (int c = 0; c < this.board[r].length; c++) {
        builder.append(this.board[r][c].debugPrint()).append(this.board[r][c].debugPrintEEdge());
      }
      builder.append("\n");
      for (int c = 0; c < this.board[r].length; c++) {
        builder.append(this.board[r][c].debugPrintSEdge()).append(" ");
      }
      builder.append("\n");
    }

    return builder.toString();
  }

  @Override
  public List<Direction> possiblePlayerMoves() {
    Position playerPos = this.player1.getPosition();
    IReadableNode node = this.board[playerPos.getRow()][playerPos.getCol()];
    return node.getConnectedDirs();
  }

  @Override
  public void movePlayer(Direction direction) {
    if (this.isGameOver()) {
      throw new IllegalStateException("The game is over");
    }
    if (this.possiblePlayerMoves().contains(direction)) {
      Position playerLoc = player1.getPosition();
      this.board[playerLoc.getRow()][playerLoc.getCol()].shouldIContainPlayer(false);
      this.player1.move(direction);
      playerLoc = handleWrap();
      this.roomEffect(this.board[playerLoc.getRow()][playerLoc.getCol()]);
      this.board[playerLoc.getRow()][playerLoc.getCol()].shouldIContainPlayer(true);

    } else {
      throw new IllegalArgumentException("The player cannot move in that direction");
    }

  }

  /**
   * Handles the player moving over wrapped edges in a maze. Mutates nothing when the player moves
   * normally.
   *
   * @return the players location.
   */
  private Position handleWrap() {
    Position playerLoc = player1.getPosition();

    if (playerLoc.getRow() >= this.board.length) {
      player1.setPosition(0, playerLoc.getCol());
    }
    if (playerLoc.getCol() >= this.board[0].length) {
      player1.setPosition(playerLoc.getRow(), 0);
    }
    if (playerLoc.getRow() < 0) {
      player1.setPosition(this.board.length - 1, playerLoc.getCol());
    }
    if (playerLoc.getCol() < 0) {
      player1.setPosition(playerLoc.getRow(), this.board[0].length - 1);
    }
    return player1.getPosition();

  }

  /**
   * After moving into a room, or ending up in one this triggers the room effect if it hasnet been
   * visited before.
   *
   * @param node the node to trigger its effect.
   */
  private void roomEffect(IWritableNode node) {
    RoomType curRoom = node.getRoomType();
    if (node.beenVisited()) {
      return;
    } else {
      node.visit();
    }

    switch (curRoom) {
      case WUMPUS:
        this.isGameOver = true;
        this.victory = true;
        return;
      case THIEF:
        this.player1.encounterThief();
        return;
      case PIT:
        this.isGameOver = true;
        return;
      case SUPERBAT:
        this.telePlayer();
        return;
      case SUPERBAT_AND_PIT:
        if (!this.telePlayer()) {
          this.isGameOver = true;
        }

      default:
        //nonspecial rooms do nothing;


    }

  }

  private boolean telePlayer() {
      boolean foundLoc = false;
      Random r = new Random(this.seed* this.player1.getPosition().getRow()
              * this.player1.getPosition().getCol());

    if (r.nextBoolean()) {
      return false;
    }
      while (!foundLoc) {
          int rowLoc = r.nextInt(this.board.length) - 1;
          int colLoc = r.nextInt(this.board[0].length) - 1;
          if (!Arrays.asList(RoomType.SUPERBAT, RoomType.SUPERBAT_AND_PIT, RoomType.HALLWAY)
                  .contains(this.board[rowLoc][colLoc].getRoomType())) {
              this.player1.setPosition(rowLoc, colLoc);
              foundLoc = true;
          }
      }
    return true;
  }

  @Override
  public Position getPlayerLocation() {
    return this.player1.getPosition();
  }

  @Override
  public List<List<IReadableNode>> getNodes() {
    List<List<IReadableNode>> nodes = new ArrayList<>();
    for (int r = 0; r < this.board.length; r++) {
      List<IReadableNode> thisRow = new ArrayList<>();
      for (int c = 0; c < this.board[r].length; c++) {
        thisRow.add(this.board[r][c].copy());
      }
      nodes.add(thisRow);
    }
    return nodes;
  }

  /**
   * Used to get a list of all the nodes in a writable form for room assignment purposes.
   *
   * @return a list of all the nodes in writeable form
   */
  private List<IWritableNode> getWriteableNodes() {
    List<IWritableNode> nodes = new ArrayList<>();
    for (IWritableNode[] iWritableNodes : this.board) {
      nodes.addAll(Arrays.asList(iWritableNodes));

    }
    return nodes;
  }

  @Override
  public boolean isGameOver() {
    return this.isGameOver;
  }

  @Override
  public int getPlayerGold() {
    return this.player1.getGold();
  }
}
