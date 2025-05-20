package de.project.ui;

import de.project.algorithm.impl.Algorithm; // Assuming enum maps to actual impls
import de.project.algorithm.interfaces.IGraphAlgorithm;
import de.project.model.impl.Edge;
import de.project.model.impl.Node;
import de.project.service.LogType;
import de.project.service.LoggerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static de.project.model.impl.NodeHandler.*;

public class Ui extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private final List<Node> cities = new ArrayList<>();
    private List<Node> path = new ArrayList<>();
    private List<List<Node>> pathSteps = new ArrayList<>();
    private int currentStep = 0;
    private Timer animationTimer;
    private final List<Edge> edges = new ArrayList<>();
    private GraphPanel graphPanel;

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
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        graphPanel = new GraphPanel(cities, edges, path);
        add(graphPanel, BorderLayout.CENTER);

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

        // Setup for combo box
        solveOptions.setToolTipText("Choose a TSP Algorithm");
        solveOptions.setSelectedIndex(0);

        // Add tabs
        tabbedPane.addTab("Setup", setupPanel);
        tabbedPane.addTab("Simulation", simulationPanel);

        // Add to the main frame
        add(tabbedPane, BorderLayout.SOUTH);

        // Button states
        clearCitiesButton.setEnabled(false);
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
                //cities.clear();
                addRandomNodes(value, cities);
                updateClearButtonState();
                addCityButton.setEnabled(true);
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

            if (selected == null || selected.getInstance() == null) {
                LoggerService.logMessage(LogType.ERROR, "Algorithm instance is not available.");
                return;
            }

            IGraphAlgorithm algo = selected.getInstance();
            pathSteps = computePath(cities, algo);

            currentStep = 0;
            path = pathSteps.get(currentStep);
            graphPanel.setPath(path);
            graphPanel.repaint();
        });

        addCityButton.addActionListener(e -> {
            toggleManualAddMode();
        });

        clearCitiesButton.addActionListener(e -> {
            if (!cities.isEmpty()) {
                LoggerService.logMessage(LogType.INFO, cities.size() + " cities were removed");
                cities.clear();
                addCityButton.setEnabled(true);
                updateClearButtonState();
                toggleSimulationStatus(false);
                graphPanel.repaint();
            }
        });

        nextStepButton.addActionListener(e -> {
            if (pathSteps.isEmpty()) return;

            if (currentStep < pathSteps.size() - 1) {
                currentStep++;
                path = pathSteps.get(currentStep);
                graphPanel.setPath(path);
                graphPanel.repaint();
            }
        });

        prevStepButton.addActionListener(e -> {
            if (pathSteps.isEmpty()) return;

            if (currentStep > 0) {
                currentStep--;
                path = pathSteps.get(currentStep);
                graphPanel.setPath(path);
                graphPanel.repaint();
            }
        });

        playPauseButton.addActionListener(e -> {
            if (animationTimer == null) {
                animationTimer = new Timer(1000, ev -> {
                    if (currentStep < pathSteps.size() - 1) {
                        currentStep++;
                        path = pathSteps.get(currentStep);
                        graphPanel.setPath(path);
                        graphPanel.repaint();
                    } else {
                        animationTimer.stop();
                        playPauseButton.setText("Play");
                    }
                });
            }

            if (animationTimer == null || !animationTimer.isRunning()) {
                startAnimation();
                playPauseButton.setText("Pause");
            } else {
                animationTimer.stop();
                playPauseButton.setText("Play");
            }
        });
    }

    private void startAnimation() {
        animationTimer = new Timer(1000, ev -> {
            if (currentStep < pathSteps.size() - 1) {
                currentStep++;
                path = pathSteps.get(currentStep);
                graphPanel.setPath(path);
                graphPanel.repaint();
            } else {
                animationTimer.stop();
                playPauseButton.setText("Play");
            }
        });
        animationTimer.start();
    }

    private void toggleManualAddMode() {
        boolean newMode = !graphPanel.isAddMode();
        graphPanel.setAddMode(newMode);

        if (newMode) {
            // Disable all buttons except addCityButton and disable Simulation tab
            addRandomCityButton.setEnabled(false);
            clearCitiesButton.setEnabled(false);
            solveButton.setEnabled(false);
            nextStepButton.setEnabled(false);
            prevStepButton.setEnabled(false);
            playPauseButton.setEnabled(false);
            solveOptions.setEnabled(false);
            tabbedPane.setEnabledAt(1, false);  // disable Simulation tab
            tabbedPane.setEnabledAt(0, true);   // keep Setup tab enabled to access addCityButton

            // Keep addCityButton enabled so user can toggle off manual mode
            addCityButton.setEnabled(true);

            // Optionally, change addCityButton text to indicate "Stop Adding"
            addCityButton.setText("Stop Adding");
        } else {
            // Re-enable all buttons and simulation tab
            addRandomCityButton.setEnabled(true);
            clearCitiesButton.setEnabled(!cities.isEmpty());
            solveButton.setEnabled(true);
            nextStepButton.setEnabled(true);
            prevStepButton.setEnabled(true);
            playPauseButton.setEnabled(true);
            solveOptions.setEnabled(true);
            tabbedPane.setEnabledAt(1, true);  // enable Simulation tab

            // Reset addCityButton text
            addCityButton.setText("Add City");
        }
    }


    private void toggleButtonStatusForManually(boolean b) {
        tabbedPane.setEnabled(b);
        addRandomCityButton.setEnabled(b);
        clearCitiesButton.setEnabled(b);
    }

    private JSlider createSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        return slider;
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

    private void updateClearButtonState() {
        clearCitiesButton.setEnabled(!cities.isEmpty());
    }
}
