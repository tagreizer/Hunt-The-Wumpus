package model;

/**
 * Represents a cardinal direction.
 */
public enum Direction {
  NORTH, SOUTH, EAST, WEST;

  /**
   * Returns the opposite direction associated with the current direction.
   *
   * @return the opposite direction.
   */
  Direction opposite() {
    switch (this) {
      case WEST:
        return EAST;
      case EAST:
        return WEST;
      case SOUTH:
        return NORTH;
      case NORTH:
        return SOUTH;
      default:
        return null;
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case WEST:
        return "West";
      case EAST:
        return "East";
      case SOUTH:
        return "South";
      case NORTH:
        return "North";
      default:
        return null;
    }
  }
}
