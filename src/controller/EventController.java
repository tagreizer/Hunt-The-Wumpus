package controller;

import model.Direction;

public interface EventController {

  void movePlayer(Direction direction);

  void shootArrow(Direction dir, int distance);
}
