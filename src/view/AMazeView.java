package view;

import java.util.ArrayList;
import java.util.List;

import controller.EventController;
import model.Direction;
import model.IReadableNode;
import model.PlayerEffect;
import model.Position;

public abstract class AMazeView implements IMazeView{
  protected EventController listener;
  protected List<List<IReadableNode>> nodes;
  protected Position playerPos;
  protected List<Direction> possibleMoves;
  protected List<PlayerEffect> effects;

  public AMazeView() {
    this.listener = null;
    this.nodes = new ArrayList<>();
    this.playerPos = null;
    this.possibleMoves = new ArrayList<>();
    this.effects = new ArrayList<>();
  }
  @Override
  public void setEventController(EventController listener) {
    this.listener = listener;
  }

  @Override
  public void setNodes(List<List<IReadableNode>> nodes) {
    this.nodes = nodes;

  }

  @Override
  public void setPlayerPos(Position pos) {
    this.playerPos = pos;

  }

  @Override
  public void setPossibleMoves(List<Direction> directions) {
    this.possibleMoves = directions;

  }

  @Override
  public void setPlayerEffects(List<PlayerEffect> effects) {
    this.effects = effects;
  }

}
