package de.project.model.impl;

import de.project.algorithm.impl.Algorithm;
import de.project.service.LogType;
import de.project.service.LoggerService;
import de.project.algorithm.impl.TSPAlgorithm;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NodeHandler {
    private static final int MAX_NODES = 20;

    public static void addRandomNodes(int numberOfNodes, List<Node> nodes){
        int currentNumberOfNodes = nodes.size();

        if(currentNumberOfNodes >= MAX_NODES){
            LoggerService.logMessage(LogType.ERROR, "Max. number of nodes!");
            return;
        }

        Random rand = new Random();
        int xPos, yPos;
        for (int i = currentNumberOfNodes; i < currentNumberOfNodes + numberOfNodes; i++) {
            xPos = rand.nextInt(700);
            yPos = rand.nextInt(500);
            nodes.add(new Node("Node" + i, xPos, yPos));
            LoggerService.logMessage(LogType.INFO, "Random node was added to panel (x=" + xPos + "|y=" + yPos + ")");
        }
    }

    public static List<Node> computePath(List<Node> nodes, Algorithm algorithm){
        Objects.requireNonNull(nodes, "Node list cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm type cannot be null");

        LoggerService.logMessage(LogType.SUCCESS, "Path is being created using " + algorithm.getName());

        return switch (algorithm) {
            case ANT_COLONY       -> TSPAlgorithm.solveAntColony(nodes);
            case NEAREST_NEIGHBOR -> TSPAlgorithm.solveNearestNeighbor(nodes);
            case BRUTE_FORCE      -> TSPAlgorithm.solveBruteForce(nodes);
        };
    }
}
