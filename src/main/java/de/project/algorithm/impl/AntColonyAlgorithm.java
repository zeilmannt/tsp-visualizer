/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.algorithm.impl;

import de.project.algorithm.interfaces.IGraphAlgorithm;
import de.project.model.impl.Node;

import java.util.ArrayList;
import java.util.List;

public class AntColonyAlgorithm implements IGraphAlgorithm {
    @Override
    public List<List<Node>> solve(List<Node> nodes){
        final int numAnts = 10;
        final int numIterations = 100;
        final double alpha = 1.0; // = Pheromone importance
        final double beta = 5.0; // = Heuristic importance
        final double evaporation = 0.5;
        final double Q = 100;

        int n = nodes.size();
        double[][] pheromone = new double[n][n];
        double[][] distance = new double[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                distance[i][j] = nodes.get(i).distanceTo(nodes.get(i));
                pheromone[i][j] = 1.0;
            }
        }

        List<Node> bestTour = null;
        double bestTourLength = Double.MAX_VALUE;

        for(int iteration = 0; iteration < numIterations; iteration++) {
            List<List<Integer>> allAntPaths = new ArrayList<>();
            List<Double> allAntLengths = new ArrayList<>();

            // Each ant builds a path
            for(int ant = 0; ant < numAnts; ant++) {
                List<Integer> visited = new ArrayList<>();
                int current = new java.util.Random().nextInt(n);
                visited.add(current);

                while (visited.size() < n) {
                    int next = selectNextNode(current, visited, pheromone, distance, alpha, beta);
                    visited.add(next);
                    current = next;
                }
                allAntPaths.add(visited);

                // Compute tour length
                double length = computeTourLength(visited, distance);
                allAntLengths.add(length);

                // Update best tour
                if (length < bestTourLength) {
                    bestTourLength = length;
                    bestTour = new ArrayList<>();
                    for (int index : visited) bestTour.add(nodes.get(index));
                }
            }

            // Evaporate pheromone
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    pheromone[i][j] *= (1 - evaporation);
                }
            }

            // Deposit new pheromone
            for (int k = 0; k < numAnts; k++) {
                double contribution = Q / allAntLengths.get(k);
                List<Integer> path = allAntPaths.get(k);
                for (int i = 0; i < n - 1; i++) {
                    int from = path.get(i);
                    int to = path.get(i + 1);
                    pheromone[from][to] += contribution;
                    pheromone[to][from] += contribution;
                }
                pheromone[path.get(n - 1)][path.get(0)] += contribution;
                pheromone[path.get(0)][path.get(n - 1)] += contribution;
            }
        }

        return List.of(bestTour);
}

    @Override
    public String getName(){ return "Ant Colony Optimization"; }

    @Override
    public String getDescription(){ return "TODO"; }

    private double computeTourLength(List<Integer> tour, double[][] distance){
        double length = 0.0;
        for(int i=0; i<tour.size() - 1; i++){
            length += distance[tour.get(i)][tour.get(i+1)];
        }
        length += distance[tour.get(tour.size()-1)][tour.get(0)];
        return length;
    }

    private int selectNextNode(int current, List<Integer> visited,
                               double[][] pheromone, double[][] distance,
                               double alpha, double beta) {
        int n = pheromone.length;
        double[] probs = new double[n];
        double sum = 0.0;

        for (int j = 0; j < n; j++) {
            if (visited.contains(j)) continue;
            double tau = Math.pow(pheromone[current][j], alpha);
            double eta = Math.pow(1.0 / (distance[current][j] + 1e-10), beta);
            probs[j] = tau * eta;
            sum += probs[j];
        }

        if (sum == 0.0) {
            // fallback: return any unvisited node
            for (int j = 0; j < n; j++) {
                if (!visited.contains(j)) {
                    return j;
                }
            }
            return 0;
        }

        // Normalize probabilities
        for (int j = 0; j < n; j++) {
            probs[j] /= sum;
        }

        // Roulette wheel selection
        double r = Math.random();
        double cumulative = 0.0;
        for (int j = 0; j < n; j++) {
            if (visited.contains(j)) continue;
            cumulative += probs[j];
            if (r <= cumulative) {
                return j;
            }
        }

        // fallback: if no selection was made due to rounding errors
        for (int j = 0; j < n; j++) {
            if (!visited.contains(j)) return j;
        }
        return 0;
    }

}
