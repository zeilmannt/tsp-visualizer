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
        return new ArrayList<>();
    }

    @Override
    public String getName(){ return "Ant Colony Optimization"; }

    @Override
    public String getDescription(){ return "TODO"; }
}
