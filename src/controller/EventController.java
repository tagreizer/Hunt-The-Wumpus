package controller;

import model.Direction;
import model.IMaze;
import model.Position;


/**
 * Represents an object that listens for events. Not called event listener however to avoid
 * confusion with the existing java event listener
 */
public interface EventController {

  /**
   * Tells the event controller that the player should move in a specific direction.
   *
   * @param direction the direction a player should move in.
   */
  void movePlayer(Direction direction);

  /**
   * Tells the event controller to try and move a player to a specific position.
   * @param position the position to move to.
   */
  void movePlayer(Position position);

  /**
   * Tells the event controller that the player should shoot an arrow in a direction with a specific
   * distance.
   *
   * @param dir      the direction
   * @param distance the distance
   */
  void shootArrow(Direction dir, int distance);

  /**
   * Signals that the event controller should restart the game.
   */
  void restartGame();

  /**
   * Signals that the event controller should start a new game.
   *
   */
  void newGame();

  void useModel(IMaze model);


}
