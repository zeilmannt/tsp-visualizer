/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

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
        return algorithm.getName();
    }
}
