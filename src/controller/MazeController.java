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
  private boolean modelChanged;
  private boolean creatingNewGame;

  /**
   * Creates the controller/listener for the given view and model.
   *
   * @param model the model to use for the game.
   * @param view  the view to display the game.
   */
  public MazeController(IMaze model, IMazeView view) {
    this.model = model;
    this.view = view;
    this.modelChanged = true;
    this.view.setEventController(this);
    this.creatingNewGame = false;


  }

  @Override
  public void runGame() {


    while (true) {
      if (creatingNewGame) {

          if (this.model != null) {
            this.view = new SwingMazeView();
            this.view.setEventController(this);
            this.creatingNewGame = false;
          } else {
            //This line is required, but functionally useless.
            //For some reason the thread or whatever keeps this main loop running in java
            //needs a line of code here (that needs to include something besides primitives) or
            //it just shuts down when the other thread is created. I chose creating an empty string
            //As i figured it wouldn't be taxing on the resources (I dont really know though).
            //If you know why this bug is happening code you leave a comment and explain.
            //I dont have time to troubleshoot it more, and assume maybe this function is being garbage collected
            //or something, but I have no idea otherwise.
            new String();
          }


      } else{
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

    }


  }

  @Override
  public void newGame() {
    //sets params that say the game will restart and there will be a new one
    this.model = null;
    this.creatingNewGame = true;

    //shuts down the old window
    this.view.close();

    newGameThreadClass creator = new newGameThreadClass(this);
    Thread t = new Thread(creator);
    t.setPriority(Thread.MAX_PRIORITY);
    t.start();
    this.modelChanged = true;


  }

  private void assignModel(IMaze model) {
    this.model = model;
  }

  private class newGameThreadClass implements Runnable {
      IMaze model;
      MazeController controller;

    newGameThreadClass(MazeController controller) {
      this.model = null;
      this.controller = controller;
    }
    @Override
    public void run() {
      this.model = new SwingMazeCreator(new MazeBuilder()).create();

      controller.assignModel(this.model);



    }


  }


}
