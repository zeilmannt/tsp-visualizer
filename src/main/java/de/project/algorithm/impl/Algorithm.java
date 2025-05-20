package de.project.algorithm.impl;

public enum Algorithm {
    NEAREST_NEIGHBOR("Nearest Neighbor", "---"),
    ANT_COLONY("Ant Colony Optimization", "N/A"),
    BRUTE_FORCE("Brute Force", "N/A");

    private final String name;
    private final String description;

    Algorithm(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName(){ return name; }
    public String getDescription(){ return description; }
}
