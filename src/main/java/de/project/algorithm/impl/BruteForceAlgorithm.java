package de.project.algorithm.impl;

import de.project.algorithm.interfaces.IGraphAlgorithm;
import de.project.model.impl.Node;
import de.project.algorithm.utils.AlgorithmUtils;

import java.util.ArrayList;
import java.util.List;

public class BruteForceAlgorithm implements IGraphAlgorithm {
    public List<List<Node>> solve(List<Node> nodes) {
        List<List<Node>> steps = new ArrayList<>();

        if (nodes == null) {
            steps.add(new ArrayList<>());
            return steps;
        }

        if (nodes.size() <= 1) {
            steps.add(new ArrayList<>(nodes));
            return steps;
        }

        Node startNode = nodes.get(0);
        List<Node> restNodes = nodes.subList(1, nodes.size());

        double minDistance = Double.MAX_VALUE;
        List<Node> bestPath = new ArrayList<>();

        for (List<Node> perm : AlgorithmUtils.generatePermutations(restNodes)) {
            List<Node> tempPath = new ArrayList<>();
            tempPath.add(startNode);
            tempPath.addAll(perm);
            tempPath.add(startNode);

            steps.add(new ArrayList<>(tempPath));

            double distance = AlgorithmUtils.calculateTotalDistance(tempPath);
            if (distance < minDistance) {
                minDistance = distance;
                bestPath = new ArrayList<>(tempPath);
            }
        }

        steps.add(bestPath);

        return steps;
    }

    @Override
    public String getName(){ return "Brute Force"; }

    @Override
    public String getDescription(){ return "TODO"; }
}
