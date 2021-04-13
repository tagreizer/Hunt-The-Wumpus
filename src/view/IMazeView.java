package view;

import java.util.List;

import controller.EventController;
import model.Direction;
import model.IReadableNode;
import model.PlayerEffect;
import model.Position;

public interface IMazeView {

  void animate();

  void animateGameOver();

  void setEventController(EventController listener);

  void setNodes(List<List<IReadableNode>> nodes);

  void setPlayerPos(Position pos);

  void setPossibleMoves(List<Direction> directions);

  void setPlayerEffects(List<PlayerEffect> effects);

  void displayError(String error);

}
