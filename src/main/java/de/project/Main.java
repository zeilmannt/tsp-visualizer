/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

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
