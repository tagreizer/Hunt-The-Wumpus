package controller;


import model.Direction;
import model.IMaze;
import model.MazeBuilder;
import view.IMazeView;
import view.SwingMazeCreator;
import view.SwingMazeView;

/**
 * Represents a controller and a listener for a maze/wumpus game.
 */
public class MazeController implements IMazeController, EventController {
  private IMaze model;
  private IMazeView view;
  private boolean gameIsRunning;
  private boolean modelChanged;
  private boolean restarting;

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
    this.restarting = false;


  }

  @Override
  public void runGame() {


    while (gameIsRunning) {


      if (modelChanged) {
        if (restarting) {
          this.view.close();
          SwingMazeCreator newCreator = new SwingMazeCreator(new MazeBuilder());
          this.model = newCreator.create();
          this.view = new SwingMazeView();

          view.setEventController(this);
          restarting = false;

        }
        this.updateView();

        this.modelChanged = false;

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

  private void updateView() {
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
  public void shootArrow(Direction dir, int distance) {
    try {
      this.model.fireArrow(dir, distance);
      this.modelChanged = true;
    } catch (IllegalArgumentException | IllegalStateException e) {
      this.view.displayError(e.getMessage());

    }
  }

  @Override
  public void sendError(String error) {

  }

  @Override
  public void restartGame() {
    IMaze oldModel = this.model;
    this.model = this.model.restart();
    this.modelChanged = true;
    if (oldModel.isGameOver()) {
      //this.updateView();
      System.out.println("UGH");
      //this.runGame();
    }


  }

  @Override
  public void newGame() {
    this.gameIsRunning = false;
    this.restarting = true;

   // SwingMazeCreator newCreator = new SwingMazeCreator(new MazeBuilder());

//    this.model = null;
//    this.view = null;

    //this.model = newCreator.create();


    this.modelChanged = true;

  }


}
