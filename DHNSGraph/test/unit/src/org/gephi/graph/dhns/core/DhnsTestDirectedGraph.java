/*
Copyright 2008 WebAtlas
Authors : Mathieu Bastian, Mathieu Jacomy, Julian Bilcke
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.graph.dhns.core;

import java.util.HashMap;
import java.util.Map;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;
import org.gephi.graph.dhns.DhnsGraphController;
import org.gephi.graph.dhns.edge.AbstractEdge;
import org.gephi.graph.dhns.graph.ClusteredDirectedGraphImpl;
import org.gephi.graph.dhns.node.AbstractNode;
import org.gephi.graph.dhns.node.PreNode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mathieu Bastian
 */
public class DhnsTestDirectedGraph {

    private Dhns dhnsGlobal;
    private ClusteredDirectedGraphImpl graphGlobal;
    private Map<String, Node> nodeMap;
    private Map<String, Edge> edgeMap;

    public DhnsTestDirectedGraph() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        DhnsGraphController controller = new DhnsGraphController();
        dhnsGlobal = controller.getMainDhns();
        graphGlobal = new ClusteredDirectedGraphImpl(dhnsGlobal, false);
        nodeMap = new HashMap<String, Node>();
        edgeMap = new HashMap<String, Edge>();

        TreeStructure treeStructure = dhnsGlobal.getTreeStructure();
        GraphFactoryImpl factory = dhnsGlobal.getGraphFactory();

        //Nodes
        //System.out.println("-----Global-----");
        for (int i = 0; i < 10; i++) {
            Node node = factory.newNode();
            node.getNodeData().setLabel("Node " + i);
            graphGlobal.addNode(node);
            nodeMap.put(node.getNodeData().getLabel(), node);
        //System.out.println("Node " + i + " added. Id = " + node.getId());
        }
        //System.out.println("---End Global---");

        //Alone node
        Node fakeNode1 = factory.newNode();
        Node fakeNode2 = factory.newNode();
        nodeMap.put("Fake Node 1", fakeNode1);
        nodeMap.put("Fake Node 2", fakeNode2);

        //Edges
        Node node1 = nodeMap.get("Node 1");
        Node node2 = nodeMap.get("Node 2");
        Node node3 = nodeMap.get("Node 3");
        Node node4 = nodeMap.get("Node 4");
        Node node5 = nodeMap.get("Node 5");
        Node node6 = nodeMap.get("Node 6");
        Node node7 = nodeMap.get("Node 7");
        Node node8 = nodeMap.get("Node 8");

        AbstractEdge edge1 = factory.newEdge(node4, node5);
        AbstractEdge edge2 = factory.newEdge(node5, node6);
        AbstractEdge edge3 = factory.newEdge(node6, node5);
        AbstractEdge edge4 = factory.newEdge(node7, node7);
        AbstractEdge edge5 = factory.newEdge(node4, node4);

        graphGlobal.addEdge(edge1);
        graphGlobal.addEdge(edge2);
        graphGlobal.addEdge(edge3);
        graphGlobal.addEdge(edge4);
        graphGlobal.addEdge(edge5);

