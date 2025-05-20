package de.project.algorithm.impl;

import de.project.model.impl.Node;
import de.project.algorithm.interfaces.IGraphAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborAlgorithm implements IGraphAlgorithm {
    @Override
    public List<Node> solve(List<Node> cities){
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

    @Override
    public String getName(){ return "Nearest Neighbor"; }

    @Override
    public String getDescription(){ return "TODO"; }
}
