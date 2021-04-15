package model;

import java.util.ArrayList;

import java.util.List;

/**
 * Represents a node that is readable, but not writeable. Nodes have positions, know whether the
 * player is there, have roomtypes, and know the directions they are connected in.
 */
class ReadableNode implements IReadableNode {
  protected final Position position;
  protected RoomType roomType;
  protected final List<Direction> connectedDirections;
  protected boolean visited;
  protected final List<RoomAttribute> attributes;
  /**
   * Creates a readable node at the given position, with no connections and as an empty room.
   *
   * @param row the row location
   * @param col the col location
   */
  ReadableNode(int row, int col) {
    this(row, col, new ArrayList<>(),  RoomType.EMPTY, false, new ArrayList<>());
  }

  /**
   * Creates a readable node at the given position, with connections, whether or not he player is
   * there, and a given roomtype.
   *
   * @param row                 the row location
   * @param col                 the col location
   * @param connectedDirections the directions this is connected in.

   * @param roomType            the type of room this node is.
   */
  protected ReadableNode(int row, int col, List<Direction> connectedDirections,
                         RoomType roomType, boolean visited, List<RoomAttribute> attributes) {
    this.position = new Position(row, col);
    this.roomType = roomType;
    this.connectedDirections = connectedDirections;
    this.visited = visited;
    this.attributes = attributes;


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
            this.getConnectedDirs(), this.roomType, this.visited, attributes);
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
  public List<RoomAttribute> getRoomAttributes() {
    return List.copyOf(this.attributes);
  }

  @Override
  public String debugPrint() {
    if (this.attributes.contains(RoomAttribute.HAS_PLAYER1)) {
      return "P";
    }
    switch (this.roomType) {
      case WUMPUS:
        return "G";
      case START:
        return "S";
      case PIT:
        return "U";
      case SUPERBAT:
        return "B";
      case SUPERBAT_AND_PIT:
        return "#";
      case HALLWAY:
        return "+";
      default:
        return "0";
    }
  }

  @Override
  public String toString() {
    return "(" + this.position.getRow() + "," + this.position.getCol() + ")";
  }
}
