package model;

import java.util.List;

/**
 * Represents a node that is uneditable, can only be read not written.
 */
public interface IReadableNode {
  /**
   * Used to show the node in the debug print, will be reused/repurposed when views are added.
   *
   * @return the string of the node.
   */
  String debugPrint();

  /**
   * Used to show the nodes right edge in the debug print.
   *
   * @return the string of the nodes righ edge.
   */
  String debugPrintEEdge();

  /**
   * Used to show the nodes bottom edge in the debug print.
   *
   * @return the string of the nodes bottom edge
   */
  String debugPrintSEdge();

  /**
   * Returns the connected Directions that this node has.
   *
   * @return the connected directions.
   */
  List<Direction> getConnectedDirs();

  /**
   * Returns the position of the node.
   *
   * @return the position
   */
  Position getPosition();

  /**
   * Returns a copy of the node that is also a readable version.
   *
   * @return a readable copy of the node.
   */
  IReadableNode copy();

  /**
   * Gets the current room type of the node.
   *
   * @return the room type
   */
  RoomType getRoomType();

  /**
   * Returns whether or not this node has been visited by the player.
   *
   * @return whether this node has been visited
   */
  boolean beenVisited();

  /**
   * Returns the attributes of the room.
   *
   * @return the room attributes
   */
  List<RoomAttribute> getRoomAttributes();
}
