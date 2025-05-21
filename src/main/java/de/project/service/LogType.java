/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogType {
    ERROR("Indicates a serious failure (text color is red)."),
    WARN("Indicates a potential problem or unexpected situation (text color is yellow)."),
    INFO("Provides general runtime information (text color is standard)."),
    SUCCESS("Operation completed successfully (text color is green).");

    private final String description;
}
