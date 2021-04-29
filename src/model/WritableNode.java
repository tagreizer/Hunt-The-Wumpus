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
  public void shouldIContainPlayer(boolean contains, int playerNum) {
    RoomAttribute hasPlayer = playerNum == 1 ?
            RoomAttribute.HAS_PLAYER1 : RoomAttribute.HAS_PLAYER2;
    if (contains && !this.attributes.contains(hasPlayer)) {
      this.attributes.add(hasPlayer);
    }
    if (!contains) {
      this.attributes.remove(hasPlayer);
    }
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
  public void addAttribute(RoomAttribute attribute) {
    if (!this.attributes.contains(attribute)) {
      this.attributes.add(attribute);
    }

  }


  @Override
  public void setLeader(IWritableNode node) {
    if (node == null) {
      throw new IllegalArgumentException("no null inputs");
    }
    this.leader = node.findLeader();

  }


}
