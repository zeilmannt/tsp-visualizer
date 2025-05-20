/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.ui;

import de.project.model.impl.Edge;
import de.project.model.impl.Node;
import de.project.service.LogType;
import de.project.service.LoggerService;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Setter
@Getter
public class GraphPanel extends JPanel {
    private List<Node> cities;
    private List<Edge> edges;
    private List<Node> path;
    private Node firstSelectedNode;
    private boolean addMode = false;

    public GraphPanel(List<Node> cities, List<Edge> edges, List<Node> path) {
        this.cities = cities;
        this.edges = edges;
        this.path = path;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double x = e.getX();
                double y = e.getY();

                if (SwingUtilities.isRightMouseButton(e)) {
                    Edge edgeToRemove = getEdgeNearPosition(x, y, 5.0);
                    if (edgeToRemove != null) {
                        edges.remove(edgeToRemove);
                        LoggerService.logMessage(LogType.INFO, "Edge removed between "
                                + edgeToRemove.getFrom().getName() + " and " + edgeToRemove.getTo().getName());
                        repaint();
                        return;
                    }
                }

                // Handle left-click (node selection or creation)
                Node clickedNode = getNodeAtPosition(x, y);
                if (clickedNode != null) {
                    if (firstSelectedNode == null) {
                        firstSelectedNode = clickedNode;
                        LoggerService.logMessage(LogType.INFO, "First node selected: " + clickedNode.getName());
                    } else if (firstSelectedNode != clickedNode) {
                        edges.add(new Edge(firstSelectedNode, clickedNode));
                        LoggerService.logMessage(LogType.INFO, "Edge created between " + firstSelectedNode.getName()
                                + " and " + clickedNode.getName());
                        firstSelectedNode = null;
                        repaint();
                    } else {
                        firstSelectedNode = null; // deselect
                    }
                } else if (addMode) {
                    Node newNode = new Node(x, y, "Node" + cities.size());
                    cities.add(newNode);
                    LoggerService.logMessage(LogType.INFO, "Manual node added at (" + x + "," + y + ")");
                    repaint();
                }
            }
        });

    }

    private Node getNodeAtPosition(double x, double y) {
        double radius = 10;
        for (Node node : cities) {
            double dx = node.getX() - x;
            double dy = node.getY() - y;
            if (Math.sqrt(dx * dx + dy * dy) <= radius) {
                return node;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int nodeRadius = 10;
        int halfRadius = nodeRadius / 2;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw edges
        g2.setColor(Color.GRAY);
        for (Edge edge : edges) {
            Node a = edge.getFrom();
            Node b = edge.getTo();
            g2.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
        }

        // Draw cities
        for (Node city : cities) {
            int x = (int) city.getX() - halfRadius;
            int y = (int) city.getY() - halfRadius;

            if (city.equals(firstSelectedNode)) {
                // Fill node
                g2.setColor(Color.WHITE);
                g2.fillOval(x, y, nodeRadius, nodeRadius);

                // Inner black circle
                g2.setColor(Color.BLACK);
                g2.fillOval(x + 2, y + 2, nodeRadius - 4, nodeRadius - 4);

                // Red outline
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(x, y, nodeRadius, nodeRadius);

                // Reset stroke after custom stroke
                g2.setStroke(new BasicStroke(1));
            } else {
                g2.setColor(Color.BLACK);
                g2.fillOval(x, y, nodeRadius, nodeRadius);
            }

            g2.setColor(Color.BLACK);
            g2.drawString(city.getName(), x + nodeRadius + 2, y);
        }

        // Draw a path
        if (path != null && path.size() > 1) {
            g2.setColor(Color.RED);
            for (int i = 0; i < path.size() - 1; i++) {
                Node a = path.get(i);
                Node b = path.get(i + 1);
                g2.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
            }
            Node first = path.get(0);
            Node last = path.get(path.size() - 1);
            g2.drawLine((int) last.getX(), (int) last.getY(), (int) first.getX(), (int) first.getY());
        }
    }

    private Edge getEdgeNearPosition(double x, double y, double threshold) {
        for (Edge edge : edges) {
            Node a = edge.getFrom();
            Node b = edge.getTo();

            double dist = pointToSegmentDistance(x, y, a.getX(), a.getY(), b.getX(), b.getY());
            if (dist <= threshold) {
                return edge;
            }
        }
        return null;
    }

    private double pointToSegmentDistance(double px, double py, double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (dx == 0 && dy == 0) {
            return Math.hypot(px - x1, py - y1);
        }

        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));
        double projX = x1 + t * dx;
        double projY = y1 + t * dy;
        return Math.hypot(px - projX, py - projY);
    }


}
