package de.project.algorithm.impl;

import de.project.algorithm.interfaces.IGraphAlgorithm;
import de.project.model.impl.Node;
import de.project.algorithm.utils.AlgorithmUtils;

import java.util.ArrayList;
import java.util.List;

public class BruteForceAlgorithm implements IGraphAlgorithm {
    public List<Node> solve(List<Node> nodes) {
        if(nodes == null || nodes.size() == 1) return nodes;

        List<Node> path = new ArrayList<>();
        double minDistance = Double.MAX_VALUE;

        Node startNode = nodes.get(0);
        List<Node> restNodes = nodes.subList(1, nodes.size());

        for (List<Node> perm : AlgorithmUtils.generatePermutations(restNodes)) {
            List<Node> tempPath = new ArrayList<>();
            tempPath.add(startNode);
            tempPath.addAll(perm);
            tempPath.add(startNode);

            double distance = AlgorithmUtils.calculateTotalDistance(tempPath);
            if (distance < minDistance) {
                minDistance = distance;
                path = tempPath;
            }
        }

        return path;
    }

    @Override
    public String getName(){ return "Brute Force"; }

    @Override
    public String getDescription(){ return "TODO"; }
}
