package de.project.algorithm.interfaces;

import de.project.model.impl.Node;

import java.util.List;

public interface IGraphAlgorithm {
    List<Node> solve(List<Node> nodes);
    String getName();
    String getDescription();
}
