package model;

/**
 * Represents a type of room that nodes can be.
 */
public enum RoomType {
  WUMPUS, START, EMPTY, HALLWAY, SUPERBAT, PIT, SUPERBAT_AND_PIT;

  public RoomType getCounterPart() {
    switch (this) {
      case SUPERBAT:
        return PIT;
      case PIT:
        return SUPERBAT;
      default:
        return this;
    }
  }

}
