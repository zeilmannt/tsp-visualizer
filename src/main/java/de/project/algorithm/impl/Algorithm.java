package de.project.algorithm.impl;

import de.project.algorithm.interfaces.IGraphAlgorithm;

public enum Algorithm {
    NEAREST_NEIGHBOR(new NearestNeighborAlgorithm()),
    ANT_COLONY(new AntColonyAlgorithm()),
    BRUTE_FORCE(new BruteForceAlgorithm());

    private final IGraphAlgorithm algorithm;

    Algorithm(IGraphAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public IGraphAlgorithm getInstance() {
        return algorithm;
    }

    @Override
    public String toString() {
        return algorithm.getName(); // For JComboBox display
    }
}
