package controller;


import model.Direction;
import model.IMaze;
import view.IMazeView;

/**
 * Represents a controller and a listener for a maze/wumpus game.
 */
public class MazeController implements IMazeController, EventController {
  private final IMaze model;
  private final IMazeView view;
  private boolean gameIsRunning;
  private boolean modelChanged;

  /**
   * Creates the controller/listener for the given view and model.
   *
   * @param model the model to use for the game.
   * @param view  the view to display the game.
   */
  public MazeController(IMaze model, IMazeView view) {
    this.model = model;
    this.view = view;
    this.gameIsRunning = true;
    this.modelChanged = true;
    this.view.setEventController(this);

  }

  @Override
  public void runGame() {

    while (gameIsRunning) {
      if (modelChanged) {
        this.view.setNodes(this.model.getNodes());
        this.view.setPlayerPos(this.model.getPlayerLocation());
        this.view.setPossibleMoves(this.model.possiblePlayerMoves());
        this.view.setPlayerEffects(this.model.getRecentEffects());

        this.modelChanged = false;

        if (this.model.isGameOver()) {
          break;
        }
      }

        this.view.animate();




    }

    this.view.animateGameOver();


  }


  @Override
  public void movePlayer(Direction direction) {
    try {
      this.model.movePlayer(direction);
      this.modelChanged = true;
    } catch (IllegalArgumentException e) {
      this.view.displayError("Illegal move, please try again in a valid direction.");

    } catch (IllegalStateException e) {
      this.view.displayError("You should not be able to move the game is over.");
    }

  }

  @Override
  public void shootArrow(Direction dir, int distance) {
    try {
      this.model.fireArrow(dir, distance);
      this.modelChanged = true;
    } catch (IllegalArgumentException e) {
      this.view.displayError(e.getMessage());

    }
  }


}
