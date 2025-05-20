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

import static de.project.model.impl.NodeHandler.*;

public class Ui extends JFrame {
    private final List<Node> cities = new ArrayList<>();
    private List<Node> path = new ArrayList<>();

    // UI Components
    private final JComboBox<Algorithm> solveOptions = new JComboBox<>(Algorithm.values());
    private final JButton addRandomCityButton = new JButton("Add Random Cities");
    private final JButton addCityButton = new JButton("Add City");
    private final JButton solveButton = new JButton("Solve TSP");
    private final JButton clearCitiesButton = new JButton("Clear Cities");

    public Ui() {
        setTitle("Traveling Salesman Problem");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupLayout();
        setupListeners();
    }

    private void setupLayout() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addRandomCityButton);
        buttonPanel.add(addCityButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(solveOptions);
        buttonPanel.add(clearCitiesButton);

        add(buttonPanel, BorderLayout.SOUTH);
        clearCitiesButton.setEnabled(false);
        addCityButton.setEnabled(false);
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
            IGraphAlgorithm algo = selected.getInstance(); // assumes getInstance returns impl
            path = computePath(cities, algo);
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
                repaint();
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

        for (Node city : cities) {
            g2.fillOval((int) city.getX(), (int) city.getY(), 10, 10);
            g2.drawString(city.getName(), (int) city.getX(), (int) city.getY() - 5);
        }

        if (path.size() > 1) {
            g2.setColor(Color.RED);
            for (int i = 0; i < path.size() - 1; i++) {
                Node a = path.get(i);
                Node b = path.get(i + 1);
                g2.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
            }

            // connect last to first to complete the tour
            Node first = path.get(0);
            Node last = path.get(path.size() - 1);
            g2.drawLine((int) last.getX(), (int) last.getY(), (int) first.getX(), (int) first.getY());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ui().setVisible(true));
    }
}
