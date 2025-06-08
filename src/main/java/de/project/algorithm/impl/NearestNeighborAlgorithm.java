/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.algorithm.impl;

import de.project.model.impl.Node;
import de.project.algorithm.interfaces.IGraphAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborAlgorithm implements IGraphAlgorithm {
    @Override
    public List<List<Node>> solve(List<Node> cities){
        if(cities.isEmpty()) return List.of();

        List<Node> unvisited = new ArrayList<>(cities);
        List<Node> path = new ArrayList<>();
        List<List<Node>> steps = new ArrayList<>();

        Node current = unvisited.remove(0);
        path.add(current);
        steps.add(new ArrayList<>(path));

        while (!unvisited.isEmpty()) {
            Node nearest = unvisited.get(0);
            double shortest = current.distanceTo(nearest);

            for (Node c : unvisited) {
                double dist = current.distanceTo(c);
                if (dist < shortest) {
                    shortest = dist;
                    nearest = c;
                }
            }

            current = nearest;
            path.add(current);
            unvisited.remove(current);
            steps.add(new ArrayList<>(path));
        }

        return steps;
    }

    @Override
    public String getName(){ return "Nearest Neighbor"; }

    @Override
    public String getDescription(){ return "TODO"; }
}
