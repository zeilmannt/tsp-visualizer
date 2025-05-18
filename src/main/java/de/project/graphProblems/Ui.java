package de.project.graphProblems;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Ui extends JFrame {
    private List<Node> cities = new ArrayList<>();
    private List<Node> path = new ArrayList<>();
    private final String[] algorithms = {
            "Nearest Neighbor",
            "Ant Colony Optimization"
    };

    public Ui(){
        setTitle("Traveling Salesman Problem");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JButton addRandomCityButton = new JButton("Add Random Cities");
        JButton addCityButton = new JButton("Add City");
        JButton solveButton = new JButton("Solve TSP");
        JButton clearCitiesButton = new JButton("Clear cities");
        JComboBox solveOptions = new JComboBox(algorithms);
        JFrame infoTestFrame = new JFrame("Test");

        addCityButton.setEnabled(false);
        clearCitiesButton.setEnabled(false);

        buttonPanel.add(addRandomCityButton);
        buttonPanel.add(addCityButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(solveOptions);
        buttonPanel.add(clearCitiesButton);

        //infoPanel.add(infoTestFrame);

        add(buttonPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.EAST);

        addRandomCityButton.addActionListener(e -> {
            cities.clear();
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                cities.add(new Node("City" + i, rand.nextInt(700), rand.nextInt(500)));
            }
            System.out.println("-- 10 random cities were added to panel");
            addCityButton.setEnabled(true);
            clearCitiesButton.setEnabled(true);
            repaint();
        });

        solveButton.addActionListener(e -> {
            if (!cities.isEmpty()) {
                path = TSPAlgorithm.solveNearestNeighbor(cities);
                repaint();
            }
        });

        addCityButton.addActionListener(e -> {
            //addCityButton.setEnabled(true);
            //clearCitiesButton.setEnabled(true);
            revalidate();
            repaint();
        });

        clearCitiesButton.addActionListener(e -> {
            if (!cities.isEmpty()) {
                System.out.println("-- " + cities.size() + " cities were removed");
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
