package net.mcreator.wildernessoddesyapi;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MixinErrorLogger {
    public static final Logger LOGGER = LogManager.getLogger(MixinErrorLogger.class);

    public static void logError(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }

    public static void logInfo(String message) {
        LOGGER.info(message);
    }
}
