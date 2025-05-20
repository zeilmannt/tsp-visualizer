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
 *   <li><b>SEVERE</b> – Indicates a serious failure.</li>
 *   <li><b>WARNING</b> – Indicates a potential problem or unexpected situation.</li>
 *   <li><b>INFO</b> – Provides general runtime information.</li>
 *   <li><b>CONFIG</b> – Used for configuration-related messages.</li>
 *   <li><b>FINE</b> – Provides detailed tracing information.</li>
 *   <li><b>FINER</b> – More detailed tracing than FINE.</li>
 *   <li><b>FINEST</b> – The most detailed tracing level available.</li>
 *   <li><b>ALL</b> – Enables logging of all messages.</li>
 *   <li><b>OFF</b> – Disables logging entirely.</li>
 * </ul>
 */

public class LoggerService {
    private static final Logger logger = Logger.getLogger("Logger");

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
