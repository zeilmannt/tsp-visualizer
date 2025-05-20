package de.project.model.impl;

import de.project.model.interfaces.INode;

public class Node implements INode {
    private double x, y;
    private String name;

    public Node(String name, double x, double y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public double distanceTo(Node city){
        double dx = this.x - city.x;
        double dy = this.y - city.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public double getX(){
        return x;
    }

    @Override
    public double getY(){
        return y;
    }
}
