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

  /**
   * Sets the amount of remaining Arrows.
   * @param arrowAmount the amount of remaining arrows.
   */
  void setArrowAmount(int arrowAmount);

  /**
   * Sets the player turn.
   * @param playerNumber the player who's turn it is.
   */
  void setTurn(int playerNumber);

  /**
   * Displays an error string that is given.
   *
   * @param error the error to be displayed.
   */
  void displayError(String error);

  /**
   * Signals to a controller if this view type should quit after the end of a game. This allows
   * some views to prompt for a restart while others exit on game completion.
   * @return if the view should quit after the game is finished.
   */
  boolean shouldQuit();

  /**
   * Shuts down a view so that it stops animating.
   */
  void close();


}
