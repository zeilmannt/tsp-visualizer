package de.project.algorithm.impl;

import de.project.model.impl.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSPAlgorithm {
    public static List<Node> solveNearestNeighbor(List<Node> cities){
        List<Node> unvisited = new ArrayList<>(cities);
        List<Node> path = new ArrayList<>();

        Node current = unvisited.remove(0);
        path.add(current);

        while(!unvisited.isEmpty()){
            Node nearest = unvisited.get(0);
            double shortest = current.distanceTo(nearest);

            for (Node c : unvisited){
                double dist = current.distanceTo(c);

                if(dist < shortest){
                    shortest = dist;
                    nearest = c;
                }
            }

            current = nearest;
            path.add(current);
            unvisited.remove(current);
        }
        return path;
    }

    public static List<Node> solveAntColony(List<Node> nodes) {
        return new ArrayList<>();
    }

    public static List<Node> solveBruteForce(List<Node> nodes) {
        if(nodes == null || nodes.size() == 1) return nodes;

        List<Node> path = new ArrayList<>();
        double minDistance = Double.MAX_VALUE;

        Node startNode = nodes.get(0);
        List<Node> restNodes = nodes.subList(1, nodes.size());

        for (List<Node> perm : generatePermutations(restNodes)) {
            List<Node> tempPath = new ArrayList<>();
            path.add(startNode);
            path.addAll(perm);
            path.add(startNode);

            double distance = calculateTotalDistance(tempPath);
            if (distance < minDistance) {
                minDistance = distance;
                path = tempPath;
            }
        }

        return path;
    }

    private static List<List<Node>> generatePermutations(List<Node> nodes){
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

    private static double calculateTotalDistance(List<Node> path){
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
