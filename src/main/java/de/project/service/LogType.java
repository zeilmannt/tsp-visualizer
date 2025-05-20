/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.service;

public enum LogType {
    ERROR("Severe issue appeared (text color is red)."),
    WARN("A potential problem or something unexpected (text color is yellow)."),
    INFO("General runtime information (text color is standard)."),
    SUCCESS("Operation completed successfully (text color is green).");

    private final String description;

    LogType(String description){
        this.description = description;
    }

    public String getDescription(){ return description; }
}
