package de.project.algorithm;

import de.project.algorithm.impl.NearestNeighborAlgorithm;
import de.project.model.impl.Node;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NearestNeighborTest {

    @Test
    public void testSolveSimpleCase() {
        Node a = new Node(0, 0, "A");
        Node b = new Node(1, 0, "B");
        Node c = new Node(0, 1, "C");

        List<Node> cities = Arrays.asList(a, b, c);

        NearestNeighborAlgorithm algorithm = new NearestNeighborAlgorithm();
        List<List<Node>> steps = algorithm.solve(cities);

        assertNotNull(steps);
        assertEquals(3, steps.size());

        List<Node> finalPath = steps.get(steps.size() - 1);
        assertEquals(3, finalPath.size());
        assertTrue(finalPath.contains(a));
        assertTrue(finalPath.contains(b));
        assertTrue(finalPath.contains(c));

        assertEquals(a, finalPath.get(0));
    }

    @Test
    public void testGetName() {
        NearestNeighborAlgorithm algorithm = new NearestNeighborAlgorithm();
        assertEquals("Nearest Neighbor", algorithm.getName());
    }

    @Test
    public void testGetDescription() {
        NearestNeighborAlgorithm algorithm = new NearestNeighborAlgorithm();
        assertEquals("TODO", algorithm.getDescription());
    }

    @Test
    public void testSolveEmptyInput() {
        NearestNeighborAlgorithm algorithm = new NearestNeighborAlgorithm();
        List<List<Node>> result = algorithm.solve(List.of());
        assertTrue(result.isEmpty());
    }
}
