package tsp;

import java.util.ArrayList;
import java.util.List;

public class TSPAlgorithm {
    public static List<City> solveNearestNeighbor(List<City> cities){
        List<City> unvisited = new ArrayList<>(cities);
        List<City> path = new ArrayList<>();

        City current = unvisited.remove(0);
        path.add(current);

        while(!unvisited.isEmpty()){
            City nearest = unvisited.get(0);
            double shortest = current.distanceTo(nearest);

            for (City c : unvisited){
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
