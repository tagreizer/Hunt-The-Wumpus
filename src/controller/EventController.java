package controller;

import model.Direction;


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
   * Tells the event controller that the player should shoot an arrow in a direction with a specific
   * distance.
   *
   * @param dir the direction
   * @param distance the distance
   */
  void shootArrow(Direction dir, int distance);


}
