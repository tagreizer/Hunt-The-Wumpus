package model;

/**
 * Represents a player in the maze game. Not accessable outside the model package. Users should not
 * be able to obtain, or modify the player, as only the maze should be doing this. A player
 * traverses the maze and can collect and use gold. Players have no notion of the rooms they are in,
 * only the maze knows, the player just knows its location and gold stats.
 */
class Player {
  private Position position;
  private int gold;

  /**
   * Creates a player at the specified location.
   *
   * @param row the row the player starts at.
   * @param col the col the player starts at.
   */
  Player(int row, int col) {
    this.position = new Position(row, col);
    this.gold = 0;
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
   * Represents encountering the Thief, this removes 10% of the players gold.
   */
  void encounterThief() {
    this.gold = this.gold - (this.gold / 10);
  }

  /**
   * Represents picking up gold.
   *
   * @param gold the amount of gold picked up
   */
  void pickUpGold(int gold) {
    this.gold += gold;
  }

  /**
   * Returns the players gold amount.
   *
   * @return the players gold amount.
   */
  int getGold() {
    return this.gold;
  }
}
