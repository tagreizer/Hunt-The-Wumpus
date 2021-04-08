package model;

/**
 * Represents an edge in the maze. Currently only needed for maze creation and not functionality
 * after.
 */
public interface IEdge {

  /**
   * Checks if the two nodes this edge connects are already in the same set.
   *
   * @return if they are connected already.
   */
  boolean areNodesConnected();

  /**
   * Assigns this edge to both of the nodes. Assigns itself in edge's direction to the start and the
   * opposite direction to the ending node.
   */
  void assignMySelf();

  /**
   * Tells the nodes this edge connects to join sets with each other.
   */
  void unionNodes();

  /**
   * Gets the weight of this edge.
   *
   * @return the weight.
   */
  int getWeight();

}
