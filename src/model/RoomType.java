package model;

/**
 * Represents a type of room that nodes can be.
 */
public enum RoomType {
  WUMPUS, START, EMPTY, HALLWAY, SUPERBAT, PIT, SUPERBAT_AND_PIT;

  /**
   * Returns the countertype of the room. This is for roomtypes that can combine.
   *
   * @return the counter part of the room type.
   */
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
