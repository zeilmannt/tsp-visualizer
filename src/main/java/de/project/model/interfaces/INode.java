/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.model.interfaces;

import de.project.model.impl.Node;

public interface INode {
    double distanceTo(Node city);
    String getName();
    double getX();
    double getY();
}
