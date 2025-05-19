package de.project.graphProblems;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static de.project.graphProblems.NodeHandler.*;

public class Ui extends JFrame {
    private List<Node> cities = new ArrayList<>();
    private List<Node> path = new ArrayList<>();


    public Ui(){
        setTitle("Traveling Salesman Problem");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // All JavaSwing objects
        JPanel buttonPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JButton addRandomCityButton = new JButton("Add Random Cities");
        JButton addCityButton = new JButton("Add City");
        JButton solveButton = new JButton("Solve TSP");
        JButton clearCitiesButton = new JButton("Clear cities");

        Algorithm[] algorithms = {
                Algorithm.NEAREST_NEIGHBOR,
                Algorithm.ANT_COLONY
        };

        JComboBox solveOptions = new JComboBox(algorithms);

        // Pop up slider for number of city inputs
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5); // min=1, max=10, initial=5
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);

        JFrame infoTestFrame = new JFrame("Test");

        // Set button to add one city and to clear all cities disabled
        addCityButton.setEnabled(false);
        clearCitiesButton.setEnabled(false);

        // Add objects to buttons panel
        buttonPanel.add(addRandomCityButton);
        buttonPanel.add(addCityButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(solveOptions);
        buttonPanel.add(clearCitiesButton);

        //infoPanel.add(infoTestFrame);

        add(buttonPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.EAST);

        // Action listeners for buttons
        addRandomCityButton.addActionListener(e -> {
            cities.clear();

            // Open pop up for number of random cities as input
            int result = JOptionPane.showConfirmDialog(
                    null,
                    slider,
                    "Select number of cities",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            // Add new city to frame
            if (result == JOptionPane.OK_OPTION) {
                int value = slider.getValue();
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

            path = startTspSolver(cities, (Algorithm) solveOptions.getSelectedItem());
            repaint();
        });

        addCityButton.addActionListener(e -> {
            addRandomNode(cities);
            addCityButton.setEnabled(true);
            clearCitiesButton.setEnabled(true);
            revalidate();
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
            // Connect last to first
            Node first = path.get(0);
            Node last = path.get(path.size() - 1);
            g2.drawLine((int) last.getX(), (int) last.getY(), (int) first.getX(), (int) first.getY());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ui().setVisible(true));
    }
}
