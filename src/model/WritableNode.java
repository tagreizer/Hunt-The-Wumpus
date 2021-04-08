package model;


/**
 * Represents a writeable version of a {@link ReadableNode}.
 */
class WritableNode extends ReadableNode implements IWritableNode {
  private IWritableNode leader;

  /**
   * Creates a writeable node at the given location.
   *
   * @param row the row location
   * @param col they col location.
   */
  WritableNode(int row, int col) {
    super(row, col);
    this.leader = this;

  }

  @Override
  public void setConnectedDirection(Direction dir) {
    if (dir == null) {
      throw new IllegalArgumentException("no null inputs");
    }
    if (!this.connectedDirections.contains(dir)) {
      this.connectedDirections.add(dir);
    } else {
      throw new IllegalArgumentException("Already has an edge for this position");
    }

  }

  @Override
  public void shouldIContainPlayer(boolean contains) {
    this.hasPlayer = contains;

  }

  @Override
  public IWritableNode findLeader() {
    if (this.leader == this) {
      return this;
    } else {
      return this.leader.findLeader();
    }
  }

  @Override
  public void setRoomType(RoomType roomType) {
    if (this.roomType == null) {
      throw new IllegalArgumentException("no null inputs");
    }
    this.roomType = roomType;
  }

  @Override
  public void visit() {
    this.visited = true;
  }


  @Override
  public void setLeader(IWritableNode node) {
    if (node == null) {
      throw new IllegalArgumentException("no null inputs");
    }
    this.leader = node.findLeader();

  }


}
