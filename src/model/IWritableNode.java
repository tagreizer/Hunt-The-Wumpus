package model;

/**
 * Represents a node that is not only readable, but writeable too. Contains methods that can mutate
 * nodes.
 */
public interface IWritableNode extends IReadableNode {

  /**
   * Sets a connected direction or and edge to the node. Is given the direction, but not the edge
   * itself as a result the node does not actually know what nodes its connected to, but instead
   * directions of connections.
   *
   * @param dir the direction
   */
  void setConnectedDirection(Direction dir);

  /**
   * Sets if the player is in this node or not.
   *
   * @param contains whether or not this node will contain the player.
   */
  void shouldIContainPlayer(boolean contains);


  /**
   * Sets the leader for the node. Used in generation of the maze, nodes that have leaders to each
   * other are in the same set.
   *
   * @param node the node to be the leader.
   */
  void setLeader(IWritableNode node);

  /**
   * Finds the highest leader of the current node. Indicating the current set of the node.
   *
   * @return the set leader of the node.
   */
  IWritableNode findLeader();

  /**
   * Sets the node do a specific {@link RoomType}.
   *
   * @param roomType the {@link RoomType} the node should be.
   */
  void setRoomType(RoomType roomType);

  /**
   * Tells this node that its been visited by the player.
   */
  void visit();

  /**
   * Adds an attribute to a node.
   * @param attribute the attribute to add to a node.
   */
  void addAttribute(RoomAttribute attribute);

}