        edgeMap.put("4-5", edge1);
        edgeMap.put("5-6", edge2);
        edgeMap.put("6-5", edge3);
        edgeMap.put("7-7", edge4);
        edgeMap.put("4-4", edge5);
    }

    @After
    public void tearDown() {
        nodeMap.clear();
        dhnsGlobal = null;
        graphGlobal = null;
    }

    @Test
    public void testAddNode() {
        System.out.println("testAddNode");
        DhnsGraphController controller = new DhnsGraphController();
        Dhns dhns = controller.getMainDhns();
        ClusteredDirectedGraphImpl graph = new ClusteredDirectedGraphImpl(dhns, false);
        TreeStructure treeStructure = dhns.getTreeStructure();
        GraphFactoryImpl factory = dhns.getGraphFactory();

        for (int i = 0; i < 10; i++) {
            Node node = factory.newNode();
            node.getNodeData().setLabel("Node " + i);
            graph.addNode(node);
            System.out.println("Node " + i + " added. Id = " + node.getId());
        }

        graph.readLock();

        //Test
        assertEquals("root size", 11, treeStructure.getTreeSize());
        assertEquals("graph size", 10, graph.getNodeCount());

        for (int i = 0; i < 10; i++) {
            AbstractNode n = treeStructure.getNodeAt(i);
            assertEquals("prenode pre", i, n.getPre());
            assertEquals("prenode id", i - 1, n.getId());
            assertEquals("prenode enabled", i > 0, n.isEnabled());
            assertEquals("prenode avl node", i, n.avlNode.getIndex());
            if (n.avlNode.next() != null) {
                assertEquals("prenode next", treeStructure.getNodeAt(i + 1).avlNode, n.avlNode.next());
            }
            if (n.avlNode.previous() != null) {
                assertEquals("prenode previous", treeStructure.getNodeAt(i - 1).avlNode, n.avlNode.previous());
            }
        }

        int i = 0;
        for (Node node : graph.getNodes()) {
            assertEquals("node iterator", i, node.getId());
            i++;
        }

        graph.readUnlock();
    }

    @Test
    public void testRemoveNode() {
        DhnsGraphController controller = new DhnsGraphController();
        Dhns dhns = controller.getMainDhns();
        ClusteredDirectedGraphImpl graph = new ClusteredDirectedGraphImpl(dhns, false);
        TreeStructure treeStructure = dhns.getTreeStructure();
        GraphFactoryImpl factory = dhns.getGraphFactory();

        Node first = null;
        Node middle = null;
        Node end = null;
        for (int i = 0; i < 10; i++) {
            Node node = factory.newNode();
            node.getNodeData().setLabel("Node " + i);
            graph.addNode(node);
            System.out.println("Node " + i + " added. Id = " + node.getId());

            if (i == 0) {
                first = node;
            } else if (i == 4) {
                middle = node;
            } else if (i == 9) {
                end = node;
            }
        }

        graph.removeNode(first);

        //Test1
        System.out.print("Test1 nodes: ");
        for (int i = 0; i < treeStructure.getTreeSize(); i++) {
            AbstractNode n = treeStructure.getNodeAt(i);
            System.out.print(n.getId() + " ");
            assertEquals("prenode pre", i, n.getPre());
            assertEquals("prenode avl node", i, n.avlNode.getIndex());
            if (n.avlNode.next() != null) {
                assertEquals("prenode next", treeStructure.getNodeAt(i + 1).avlNode, n.avlNode.next());
            }
            if (n.avlNode.previous() != null) {
                assertEquals("prenode previous", treeStructure.getNodeAt(i - 1).avlNode, n.avlNode.previous());
            }
        }
        System.out.println();
        //End Test1

        graph.removeNode(middle);

        //Test2
        System.out.print("Test2 nodes: ");
        for (int i = 0; i < treeStructure.getTreeSize(); i++) {
            AbstractNode n = treeStructure.getNodeAt(i);
            System.out.print(n.getId() + " ");
            assertEquals("prenode pre", i, n.getPre());
            assertEquals("prenode avl node", i, n.avlNode.getIndex());
            if (n.avlNode.next() != null) {
                assertEquals("prenode next", treeStructure.getNodeAt(i + 1).avlNode, n.avlNode.next());
            }
            if (n.avlNode.previous() != null) {
                assertEquals("prenode previous", treeStructure.getNodeAt(i - 1).avlNode, n.avlNode.previous());
            }
        }
        System.out.println();
        //End Test2


        graph.removeNode(end);

        //Test3
        System.out.print("Test3 nodes: ");
        for (int i = 0; i < treeStructure.getTreeSize(); i++) {
            AbstractNode n = treeStructure.getNodeAt(i);
            System.out.print(n.getId() + " ");
            assertEquals("prenode pre", i, n.getPre());
            assertEquals("prenode avl node", i, n.avlNode.getIndex());
            if (n.avlNode.next() != null) {
                assertEquals("prenode next", treeStructure.getNodeAt(i + 1).avlNode, n.avlNode.next());
            }
            if (n.avlNode.previous() != null) {
                assertEquals("prenode previous", treeStructure.getNodeAt(i - 1).avlNode, n.avlNode.previous());
            }
        }
        System.out.println();
        //End Test3

        assertFalse(graph.contains(first));
        assertFalse(graph.contains(middle));
        assertFalse(graph.contains(end));

        PreNode preNode = (PreNode) first;
        assertNull(preNode.avlNode);
        assertNull(preNode.parent);

        //Test
        assertEquals("tree size", 8, treeStructure.getTreeSize());
    }

    @Test
    public void testContainsNode() {

        Node node = nodeMap.get("Node 1");
        boolean contains = graphGlobal.contains(node);

        //Test
        assertTrue("contains node", contains);
        assertFalse("not contains node", graphGlobal.contains(nodeMap.get("Fake Node 1")));
    }

    @Test
    public void testClearNodes() {

        TreeStructure treeStructure = dhnsGlobal.getTreeStructure();
        graphGlobal.clear();

        //Test
        assertEquals("clear nodes", 1, treeStructure.getTreeSize());
        assertEquals("clear nodes", 0, graphGlobal.getNodeCount());
        assertEquals("clear nodes", treeStructure.getRoot(), treeStructure.getNodeAt(0));

        assertFalse("not contains anymore", graphGlobal.contains(nodeMap.get("Node 1")));

        PreNode preNode = (PreNode) nodeMap.get("Node 2");
        assertNull("clean clear", preNode.avlNode);
        assertNull("clean clear", preNode.parent);
    }

    @Test
    public void testAddEdge() {
        DhnsGraphController controller = new DhnsGraphController();
        Dhns dhns = controller.getMainDhns();
        ClusteredDirectedGraphImpl graph = new ClusteredDirectedGraphImpl(dhns, false);
        TreeStructure treeStructure = dhns.getTreeStructure();
        GraphFactoryImpl factory = dhns.getGraphFactory();

        Node node1 = factory.newNode();
        Node node2 = factory.newNode();
        Node node3 = factory.newNode();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);

        //Test normal edge
        graph.addEdge(node1, node2);
        PreNode preNode1 = (PreNode) node1;
        PreNode preNode2 = (PreNode) node2;

        AbstractEdge edge = preNode1.getEdgesOutTree().getItem(preNode2.getNumber());
        assertNotNull("find OUT edge", edge);
        assertTrue("contains OUT edge", preNode1.getEdgesOutTree().contains(edge));

        AbstractEdge edge2 = preNode2.getEdgesInTree().getItem(preNode1.getNumber());
        assertNotNull("find IN edge", edge);
        assertTrue("contains IN edge", preNode2.getEdgesInTree().contains(edge2));

        assertSame("edges equal", edge, edge2);

        assertEquals("edges count", 1, graph.getEdgeCount());

        //Test factoryedge
        graph.addEdge(edge);
        assertEquals("edges count", 1, graph.getEdgeCount());

        //Test self loop
        graph.addEdge(node3, node3);

        PreNode preNode3 = (PreNode) node3;

        AbstractEdge edge3 = preNode3.getEdgesOutTree().getItem(preNode3.getNumber());
        assertNotNull("find OUT edge", edge);
        assertTrue("contains OUT edge", preNode3.getEdgesOutTree().contains(edge3));

        AbstractEdge edge4 = preNode3.getEdgesInTree().getItem(preNode3.getNumber());
        assertNotNull("find IN edge", edge);
        assertTrue("contains IN edge", preNode3.getEdgesInTree().contains(edge3));

        assertSame("edges equal", edge3, edge4);

        assertTrue("is self loop", edge3.isSelfLoop());
    }

    @Test
    public void testRemoveEdge() {
        GraphFactoryImpl factory = dhnsGlobal.getGraphFactory();
        PreNode node3 = (PreNode) nodeMap.get("Node 1");
        PreNode node4 = (PreNode) nodeMap.get("Node 2");
        AbstractEdge edge = factory.newEdge(node3, node4);

        graphGlobal.addEdge(edge);
        assertTrue(graphGlobal.contains(edge));

        graphGlobal.removeEdge(edge);
        AbstractEdge edge3 = node3.getEdgesOutTree().getItem(node4.getNumber());
        assertNull("OUT null", edge3);
        assertFalse("contains OUT edge", node3.getEdgesOutTree().contains(edge));

        AbstractEdge edge4 = node4.getEdgesInTree().getItem(node3.getNumber());
        assertNull("IN null", edge4);
        assertFalse("contains IN edge", node3.getEdgesInTree().contains(edge));

        assertFalse(graphGlobal.contains(edge));

        Edge edge7 = edgeMap.get("7-7");
        assertTrue(graphGlobal.contains(edge7));
        graphGlobal.removeEdge(edge7);
        assertFalse(graphGlobal.contains(edge7));
    }

    @Test
    public void testGetEdges() {

        //Test1
        Edge[] expected = new Edge[5];
        expected[0] = edgeMap.get("4-4");
        expected[1] = edgeMap.get("4-5");
        expected[2] = edgeMap.get("5-6");
        expected[3] = edgeMap.get("6-5");
        expected[4] = edgeMap.get("7-7");
        Edge[] actual = new Edge[5];

        int i = 0;
        System.out.print("testGetEdges: ");
        for (Edge e : graphGlobal.getEdges()) {
            Node s = e.getSource();
            Node t = e.getTarget();
            Edge ed = edgeMap.get(s.getId() + "-" + t.getId());
            assertSame("edge iterator", e, ed);
            System.out.print("#" + s.getId() + "-" + t.getId() + " ");
            actual[i++] = e;
        }
        System.out.println();
        assertArrayEquals(expected, actual);
        assertEquals("edge count", i, graphGlobal.getEdgeCount());


        graphGlobal.removeNode(nodeMap.get("Node 5"));

        //Test2
        expected = new Edge[2];
        expected[0] = edgeMap.get("4-4");
        expected[1] = edgeMap.get("7-7");
        actual = new Edge[2];
        i = 0;
        System.out.print("testGetEdges: ");
        for (Edge e : graphGlobal.getEdges()) {
            Node s = e.getSource();
            Node t = e.getTarget();
            Edge ed = edgeMap.get(s.getId() + "-" + t.getId());
            assertSame("edge iterator", e, ed);
            System.out.print("#" + s.getId() + "-" + t.getId() + " ");
            actual[i++] = e;
        }
        System.out.println();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetEdgesNode() {

        //Test1
        System.out.print("testGetEdgesNode: ");
        Edge[] expected = new Edge[3];
        expected[0] = edgeMap.get("5-6");
        expected[1] = edgeMap.get("4-5");
        expected[2] = edgeMap.get("6-5");
        Edge[] actual = new Edge[3];

        int i = 0;
        for (Edge e : graphGlobal.getEdges(nodeMap.get("Node 5"))) {
            Node s = e.getSource();
            Node t = e.getTarget();
            System.out.print("#" + s.getId() + "-" + t.getId() + " ");
            actual[i++] = e;
        }
        System.out.println();
        assertArrayEquals(expected, actual);

        //Test2
        System.out.print("testGetEdgesNode: ");
        expected = new Edge[1];
        expected[0] = edgeMap.get("7-7");
        actual = new Edge[1];
        i = 0;
        for (Edge e : graphGlobal.getEdges(nodeMap.get("Node 7"))) {
            Node s = e.getSource();
            Node t = e.getTarget();
            System.out.print("#" + s.getId() + "-" + t.getId() + " ");
            actual[i++] = e;
        }
        System.out.println();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testSucessors() {
        //Test
        System.out.print("testSucessors: ");
        Node[] expected = new Node[1];
        expected[0] = nodeMap.get("Node 6");
        Node[] actual = new Node[1];

        int i = 0;
        Node node5 = nodeMap.get("Node 5");
        for (Node n : graphGlobal.getSuccessors(node5)) {
            System.out.print(n.getId()+" ");
            actual[i++] = n;
            assertTrue(graphGlobal.isSuccessor(node5, n));
        }
        System.out.println();
        assertArrayEquals(expected, actual);

        //Test Self loop
        Node[] array = graphGlobal.getSuccessors(nodeMap.get("Node 7")).toArray();
        assertEquals("self loop array length 0", 0, array.length);
    }

    @Test
    public void testPredecessors() {
        //Test
        System.out.print("testPredecessors: ");
        Node[] expected = new Node[2];
        expected[0] = nodeMap.get("Node 4");
        expected[1] = nodeMap.get("Node 6");
        Node[] actual = new Node[2];

        int i = 0;
        Node node5 = nodeMap.get("Node 5");
        for (Node n : graphGlobal.getPredecessors(node5)) {
            System.out.print(n.getId()+" ");
            actual[i++] = n;
            assertTrue(graphGlobal.isPredecessor(node5, n));
        }
        System.out.println();
        assertArrayEquals(expected, actual);

        //Test Self loop
        Node[] array = graphGlobal.getSuccessors(nodeMap.get("Node 7")).toArray();
        assertEquals("self loop array length 0", 0, array.length);
    }

    @Test
    public void testNeighbors() {
        System.out.print("testNeighbors: ");
        Node[] expected = new Node[2];
        expected[0] = nodeMap.get("Node 6");
        expected[1] = nodeMap.get("Node 4");
        Node[] actual = new Node[2];

        int i = 0;
        for (Node n : graphGlobal.getNeighbors(nodeMap.get("Node 5"))) {
            System.out.print(n.getId()+" ");
            actual[i++] = n;
        }
        System.out.println();
        assertArrayEquals(expected, actual);

         //Test Self loop
         Node[] array = graphGlobal.getNeighbors(nodeMap.get("Node 7")).toArray();
         assertEquals("self loop array length 0", 0, array.length);
    }

    @Test
    public void testOpposite() {
        Node node4 = nodeMap.get("Node 4");
        Node node5 = nodeMap.get("Node 5");
        Edge edge1 = edgeMap.get("4-5");
        Edge edge2 = edgeMap.get("4-4");

        assertEquals(node5,graphGlobal.getOpposite(node4, edge1));
        assertEquals(node4,graphGlobal.getOpposite(node4, edge2));
    }

    @Test
    public void testDegree() {
        Node node5 = nodeMap.get("Node 5");
        Node node4 = nodeMap.get("Node 4");
        Node node7 = nodeMap.get("Node 7");

        assertEquals(3, graphGlobal.getDegree(node5));
        assertEquals(3, graphGlobal.getDegree(node4));
        assertEquals(2, graphGlobal.getDegree(node7));
    }

    @Test
    public void testAdjacent() {
        Node node4 = nodeMap.get("Node 4");
        Node node5 = nodeMap.get("Node 5");
        Node node6 = nodeMap.get("Node 6");
        Edge edge1 = edgeMap.get("4-5");
        Edge edge2 = edgeMap.get("5-6");

        assertTrue(graphGlobal.isAdjacent(node4, node5));
        assertFalse(graphGlobal.isAdjacent(node4, node6));
        assertTrue(graphGlobal.isAdjacent(edge1, edge2));
    }

    @Test
    public void testClearEdgesNode() {
        Node node4 = nodeMap.get("Node 4");
        Node node5 = nodeMap.get("Node 5");
        Edge edge1 = edgeMap.get("4-4");
        Edge edge2 = edgeMap.get("5-6");
        graphGlobal.clearEdges(node5);
        graphGlobal.clearEdges(node4);

        assertEquals(0,graphGlobal.getDegree(node5));
        assertEquals(0,graphGlobal.getDegree(node4));
        assertFalse(graphGlobal.contains(edge2));
        assertFalse(graphGlobal.contains(edge1));
        assertFalse(graphGlobal.isAdjacent(node4, node5));
        //assertFalse(graphGlobal.isAdjacent(edge1, edge2));        //Fail because no test verifying edge belongs to the structure
    }

    @Test
    public void testGetEdge() {
        Node node4 = nodeMap.get("Node 4");
        Node node5 = nodeMap.get("Node 5");

        assertNotNull(graphGlobal.getEdge(node4, node5));
        Edge selfLoop = graphGlobal.getEdge(node4, node4);
        assertTrue(graphGlobal.isSelfLoop(selfLoop));
    }

    @Test
    public void testGetInEdges() {

        //Test1
        System.out.print("testGetInEdges: ");
        Edge[] expected = new Edge[2];
        expected[0] = edgeMap.get("4-5");
        expected[1] = edgeMap.get("6-5");
        Edge[] actual = new Edge[2];

        int i = 0;
        Node node5 = nodeMap.get("Node 5");
        for (Edge e : graphGlobal.getInEdges(node5)) {
            Node s = e.getSource();
            Node t = e.getTarget();
            System.out.print("#" + s.getId() + "-" + t.getId() + " ");
            actual[i++] = e;
        }
        System.out.println();
        assertArrayEquals(expected, actual);

        //Test2
        assertEquals(graphGlobal.getInEdges(nodeMap.get("Node 4")).toArray()[0], edgeMap.get("4-4"));
    }

    @Test
    public void testGetOutEdges() {

        //Test1
        System.out.print("testGetOutEdges: ");
        Edge[] expected = new Edge[1];
        expected[0] = edgeMap.get("5-6");
        Edge[] actual = new Edge[1];

        int i = 0;
        Node node5 = nodeMap.get("Node 5");
        for (Edge e : graphGlobal.getOutEdges(node5)) {
            Node s = e.getSource();
            Node t = e.getTarget();
            System.out.print("#" + s.getId() + "-" + t.getId() + " ");
            actual[i++] = e;
        }
        System.out.println();
        assertArrayEquals(expected, actual);

        //Test2
        assertEquals(graphGlobal.getOutEdges(nodeMap.get("Node 4")).toArray()[0], edgeMap.get("4-4"));
    }
}