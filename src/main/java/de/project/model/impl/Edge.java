package de.project.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class Edge {
    private final Node from;
    private final Node to;
}
