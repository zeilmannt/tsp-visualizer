package de.project.model.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static de.project.model.impl.NodeHandler.addRandomNodes;
import static org.junit.jupiter.api.Assertions.*;

public class NodeHandlerTest {
    private List<Node> nodeList;

    @BeforeEach
    void setUp() {
        nodeList = new ArrayList<>();
    }

    @Test
    void testAddRandomNodesWithinBounds() {
        int nodesToAdd = 5;
        addRandomNodes(nodesToAdd, nodeList);

        assertEquals(nodesToAdd, nodeList.size(), "Node count mismatch");

        for (int i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.get(i);
            assertTrue(node.getX() >= 20 && node.getX() <= 760, "X position out of bounds");
            assertTrue(node.getY() >= 20 && node.getY() <= 480, "Y position out of bounds");
            assertEquals("Node" + i, node.getName(), "Node name mismatch");
        }
    }

    @Test
    void testAddRandomNodesAtMaxCapacity() {
        int maxNodes = 20;
        for (int i = 0; i < maxNodes; i++) {
            nodeList.add(new Node(i+5, i+5, "Node" + i));
        }

        addRandomNodes(5, nodeList);
        assertEquals(maxNodes, nodeList.size(), "No nodes should be added beyond MAX_NODES");
    }
}
