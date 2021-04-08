package model;

import java.util.ArrayList;

import java.util.List;

/**
 * Represents a node that is readable, but not writeable. Nodes have positions, know whether the
 * player is there, have roomtypes, and know the directions they are connected in.
 */
class ReadableNode implements IReadableNode {
  protected final Position position;
  protected boolean hasPlayer;
  protected RoomType roomType;
  protected final List<Direction> connectedDirections;
  protected boolean visited;

  /**
   * Creates a readable node at the given position, with no connections and as an empty room.
   *
   * @param row the row location
   * @param col the col location
   */
  ReadableNode(int row, int col) {
    this(row, col, new ArrayList<>(), false, RoomType.EMPTY, false);
  }

  /**
   * Creates a readable node at the given position, with connections, whether or not he player is
   * there, and a given roomtype.
   *
   * @param row                 the row location
   * @param col                 the col location
   * @param connectedDirections the directions this is connected in.
   * @param hasPlayer           whether or not he node has the player.
   * @param roomType            the type of room this node is.
   */
  protected ReadableNode(int row, int col, List<Direction> connectedDirections,
                         boolean hasPlayer, RoomType roomType, boolean visited) {
    this.position = new Position(row, col);
    this.hasPlayer = hasPlayer;
    this.roomType = roomType;
    this.connectedDirections = connectedDirections;
    this.visited = visited;
  }

  @Override
  public String debugPrintEEdge() {
    if (this.connectedDirections.contains(Direction.EAST)) {
      return "-";
    } else {
      return " ";
    }
  }

  @Override
  public String debugPrintSEdge() {
    if (this.connectedDirections.contains(Direction.SOUTH)) {
      return "|";
    } else {
      return " ";
    }
  }

  @Override
  public List<Direction> getConnectedDirs() {

    return List.copyOf(this.connectedDirections);
  }

  @Override
  public Position getPosition() {
    return this.position.getPosition();
  }

  @Override
  public IReadableNode copy() {
    return new ReadableNode(this.position.getRow(), this.position.getCol(),
            this.getConnectedDirs(), this.hasPlayer, this.roomType, this.visited);
  }

  @Override
  public RoomType getRoomType() {
    return this.roomType;
  }

  @Override
  public boolean beenVisited() {
    return this.visited;
  }

  @Override
  public String debugPrint() {
    if (this.hasPlayer) {
      return "P";
    }
    switch (this.roomType) {
      case GOAL:
        return "G";
      case START:
        return "S";
      case GOLD:
        return "M";
      case THIEF:
        return "T";
      default:
        return "0";
    }
  }
}