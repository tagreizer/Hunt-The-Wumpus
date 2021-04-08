package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the package private nodes and edges.
 */
public class EdgeAndNodeTests {
  IWritableNode n1;
  IWritableNode n2;
  IWritableNode n3;
  IWritableNode n4;

  IEdge e1;
  IEdge e2;
  IEdge e3;
  IEdge e4;


  @Before
  public void setUp() throws Exception {
    n1 = new WritableNode(0, 0);
    n2 = new WritableNode(1, 0);
    n3 = new WritableNode(0, 1);
    n4 = new WritableNode(1, 1);

    e1 = new Edge(1, n1, n2, Direction.SOUTH);
    e2 = new Edge(2, n3, n4, Direction.SOUTH);
    e3 = new Edge(3, n1, n3, Direction.EAST);
    e4 = new Edge(4, n2, n4, Direction.EAST);
  }

  @Test
  public void connectingNodes() {


    assertEquals(0, n1.getConnectedDirs().size());
    assertEquals(0, n2.getConnectedDirs().size());
    assertEquals(0, n3.getConnectedDirs().size());
    assertEquals(0, n4.getConnectedDirs().size());

    e1.assignMySelf();
    e2.assignMySelf();
    e3.assignMySelf();
    e4.assignMySelf();

    assertEquals(2, n1.getConnectedDirs().size());
    assertEquals(2, n2.getConnectedDirs().size());
    assertEquals(2, n3.getConnectedDirs().size());
    assertEquals(2, n4.getConnectedDirs().size());


  }



  @Test
  public void unionNodes() {
    assertFalse(e1.areNodesConnected());
    assertFalse(e2.areNodesConnected());
    assertFalse(e3.areNodesConnected());
    assertFalse(e4.areNodesConnected());

    e1.unionNodes();
    e2.unionNodes();
    e3.unionNodes();
    e4.unionNodes();

    assertTrue(e1.areNodesConnected());
    assertTrue(e2.areNodesConnected());
    assertTrue(e3.areNodesConnected());
    assertTrue(e4.areNodesConnected());

  }

  @Test
  public void getWeight() {
    assertEquals(1,e1.getWeight());
    assertEquals(2,e2.getWeight());
    assertEquals(3,e3.getWeight());
    assertEquals(4,e4.getWeight());
  }

  @Test
  public void setConnectedDirection() {
    assertEquals(0, n1.getConnectedDirs().size());
    assertEquals(0, n2.getConnectedDirs().size());
    assertEquals(0, n3.getConnectedDirs().size());
    assertEquals(0, n4.getConnectedDirs().size());

    n1.setConnectedDirection(Direction.SOUTH);
    n2.setConnectedDirection(Direction.NORTH);
    n3.setConnectedDirection(Direction.EAST);
    n4.setConnectedDirection(Direction.WEST);

    assertEquals(1, n1.getConnectedDirs().size());
    assertEquals(1, n2.getConnectedDirs().size());
    assertEquals(1, n3.getConnectedDirs().size());
    assertEquals(1, n4.getConnectedDirs().size());


  }

  @Test
  public void visiting() {
    assertFalse(n1.beenVisited());
    n1.visit();
    assertTrue(n1.beenVisited());
  }

  @Test
  public void findLeader() {
    assertEquals(n1, n1.findLeader());
    assertEquals(n2, n2.findLeader());
    assertEquals(n3, n3.findLeader());
    assertEquals(n4, n4.findLeader());

    e1.unionNodes();
    e2.unionNodes();

    assertEquals(n2, n1.findLeader());
    assertEquals(n2, n2.findLeader());
    assertEquals(n4, n3.findLeader());
    assertEquals(n4, n4.findLeader());

    e3.unionNodes();

    assertEquals(n4, n1.findLeader());
    assertEquals(n4, n2.findLeader());
    assertEquals(n4, n3.findLeader());
    assertEquals(n4, n4.findLeader());
  }

}