package controller;

import model.Direction;
import model.IMaze;
import model.MazeBuilder;
import model.Position;
import view.IMazeView;
import view.SwingMazeCreator;
import view.SwingMazeView;

/**
 * The controller that should be used with the swing version of the view.
 */
public class SwingMazeController extends TextMazeController {
  /**
   * Creates the controller/listener for the given view and model.
   *
   * @param view the view to display the game.
   */
  public SwingMazeController(IMazeView view) {
    super(null, view);
  }


  @Override
  public void runGame() {
    SwingMazeCreator creator = new SwingMazeCreator(new MazeBuilder());
    creator.setEventController(this);
    creator.create();
  }

  @Override
  protected void updateView() {
    super.updateView();
    this.view.animate();
  }

  @Override
  public void movePlayer(Direction direction) {
    super.movePlayer(direction);
    this.updateView();
  }

  @Override
  public void movePlayer(Position position) {
    super.movePlayer(position);
    this.updateView();
  }

  @Override
  public void shootArrow(Direction dir, int distance) {
    super.shootArrow(dir, distance);
    this.updateView();
  }

  @Override
  public void restartGame() {
    super.restartGame();
    this.updateView();
  }

  @Override
  public void newGame() {
    this.view.close();
    SwingMazeCreator creator = new SwingMazeCreator(new MazeBuilder());
    creator.setEventController(this);
    creator.create();
    //Creates a new view to allow it to re adjust all the frame sizing
    this.view = new SwingMazeView();
    this.view.setEventController(this);

  }

  @Override
  public void useModel(IMaze model) {
    super.useModel(model);
    this.updateView();
  }
}
