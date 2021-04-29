package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the maze game. Not accessable outside the model package. Users should not
 * be able to obtain, or modify the player, as only the maze should be doing this. A player
 * traverses the maze and can collect and use gold. Players have no notion of the rooms they are in,
 * only the maze knows, the player just knows its location and gold stats.
 */
final class Player {
  private Position position;
  private int arrowAmount;
  private final List<PlayerEffect> recentEffects;
  private final int playerNum;


  /**
   * Creates a player at the specified location.
   *
   * @param row        the row the player starts at.
   * @param col        the col the player starts at.
   * @param arrowCount the amount of arrows the player starts with.
   * @param playerNum  the number identifier for the player
   */
  Player(int row, int col, int arrowCount, int playerNum) {
    this.position = new Position(row, col);
    this.arrowAmount = arrowCount;
    this.recentEffects = new ArrayList<>();
    this.playerNum = playerNum;

  }

  /**
   * Gets the current position of the player.
   *
   * @return the position of the player.
   */
  Position getPosition() {
    return this.position.getPosition();
  }

  /**
   * Sets the position of the player. Will be used in a later iteration when the player can be
   * teleported.
   *
   * @param row the row to go to.
   * @param col the col to go to.
   */
  void setPosition(int row, int col) {
    this.position = new Position(row, col);

  }

  /**
   * Moves the players position in the specified direction.
   *
   * @param dir the direction for the player to move.
   */
  void move(Direction dir) {
    switch (dir) {
      case EAST:
        this.position = new Position(this.position.getRow(), this.position.getCol() + 1);
        return;
      case WEST:
        this.position = new Position(this.position.getRow(), this.position.getCol() - 1);
        return;
      case SOUTH:
        this.position = new Position(this.position.getRow() + 1, this.position.getCol());
        return;
      case NORTH:
        this.position = new Position(this.position.getRow() - 1, this.position.getCol());
        return;
      default:
        throw new IllegalArgumentException("No null inputs");
    }

  }

  /**
   * Removes one arrow from the player.
   */
  void removeArrow() {
    this.arrowAmount--;
  }

  /**
   * Returns the amount of arrows the player has left.
   *
   * @return the amount of arrows.
   */
  int getArrowAmount() {
    return this.arrowAmount;
  }

  /**
   * Adds an effect to the player.
   *
   * @param effect the effect to add
   */
  void addEffect(PlayerEffect effect) {
    this.recentEffects.add(effect);
  }

  /**
   * Clears the effects the player currently has.
   */
  void clearEffects() {
    this.recentEffects.clear();
  }

  /**
   * Returns the players number.
   *
   * @return the players number.
   */
  int getPlayerNum() {
    return this.playerNum;
  }

  /**
   * Returns the recent effects on the player.
   *
   * @return the effects.
   */
  List<PlayerEffect> getRecentEffects() {
    return List.copyOf(this.recentEffects);
  }
}

