package controller;


import java.io.InputStreamReader;

import model.Direction;
import model.IMaze;
import model.MazeBuilder;
import model.Position;
import view.IMazeView;
import view.SwingMazeCreator;
import view.SwingMazeView;
import view.TextMazeView;
import view.ViewStyle;

/**
 * Represents a controller and a listener for a maze/wumpus game.
 */
public final class MazeController implements IMazeController, EventController {

  private IMaze model;
  private IMazeView view;
  private boolean modelChanged;
  private boolean creatingNewGame;
  private ViewStyle remakeStyle;

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
    //By default if the view is remade it will be with a swing option.
    this.remakeStyle = ViewStyle.SWING;


  }

  @Override
  public void runGame() {

    while (true) {
      if (creatingNewGame) {

        if (this.model != null) {
          if (this.remakeStyle == ViewStyle.SWING) {
            this.view = new SwingMazeView();
          }
          if (this.remakeStyle == ViewStyle.TEXT) {
            this.view = new TextMazeView(System.out, new InputStreamReader(System.in));
          }

          this.view.setEventController(this);
          this.creatingNewGame = false;
        } else {
          //This line is required, but functionally useless.
          //For some reason the thread or whatever keeps this main loop running in java
          //needs a line of code here (that needs to include something besides primitives) or
          //it just shuts down when the other thread is created. I chose creating an empty string
          //As i figured it wouldn't be taxing on the resources (I dont really know though).
          //If you know why this bug is happening could you leave a comment and explain.
          //I dont have time to troubleshoot it more, and assume maybe this function is
          // being garbage collected or something, but I have no idea otherwise.
          new String();
        }


      } else {
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

  /**
   * Updates the info in the view from the model.
   */
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
  public void newGame(ViewStyle style) {
    //sets params that say the game will restart and there will be a new one
    this.model = null;
    this.creatingNewGame = true;
    this.remakeStyle = style;

    //shuts down the old window
    this.view.close();

    NewGameThreadClass creator = new NewGameThreadClass(this);
    Thread t = new Thread(creator);
    t.setPriority(Thread.MAX_PRIORITY);
    t.start();
    this.modelChanged = true;


  }

  /**
   * Assigns a new mode to this controller. (Only accessable to the newGameThreadClass)
   *
   * @param model the model to assign.
   */
  private void assignModel(IMaze model) {
    this.model = model;
  }

  /**
   * A runnable to that creates an object to create a new maze with.
   */
  private class NewGameThreadClass implements Runnable {

    IMaze model;
    MazeController controller;

    /**
     * Creates a newGameThreadClass and assigns the controller to give the model to.
     *
     * @param controller the controller to give the model to.
     */
    private NewGameThreadClass(MazeController controller) {
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
