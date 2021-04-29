package controller;


import model.Direction;
import model.IMaze;
import model.Position;
import view.IMazeView;
import view.ViewStyle;

/**
 * Represents a controller and a listener for a maze/wumpus game.
 */
public class TextMazeController implements IMazeController, EventController {

  protected IMaze model;
  protected IMazeView view;
  protected boolean modelChanged;
  protected boolean creatingNewGame;

  /**
   * Creates the controller/listener for the given view and model.
   *
   * @param model the model to use for the game.
   * @param view  the view to display the game.
   */
  public TextMazeController(IMaze model, IMazeView view) {
    this.model = model;
    this.view = view;
    this.modelChanged = true;
    this.view.setEventController(this);
    this.creatingNewGame = false;




  }

  @Override
  public void runGame() {

    while (true) {

        //code that executes if not creating a new game.
        if (modelChanged) {

          this.updateView();

          this.modelChanged = false;

          //included to allow swing views to continue after the game ends,
          // but allow text/other views to terminate if they want
          if (this.model.isGameOver()) {
            this.view.animateGameOver();
            if (this.view.shouldQuit()) {
              break;
            }
          }
        }

        this.view.animate();



    }


  }

  /**
   * Updates the info in the view from the model.
   */
  protected void updateView() {
    this.view.setNodes(this.model.getNodes());
    this.view.setTurn(this.model.playerNumTurn());
    this.view.setPlayerPos(this.model.getPlayerLocation());
    this.view.setPossibleMoves(this.model.possiblePlayerMoves());
    this.view.setPlayerEffects(this.model.getRecentEffects());
    this.view.setArrowAmount(this.model.getArrowAmount());

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
  public void movePlayer(Position position) {
    try {
      this.model.movePlayer(position);
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
    } catch (IllegalArgumentException | IllegalStateException e) {
      this.view.displayError(e.getMessage());

    }
  }


  @Override
  public void restartGame() {
    this.model = this.model.restart();
    this.modelChanged = true;


  }

  @Override
  public void newGame() {
    //not supported by this class


  }

  @Override
  public void useModel(IMaze model) {
    this.model = model;
  }






}
