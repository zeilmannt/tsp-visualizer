package de.project.model.interfaces;

import de.project.model.impl.Node;

public interface INode {
    double distanceTo(Node city);
    String getName();
    double getX();
    double getY();
}
