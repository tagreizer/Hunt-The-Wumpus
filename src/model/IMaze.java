package model;

import java.util.List;

/**
 * Represents a maze that can return {@link IReadableNode}s representing its maze. There is a
 * starting and ending point, as well as a * player that can traverse the maze from the start to the
 * end. Mazes can either wrap(connect on the * edges of the map) or not. They can also be a perfect
 * maze(minimum spanning tree) or not.
 */
public interface IMaze {

  /**
   * Returns the possible directions that the player can move in.
   *
   * @return the directions the player can move in.
   */
  List<Direction> possiblePlayerMoves();

  /**
   * Moves the player in the given direction if possible.
   *
   * @param direction the direction for the player to move
   */
  void movePlayer(Direction direction);

  /**
   * Returns a {@link Position} representing the players location.
   *
   * @return the players location as a position.
   */
  Position getPlayerLocation();

  /**
   * Returns a 2D list of {@link IReadableNode}s that represent the maze.
   *
   * @return
   */
  List<List<IReadableNode>> getNodes();

  /**
   * Returns whether or not the game is over. The game ends when the player reaches the goal
   * currently
   *
   * @return whether or not the game is over
   */
  boolean isGameOver();

  /**
   * Returns the players gold amount.
   *
   * @return the players gold amount.
   */
  int getPlayerGold();
}
