/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.algorithm.utils;

import de.project.model.impl.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AlgorithmUtils {
    private AlgorithmUtils(){}

    public static List<List<Node>> generatePermutations(List<Node> nodes){
        List<List<Node>> result = new ArrayList<>();
        permute(nodes, 0, result);
        return result;
    }

    private static void permute(List<Node> nodes, int start, List<List<Node>> permutatedNodes){
        if (start == nodes.size()){
            permutatedNodes.add(new ArrayList<>(nodes));
        }
        else{
            for (int i=start; i<nodes.size(); i++){
                Collections.swap(nodes, i, start);
                permute(nodes, start+1, permutatedNodes);
                Collections.swap(nodes, i, start);
            }
        }
    }

    public static double calculateTotalDistance(List<Node> path){
        double total = 0;
        for (int i=0; i<path.size()-1; i++){
            total += distanceBetween(path.get(i), path.get(i+1));
        }
        return total;
    }

    private static double distanceBetween(Node node1, Node node2){
        double dx = node1.getX() - node2.getX();
        double dy = node1.getY() - node2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
