package model;

/**
 * Represents an edge in the maze. Currently only needed for maze creation and not functionality
 * after.
 */
class Edge implements IEdge{
  private final IWritableNode[] nodes;
  private final int weight;
  private final Direction dir;

  /**
   * Creates an edge between two nodes.
   *
   * @param weight the weight of the edge (for selection in the tree purposes)
   * @param start  the starting node
   * @param end    the ending node
   * @param dir    the direction of the edge
   */
  Edge(int weight, IWritableNode start, IWritableNode end, Direction dir) {
    if (start == null || end == null || dir == null) {
      throw new IllegalArgumentException("no null inputs");
    }
    this.dir = dir;
    this.nodes = new IWritableNode[]{start, end};
    this.weight = weight;


  }


  @Override
  public boolean areNodesConnected() {
    return this.nodes[0].findLeader() == this.nodes[1].findLeader();
  }

  @Override
  public void assignMySelf() {
    this.nodes[0].setConnectedDirection(this.dir);
    this.nodes[1].setConnectedDirection(this.dir.opposite());

  }

  @Override
  public void unionNodes() {

    this.nodes[0].findLeader().setLeader(this.nodes[1].findLeader());
  }

  @Override
  public int getWeight() {
    return this.weight;
  }


}
