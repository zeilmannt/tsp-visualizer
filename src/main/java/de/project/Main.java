package de.project;

import de.project.service.LogType;
import de.project.service.LoggerService;
import de.project.ui.Ui;

public class Main {
    public static void main(String[] args){
        LoggerService.logMessage(LogType.INFO, "Application started...");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LoggerService.logMessage(LogType.INFO, "Application is closing...");
        }));

        javax.swing.SwingUtilities.invokeLater(() -> new Ui().setVisible(true));
    }
}
