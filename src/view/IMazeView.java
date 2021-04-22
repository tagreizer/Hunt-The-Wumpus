package view;

import java.util.List;

import controller.EventController;
import model.Direction;
import model.IReadableNode;
import model.PlayerEffect;
import model.Position;

/**
 * Represents a view for the maze/wumpus game.
 */
public interface IMazeView {

  /**
   * Animates the game, how this function will animate is based on the implementation class.
   */
  void animate();

  /**
   * Animates the end of the game. Or the game over animation.
   */
  void animateGameOver();

  /**
   * Sets the listener for this view to call.
   *
   * @param listener the listener for the view
   */
  void setEventController(EventController listener);

  /**
   * Sets the nodes to animate from the model.
   *
   * @param nodes the nodes to animate.
   */
  void setNodes(List<List<IReadableNode>> nodes);

  /**
   * Sets the players position(Can be found in the nodes, but easier to be given directly).
   *
   * @param pos player's position
   */
  void setPlayerPos(Position pos);

  /**
   * Sets the players possible moves(Can be found in the nodes, but easier to be given directly).
   *
   * @param directions the directions the player can move.
   */
  void setPossibleMoves(List<Direction> directions);

  /**
   * Sets the player effects.
   *
   * @param effects the player effects.
   */
  void setPlayerEffects(List<List<PlayerEffect>> effects);

  void setArrowAmount(int arrowAmount);

  void setTurn(int playerNumber);

  /**
   * Displays an error string that is given.
   *
   * @param error the error to be displayed.
   */
  void displayError(String error);

  boolean shouldQuit();

  //Possibly get rid of. Dont know what to do.
  void close();

  void open();

}
