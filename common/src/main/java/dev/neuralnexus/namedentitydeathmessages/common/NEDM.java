package dev.neuralnexus.namedentitydeathmessages.common;

import dev.neuralnexus.namedentitydeathmessages.common.api.NEDMAPIProvider;

public class NEDM {
    /**
     * Properties of the Template class.
     * instance: The singleton instance of the Template class
     * config: The config file
     * logger: The logger
     * STARTED: Whether the PanelServerManager has been started
     */
    private static final NEDM instance = new NEDM();
    private static Object logger;
    private static boolean STARTED = false;

    /**
     * Constructor for the Template class.
     */
    public NEDM() {}

    /**
     * Getter for the singleton instance of the Template class.
     * @return The singleton instance
     */
    public static NEDM getInstance() {
        return instance;
    }

    /**
     * Use whatever logger is being used.
     * @param message The message to log
     */
    public static void useLogger(String message) {
        if (logger instanceof java.util.logging.Logger) {
            ((java.util.logging.Logger) logger).info(message);
        } else if (logger instanceof org.slf4j.Logger) {
            ((org.slf4j.Logger) logger).info(message);
        } else {
            System.out.println(message);
        }
    }

    /**
     * Start NamedEntityDeathMessages
     * @param logger The logger
     */
    public static void start(Object logger) {
        NEDM.logger = logger;

        if (STARTED) {
            useLogger("NamedEntityDeathMessages has already started!");
            return;
        }
        STARTED = true;

        useLogger("NamedEntityDeathMessages has been started!");
        NEDMAPIProvider.register(instance);
    }

    /**
     * Start NamedEntityDeathMessages
     */
    public static void start() {
        start(logger);
    }

    /**
     * Stop NamedEntityDeathMessages
     */
    public static void stop() {
        if (!STARTED) {
            useLogger("NamedEntityDeathMessages has already stopped!");
            return;
        }
        STARTED = false;

        useLogger("NamedEntityDeathMessages has been stopped!");
        NEDMAPIProvider.unregister();
    }
}
