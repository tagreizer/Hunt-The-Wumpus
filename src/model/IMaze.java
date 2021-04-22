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
   * Returns whether or not the game is over. The game ends when the player either runs into the
   * wumpus, kills the wumpus, falls into a pit, or runs out of arrows.
   *
   * @return whether or not the game is over
   */
  boolean isGameOver();

  /**
   * Get the recent effects experienced by the player.
   *
   * @return the recent player effects
   */
  List<List<PlayerEffect>> getRecentEffects();

  /**
   * Shoots an arrow in a specified direction. The arrow can curve through hallways, but travels
   * straight through rooms. The distance must be exact to kill the wumpus, too short or too long is
   * considered a miss.
   *
   * @param dir      the direction to shoot the arrow
   * @param distance the distance to shoot
   */
  void fireArrow(Direction dir, int distance);

  int getArrowAmount();

  IMaze restart();

  int playerNumTurn();


}
