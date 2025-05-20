package de.project;

import de.project.ui.Ui;

public class Main {
    public static void main(String[] args){
        System.out.println("Running TSP...");
        javax.swing.SwingUtilities.invokeLater(() -> new Ui().setVisible(true));
    }
}
