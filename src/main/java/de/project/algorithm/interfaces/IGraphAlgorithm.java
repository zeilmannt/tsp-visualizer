/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.algorithm.interfaces;

import de.project.model.impl.Node;
import java.util.List;

public interface IGraphAlgorithm {
    List<List<Node>> solve(List<Node> nodes);
    String getName();
    String getDescription();
}
