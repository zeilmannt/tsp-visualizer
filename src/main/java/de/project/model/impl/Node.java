/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.model.impl;

import de.project.model.interfaces.INode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Node implements INode {
    private final double x;
    private final double y;
    private final String name;

    @Override
    public double distanceTo(Node node){
        double dx = this.x - node.x;
        double dy = this.y - node.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
