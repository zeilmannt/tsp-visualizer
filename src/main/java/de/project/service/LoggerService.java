/*
 * This file is part of tsp-visualizer.
 *
 * Copyright (c) 2025 Tom Zeilmann
 *
 * Licensed under the MIT License. See LICENSE file in the project root for details.
 */

package de.project.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * The {@code LoggerService} is responsible for printing logging messages to the console
 * using predefined logging levels.
 * <p>
 * Supported log levels:
 * <ul>
 *   <li><b>ERROR</b> – Indicates a serious failure.</li>
 *   <li><b>WARN</b> – Indicates a potential problem or unexpected situation.</li>
 *   <li><b>INFO</b> – Provides general runtime information.</li>
 *   <li><b>SUCCESS</b> – Operation completed successfully.</li>
 * </ul>
 */

public class LoggerService {
    public static void logMessage(LogType type, String msg) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
        String message = "[" + currentDateTime.format(formatter) + "] " + msg;

        switch (type) {
            case INFO:
                System.out.println("[INFO]" + message);
                break;
            case ERROR:
                System.err.println("[ERROR]" + message);
                break;
            case WARN:
                System.out.println("\u001B[33m[WARN]" + message + "\u001B[0m");
                break;
            case SUCCESS:
                System.out.println("\u001B[32m[SUCCESS]" + message + "\u001B[0m");
                break;
            default:
                System.err.println("[SYSTEM ERROR] Wrong LogType was used! (" + type + ")" );
                break;
        }
    }
}
