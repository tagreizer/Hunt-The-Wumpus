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
  private final Player[] players;
  private int turn;
  private final long seed;
  private final int[] restartParams;


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
    this(rows, cols, wallsRemaining, isWrapping, sRow, sCol, gRow, gCol, 0, 0, seed, 2);

  }

  /**
   * Generates a Maze using a given seed.
   *
   * @param rows       the rows in the maze
   * @param cols       the cols in the maze
   * @param isWrapping whether or not the maze wraps.
   * @param sRow       the starting point row
   * @param sCol       the starting point col
   * @param gRow       the goal row
   * @param gCol       the goal col
   * @param seed       the seed for the maze to build its edges from
   * @param arrowCount the amount of arrows the player gets
   */
  public Maze(int rows, int cols, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol,
              int percentBats, int percentPits, long seed, int arrowCount) {
    this(rows, cols, isWrapping ? (rows * cols) + 1
                    : ((cols * (rows - 1)) + (rows * (cols - 1))) - (rows * cols) + 1,
            isWrapping, sRow, sCol, gRow, gCol, percentBats, percentPits, seed, arrowCount);

  }

  /**
   * Generates a Maze using a given seed.
   *
   * @param rows        the rows in the maze
   * @param cols        the cols in the maze
   * @param isWrapping  whether or not the maze wraps.
   * @param sRow        the starting point row
   * @param sCol        the starting point col
   * @param gRow        the goal row
   * @param gCol        the goal col
   * @param seed        the seed for the maze to build its edges from
   * @param arrowCount  the amount of arrows the player gets
   * @param playerCount the amount of players
   * @param percentBats the percentage of nodes with bats
   * @param percentPits the percentage of nodes with pits
   */
  public Maze(int rows, int cols, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol,
              int percentBats, int percentPits, long seed, int arrowCount, int playerCount) {
    this(rows, cols, isWrapping ? (rows * cols) + 1
                    : ((cols * (rows - 1)) + (rows * (cols - 1))) - (rows * cols) + 1,
            isWrapping, sRow, sCol, gRow, gCol, percentBats, percentPits, seed, arrowCount,
            playerCount);

  }

  /**
   * Generates a Maze using a given seed.
   *
   * @param rows           the rows in the maze
   * @param cols           the cols in the maze
   * @param wallsRemaining the walls remaining when the maze is built
   * @param isWrapping     whether or not the maze wraps.
   * @param sRow           the starting point row
   * @param sCol           the starting point col
   * @param gRow           the goal row
   * @param gCol           the goal col
   * @param seed           the seed for the maze to build its edges from
   * @param arrowCount     the number of arrows the player gets
   * @param percentBats    the percentage of nodes with bats
   * @param percentPits    the percentage of nodes with pits
   */
  public Maze(int rows, int cols, int wallsRemaining, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol,
              int percentBats, int percentPits, long seed, int arrowCount) {
    this(rows, cols, wallsRemaining,
            isWrapping, sRow, sCol, gRow, gCol, percentBats, percentPits, seed, arrowCount, 1);

  }

  /**
   * Generates a Maze using a given seed.
   *
   * @param rows           the rows in the maze
   * @param cols           the cols in the maze
   * @param wallsRemaining the walls remaining when the maze is built
   * @param isWrapping     whether or not the maze wraps.
   * @param sRow           the starting point row
   * @param sCol           the starting point col
   * @param gRow           the goal row
   * @param gCol           the goal col
   * @param seed           the seed for the maze to build its edges from
   * @param arrowCount     the number of arrows the player gets
   * @param players        the amount of players
   * @param percentBats    the percentage of nodes with bats
   * @param percentPits    the percentage of nodes with pits
   */
  public Maze(int rows, int cols, int wallsRemaining, boolean isWrapping,
              int sRow, int sCol, int gRow, int gCol,
              int percentBats, int percentPits, long seed, int arrowCount, int players) {
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
    if (arrowCount < 1) {
      throw new IllegalArgumentException("A player must have a positive amount of arrows");
    }
    if (players <= 0 || players > 2) {
      throw new IllegalArgumentException("Currently only supports 1 or 2 players");
    }
    this.restartParams =
            new int[]{rows, cols, wallsRemaining, isWrapping ? 1 : 0, sRow, sCol,
              gRow, gCol, percentBats, percentPits, arrowCount, players};
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

    //Generates the graph of nodes building pieces
    this.generateNodes();
    List<IEdge> edgeWorkList = this.generateEdgeList(isWrapping, seed);
    //Assigns edges to connect the graph
    this.assignEdges(edgeWorkList, egdesNeeded);

    //Sets start and End
    this.board[sRow][sCol].setRoomType(RoomType.START);
    this.board[sRow][sCol].visit();
    this.board[gRow][gCol].setRoomType(RoomType.WUMPUS);

    //Converts rooms with two exits to hallways
    this.convertToHallways();

    //adds special room types
    this.assignRoomTypeToMaze(RoomType.SUPERBAT, percentBats, seed);
    this.assignRoomTypeToMaze(RoomType.PIT, percentPits, seed);

    //gives warning to special's neighbors
    this.applyAttributesToNeighbors();

    this.players = new Player[players];

    //rest of maze data

    Player player1 = new Player(sRow, sCol, arrowCount, 1);
    Player player2 = new Player(sRow, sCol, arrowCount, 2);
    this.players[0] = player1;
    if (players == 2) {

      this.players[1] = player2;
      this.board[sRow][sCol].shouldIContainPlayer(true, 2);
    }

    this.board[sRow][sCol].shouldIContainPlayer(true, 1);
    this.seed = seed;
    this.turn = 1;


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
        IEdge edge = new Edge(rand.nextInt(300), this.board[r][c],
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
        IEdge edge = new Edge(rand.nextInt(300), this.board[maxRow][c],
                this.board[0][c], Direction.SOUTH);
        edgeWorkList.add(edge);
      }
      for (int r = 0; r < this.board.length; r++) {
        IEdge edge = new Edge(rand.nextInt(300), this.board[r][maxCol],
                this.board[r][0], Direction.EAST);
        edgeWorkList.add(edge);
      }
    }

    //sort edges so that they will be selected in random orders to be placed into the maze
    edgeWorkList.sort(Comparator.comparingInt(IEdge::getWeight));

    return edgeWorkList;

  }

  /**
   * Takes nodes that have two connections and turns them into hallways. One connected rooms are NOT
   * turned to hallways, this allows dead ends to be rooms.
   */
  private void convertToHallways() {
    for (int r = 0; r < this.board.length; r++) {
      for (int c = 0; c < this.board[r].length; c++) {
        if (this.board[r][c].getRoomType() == RoomType.EMPTY && this.board[r][c]
                .getConnectedDirs().size() == 2) {
          this.board[r][c].setRoomType(RoomType.HALLWAY);

        }
      }
    }
  }

  /**
   * Looks for special rooms, then once found it finds connect rooms, hallways do not count, and
   * gives them the hint attributes that correspond to the special rooms, like smelling the wumpus.
   */
  private void applyAttributesToNeighbors() {
    for (int r = 0; r < this.board.length; r++) {
      for (int c = 0; c < this.board[r].length; c++) {
        switch (this.board[r][c].getRoomType()) {
          case WUMPUS:
            for (IWritableNode node : this.getConnectedRooms(this.board[r][c])) {
              node.addAttribute(RoomAttribute.NEXT_TO_WUMPUS);
            }
            break;
          case PIT:
          case SUPERBAT_AND_PIT:
            for (IWritableNode node : this.getConnectedRooms(this.board[r][c])) {
              node.addAttribute(RoomAttribute.NEXT_TO_PIT);
            }
            break;
          default:
            //neighbors of other room types get nothing.
        }


      }
    }
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
    Collections.shuffle(nodes, new Random(seed * roomType.ordinal()));

    while (roomsNeeded > 0 && nodes.size() > 0) {
      IWritableNode node = nodes.remove(0);
      if (node.getRoomType() == RoomType.EMPTY) {
        node.setRoomType(roomType);
        roomsNeeded--;
      } else if (node.getRoomType() == roomType.getCounterPart()) {
        node.setRoomType(RoomType.SUPERBAT_AND_PIT);
        roomsNeeded--;
      }
    }
  }

  /**
   * Takes a given node and returns a list of rooms that are connected, hallways do not count.
   *
   * @param node the node to find connections from
   * @return the connected rooms
   */
  private List<IWritableNode> getConnectedRooms(IWritableNode node) {
    List<IWritableNode> connectedRooms = new ArrayList<>();
    for (Direction dir : node.getConnectedDirs()) {
      connectedRooms.add(this.getConnectedNodeHelp(
              this.handleWrap(this.updatePosFromDirection(node.getPosition(), dir)), dir));

    }

    return connectedRooms;
  }

  /**
   * Helper to the get connected rooms, this takes in a position and a direction of the connection,
   * and if its not a hallway returns it as the connected node. If it is a hallway it goes through
   * the hallway and tries the next node.
   *
   * @param position  position of the node to check.
   * @param direction the direction of the connection from the previous node.
   * @return the connected room that isnt a hallway
   */
  private IWritableNode getConnectedNodeHelp(Position position, Direction direction) {
    IWritableNode node = this.board[position.getRow()][position.getCol()];
    if (node.getRoomType() != RoomType.HALLWAY) {
      return node;
    } else {
      List<Direction> directions = new ArrayList<>(node.getConnectedDirs());
      directions.remove(direction.opposite());

      return getConnectedNodeHelp(this
                      .handleWrap(this.updatePosFromDirection(position, directions.get(0))),
              directions.get(0));
    }
  }


  /**
   * Provides a way to view the maze for debuging purposes.
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

    Position playerPos = this.choosePlayer().getPosition();
    IReadableNode node = this.board[playerPos.getRow()][playerPos.getCol()];
    return node.getConnectedDirs();
  }

  /**
   * Looks at the turn number and returns the player whos turn it is.
   *
   * @return the player whos turn it is.
   */
  private Player choosePlayer() {
    return this.players[this.turn - 1];
  }

  /**
   * Advances the turn to the next player.
   */
  private void advanceTurn() {
    if (this.isGameOver()) {
      return;
    }

    if (this.turn < this.players.length) {
      this.turn++;
    } else {
      this.turn = 1;
    }
    if (this.isGameOver(this.choosePlayer())) {
      this.advanceTurn();
    }
  }


  @Override
  public void movePlayer(Direction direction) {

    this.movePlayer(direction, this.choosePlayer());
    this.advanceTurn();

  }


  /**
   * Moves a specific player based off of the given direction.
   *
   * @param direction the direction to move.
   * @param player    the specific player
   */
  private void movePlayer(Direction direction, Player player) {

    if (this.isGameOver(player)) {
      throw new IllegalStateException("The game is over");
    }

    if (this.possiblePlayerMoves().contains(direction)) {
      player.clearEffects();
      Position playerLoc = player.getPosition();
      this.board[playerLoc.getRow()][playerLoc.getCol()]
              .shouldIContainPlayer(false, player.getPlayerNum());
      player.move(direction);

      playerLoc = handleWrap(player.getPosition());
      player.setPosition(playerLoc.getRow(), playerLoc.getCol());
      this.roomEffect(this.board[playerLoc.getRow()][playerLoc.getCol()], direction);
      playerLoc = player.getPosition();
      this.board[playerLoc.getRow()][playerLoc.getCol()]
              .shouldIContainPlayer(true, player.getPlayerNum());


    } else {
      throw new IllegalArgumentException("The player cannot move in that direction");
    }
  }


  @Override
  public void movePlayer(Position position) {
    Direction direction = this.directionTo(this.choosePlayer(), position);
    if (direction == null) {
      throw new IllegalArgumentException("That player cannot move to that position.");
    }

    this.movePlayer(direction);

  }

  /**
   * Returns the direction to a position for the player. If there is no way to get there this
   * returns null.
   *
   * @param player   the player to go there.
   * @param position the position they are trying to go to.
   * @return the direction to a postion.
   */
  private Direction directionTo(Player player, Position position) {
    Direction dir = null;
    for (Direction direction : this.possiblePlayerMoves()) {
      Position connectedPos = this.handleWrap(
              this.updatePosFromDirection(player.getPosition(), direction));
      if (this.getConnectedNodeHelp(connectedPos, direction).getPosition().equals(position)) {
        dir = direction;
      }
    }

    return dir;
  }

  /**
   * Handles a position moving over wrapped edges in a maze. Returns the new position.
   *
   * @return the new position after wrapping
   */
  private Position handleWrap(Position position) {

    if (position.getRow() >= this.board.length) {
      return new Position(0, position.getCol());
    }
    if (position.getCol() >= this.board[0].length) {
      return new Position(position.getRow(), 0);
    }
    if (position.getRow() < 0) {
      return new Position(this.board.length - 1, position.getCol());
    }
    if (position.getCol() < 0) {
      return new Position(position.getRow(), this.board[0].length - 1);
    }
    return position;

  }

  /**
   * Takes in a position and a direction and returns the new position if you moved based off of that
   * direction.
   *
   * @param position  the position you start at.
   * @param direction the direction you move to.
   * @return the new position you are in.
   */
  private Position updatePosFromDirection(Position position, Direction direction) {
    switch (direction) {
      case EAST:
        return new Position(position.getRow(), position.getCol() + 1);

      case WEST:
        return new Position(position.getRow(), position.getCol() - 1);

      case SOUTH:
        return new Position(position.getRow() + 1, position.getCol());

      case NORTH:
        return new Position(position.getRow() - 1, position.getCol());

      default:
        throw new IllegalArgumentException("No null inputs");
    }
  }


  /**
   * After moving into a room, or ending up in one this triggers the room effect if it has not been
   * visited before.
   *
   * @param node the node to trigger its effect.
   */
  private void roomEffect(IWritableNode node, Direction dir) {
    RoomType curRoom = node.getRoomType();
    if (node.beenVisited() && node.getRoomType() != RoomType.HALLWAY
            && node.getRoomType() != RoomType.WUMPUS) {
      return;
    } else {
      node.visit();
    }

    switch (curRoom) {
      case HALLWAY:
        List<Direction> dirs = new ArrayList<>(node.getConnectedDirs());
        dirs.remove(dir.opposite());
        this.movePlayer(dirs.get(0), this.choosePlayer());
        return;
      case WUMPUS:
        this.choosePlayer().addEffect(PlayerEffect.RAN_INTO_WUMPUS);
        return;
      case PIT:
        this.choosePlayer().addEffect(PlayerEffect.FELL_INTO_PIT);
        return;
      case SUPERBAT:
        if (this.telePlayer()) {
          this.choosePlayer().addEffect(PlayerEffect.GRABBED_BY_BAT);
        } else {
          this.choosePlayer().addEffect(PlayerEffect.AVOIDED_BAT);
        }
        return;
      case SUPERBAT_AND_PIT:
        if (!this.telePlayer()) {
          this.choosePlayer().addEffect(PlayerEffect.FELL_INTO_PIT);
        } else {
          this.choosePlayer().addEffect(PlayerEffect.GRABBED_BY_BAT);
        }
        break;

      default:
        //nonspecial rooms do nothing;

    }

  }

  /**
   * Teleports the player to a random location with a 50% chance. A player cannot be teleported to a
   * room with a bat or a hallway.
   *
   * @return if the player was teleported.
   */
  private boolean telePlayer() {
    boolean foundLoc = false;
    Random r = new Random(this.seed * this.choosePlayer().getPosition().getRow()
            * this.choosePlayer().getPosition().getCol());
    int tele = r.nextInt(2);

    if (tele == 0) {
      return false;

    }
    while (!foundLoc) {
      int rowLoc = r.nextInt(this.board.length);
      int colLoc = r.nextInt(this.board[0].length);
      if (!Arrays.asList(RoomType.SUPERBAT, RoomType.SUPERBAT_AND_PIT, RoomType.HALLWAY)
              .contains(this.board[rowLoc][colLoc].getRoomType())) {
        this.choosePlayer().setPosition(rowLoc, colLoc);
        this.roomEffect(this.board[rowLoc][colLoc], null);
        foundLoc = true;
      }
    }
    return true;
  }

  @Override
  public Position getPlayerLocation() {
    return this.choosePlayer().getPosition();
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
    boolean gameOver = true;
    for (Player player : this.players) {
      //if someone has killed the wumpus
      if (player.getRecentEffects().contains(PlayerEffect.SHOT_WUMPUS)) {
        return true;
      }
      //if everyone is dead
      gameOver = gameOver && this.isGameOver(player);
    }
    return gameOver;
  }

  /**
   * Checks if the game is over for the given player.
   *
   * @param player the player to check
   * @return if the game is over for the current player.
   */
  private boolean isGameOver(Player player) {
    List<PlayerEffect> effects = player.getRecentEffects();
    return effects.contains(PlayerEffect.NO_ARROWS) || effects.contains(PlayerEffect.SHOT_WUMPUS)
            || effects.contains(PlayerEffect.RAN_INTO_WUMPUS)
            || effects.contains(PlayerEffect.FELL_INTO_PIT);
  }

  @Override
  public List<List<PlayerEffect>> getRecentEffects() {
    List<List<PlayerEffect>> effectsList = new ArrayList<>();
    for (Player player : this.players) {
      effectsList.add(player.getRecentEffects());
    }
    return effectsList;
  }

  @Override
  public void fireArrow(Direction dir, int distance) {
    this.fireArrow(dir, distance, choosePlayer());
    this.advanceTurn();

  }

  /**
   * Fires an arrow in a specific direction, at a specific distance, for a specific player.
   *
   * @param dir      the direction
   * @param distance the distance
   * @param player   the player
   */
  private void fireArrow(Direction dir, int distance, Player player) {
    if (this.isGameOver()) {
      throw new IllegalStateException("The game is over you cannot shoot");
    }
    if (player.getRecentEffects().contains(PlayerEffect.NO_ARROWS)) {
      throw new IllegalStateException("Player is out of arrows");
    }
    if (distance < 1) {
      throw new IllegalArgumentException("You must shoot an arrow greater than 1 units far");
    }
    if (dir == null) {
      throw new IllegalArgumentException("No null directions");
    }
    player.clearEffects();
    player.removeArrow();
    if (fireArrowHelper(dir, distance, player.getPosition())) {
      player.addEffect(PlayerEffect.SHOT_WUMPUS);
    } else {
      player.addEffect(PlayerEffect.MISSED_WUMPUS);
      if (player.getArrowAmount() == 0) {
        player.addEffect(PlayerEffect.NO_ARROWS);
      }

    }
  }

  @Override
  public int getArrowAmount() {
    return this.choosePlayer().getArrowAmount();

  }

  @Override
  public IMaze restart() {
    return new Maze(restartParams[0], restartParams[1], restartParams[2],
            restartParams[3] == 1, restartParams[4], restartParams[5], restartParams[6],
            restartParams[7], restartParams[8], restartParams[9],
            this.seed, restartParams[10], restartParams[11]);
  }

  @Override
  public int playerNumTurn() {
    return this.turn;
  }

  /**
   * Helps the fire arrow method. Checks to see if the arrow hit the wumpus. This function walks
   * down rooms/hallways searching to see if the wumpus is hit. If its a hallway it will curve and
   * not count for distance, if its a room it contiunes straight but looses distance.
   *
   * @param dir      the direction the arrow is traveling
   * @param distance the distance for the arrow to travel.
   * @param newStart the position the arrow should start at.
   * @return if the arrow hit the wumpus.
   */
  private boolean fireArrowHelper(Direction dir, int distance, Position newStart) {

    IWritableNode startNode = this.board[newStart.getRow()][newStart.getCol()];
    Position endPos;
    if (distance == 0) {
      return startNode.getRoomType() == RoomType.WUMPUS;
    }
    if (!startNode.getConnectedDirs().contains(dir)) {
      return false;
    }
    endPos = this.handleWrap(this.updatePosFromDirection(newStart, dir));

    if (this.board[endPos.getRow()][endPos.getCol()].getRoomType() == RoomType.HALLWAY) {
      List<Direction> dirs = new ArrayList<>(this.board[endPos.getRow()][endPos.getCol()]
              .getConnectedDirs());
      dirs.remove(dir.opposite());
      dir = dirs.get(0);

      return fireArrowHelper(dir, distance, endPos);
    }

    return fireArrowHelper(dir, distance - 1, endPos);
  }


}
