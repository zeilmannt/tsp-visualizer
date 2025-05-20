package de.project.ui;

import de.project.algorithm.impl.Algorithm; // Assuming enum maps to actual impls
import de.project.algorithm.interfaces.IGraphAlgorithm;
import de.project.model.impl.Node;
import de.project.service.LogType;
import de.project.service.LoggerService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static de.project.model.impl.NodeHandler.*;

public class Ui extends JFrame {
    private final List<Node> cities = new ArrayList<>();
    private List<Node> path = new ArrayList<>();
    private List<List<Node>> pathSteps = new ArrayList<>();
    private int currentStep = 0;
    private Timer animationTimer;

    // UI Components
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JComboBox<Algorithm> solveOptions = new JComboBox<>(Algorithm.values());
    private final JButton addRandomCityButton = new JButton("Add Random Cities");
    private final JButton addCityButton = new JButton("Add City");
    private final JButton solveButton = new JButton("Solve TSP");
    private final JButton clearCitiesButton = new JButton("Clear Cities");
    private final JButton nextStepButton = new JButton("Next Step");
    private final JButton prevStepButton = new JButton("Previous Step");
    private final JButton playPauseButton = new JButton("Play");

    public Ui() {
        setTitle("Traveling Salesman Problem");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupLayout();
        setupListeners();
    }

    private void setupLayout() {
        // Setup Tab Panel
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new FlowLayout());
        setupPanel.add(addRandomCityButton);
        setupPanel.add(addCityButton);
        setupPanel.add(clearCitiesButton);

        // Simulation Tab Panel
        JPanel simulationPanel = new JPanel();
        simulationPanel.setLayout(new FlowLayout());
        simulationPanel.add(prevStepButton);
        simulationPanel.add(nextStepButton);
        simulationPanel.add(playPauseButton);
        simulationPanel.add(solveButton);
        simulationPanel.add(solveOptions);

        // Add tabs
        tabbedPane.addTab("Setup", setupPanel);
        tabbedPane.addTab("Simulation", simulationPanel);

        // Add to main frame
        add(tabbedPane, BorderLayout.SOUTH);

        // Button states
        clearCitiesButton.setEnabled(false);
        addCityButton.setEnabled(false);
        toggleSimulationStatus(false);
    }


    private void setupListeners() {
        addRandomCityButton.addActionListener(e -> {
            JSlider slider = createSlider();

            int result = JOptionPane.showConfirmDialog(
                    null, slider, "Select number of cities",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                int value = slider.getValue();
                cities.clear();
                addRandomNodes(value, cities);
                addCityButton.setEnabled(true);
                clearCitiesButton.setEnabled(true);
                toggleSimulationStatus(true);
                repaint();
            } else {
                LoggerService.logMessage(LogType.INFO, "User cancelled adding cities");
            }
        });

        solveButton.addActionListener(e -> {
            if (cities.isEmpty()) {
                LoggerService.logMessage(LogType.WARN, "Cities are empty!");
                return;
            }

            Algorithm selected = (Algorithm) solveOptions.getSelectedItem();
            IGraphAlgorithm algo = selected.getInstance();
            pathSteps = computePath(cities, algo);

            currentStep = 0;
            path = pathSteps.get(currentStep);
            repaint();
        });

        addCityButton.addActionListener(e -> {
            addRandomNodes(1, cities);
            repaint();
        });

        clearCitiesButton.addActionListener(e -> {
            if (!cities.isEmpty()) {
                LoggerService.logMessage(LogType.INFO, cities.size() + " cities were removed");
                cities.clear();
                addCityButton.setEnabled(false);
                clearCitiesButton.setEnabled(false);
                toggleSimulationStatus(false);
                repaint();
            }
        });

        nextStepButton.addActionListener(e -> {
            if (pathSteps.isEmpty()) return;

            if (currentStep < pathSteps.size() - 1) {
                currentStep++;
                path = pathSteps.get(currentStep);
                repaint();
            }
        });

        prevStepButton.addActionListener(e -> {
            if (pathSteps.isEmpty()) return;

            if (currentStep > 0) {
                currentStep--;
                path = pathSteps.get(currentStep);
                repaint();
            }
        });

        playPauseButton.addActionListener(e -> {
            if (animationTimer == null) {
                animationTimer = new Timer(1000, ev -> {
                    if (currentStep < pathSteps.size() - 1) {
                        currentStep++;
                        path = pathSteps.get(currentStep);
                        repaint();
                    } else {
                        animationTimer.stop();
                        playPauseButton.setText("Play");
                    }
                });
            }

            if (animationTimer.isRunning()) {
                animationTimer.stop();
                playPauseButton.setText("Play");
            } else {
                animationTimer.start();
                playPauseButton.setText("Pause");
            }
        });

    }

    private JSlider createSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        return slider;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        int nodeSize = 10;
        int labelYOffset = 12;

        for (Node city : cities) {
            int x = (int) city.getX();
            int y = (int) city.getY();

            // Draw the node
            g2.fillOval(x, y, nodeSize, nodeSize);

            // Compute label position
            int labelY = y - labelYOffset;

            // If a label is above the frame, move it below the node
            if (labelY < 10) {
                labelY = y + nodeSize + labelYOffset;
            }

            g2.drawString(city.getName(), x, labelY);
        }

        if (path.size() > 1) {
            g2.setColor(Color.RED);
            for (int i = 0; i < path.size() - 1; i++) {
                Node a = path.get(i);
                Node b = path.get(i + 1);
                g2.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
            }

            // Connect last node to first to complete the tour
            Node first = path.get(0);
            Node last = path.get(path.size() - 1);
            g2.drawLine((int) last.getX(), (int) last.getY(), (int) first.getX(), (int) first.getY());
        }
    }

    private void toggleSimulationStatus(boolean newStatus){
        if(newStatus){
            tabbedPane.setEnabledAt(1, true);
            tabbedPane.setSelectedIndex(1);
        }
        else{
            tabbedPane.setEnabledAt(1, false);
        }


    }
}
