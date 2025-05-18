package de.project.graphProblems;

import java.time.Instant;
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
        Instant timestamp = Instant.now();
        String message = "[" + timestamp + "] " + msg;

        switch (type) {
            case SEVERE:
                logger.severe(message);
                break;
            case WARNING:
                logger.warning(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case CONFIG:
                logger.config(message);
                break;
            case FINE:
                logger.fine(message);
                break;
            case FINER:
                logger.finer(message);
                break;
            case FINEST:
                logger.finest(message);
                break;
            case ALL:
                logger.log(java.util.logging.Level.ALL, message);
                break;
            case OFF:
                // OFF means no logging
                break;
            default:
                logger.warning("[UNRECOGNIZED LEVEL] " + message);
                break;
        }
    }
}
