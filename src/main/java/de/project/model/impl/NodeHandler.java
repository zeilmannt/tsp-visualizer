/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.model.impl;

import de.project.algorithm.interfaces.IGraphAlgorithm;
import de.project.service.LogType;
import de.project.service.LoggerService;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NodeHandler {
    private static final int MAX_NODES = 20;

    public static void addRandomNodes(int numberOfNodes, List<Node> nodes){
        int currentNumberOfNodes = nodes.size();

        if (currentNumberOfNodes == MAX_NODES) {
            LoggerService.logMessage(LogType.WARN, "Max. number of nodes reached!");
            return;
        }

        Random rand = new Random();

        int panelWidth = 780;
        int panelHeight = 500;
        int margin = 20;

        for (int i = currentNumberOfNodes; i < currentNumberOfNodes + numberOfNodes; i++) {
            int xPos = rand.nextInt(panelWidth - margin * 2) + margin;
            int yPos = rand.nextInt(panelHeight - margin * 2) + margin;
            nodes.add(new Node(xPos, yPos, "Node" + i));
            LoggerService.logMessage(LogType.INFO, "Random node was added to panel (x=" + xPos + "|y=" + yPos + ")");
        }
    }

    public static List<List<Node>> computePath(List<Node> nodes, IGraphAlgorithm algorithm) {
        Objects.requireNonNull(nodes, "Node list cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        LoggerService.logMessage(LogType.SUCCESS, "Creating path using " + algorithm.getName());
        return algorithm.solve(nodes);
    }
}
