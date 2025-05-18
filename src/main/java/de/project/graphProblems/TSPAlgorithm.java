package de.project.graphProblems;

import java.util.ArrayList;
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
}
