package dev.neuralnexus.namedentitydeathmessages.common;

/**
 * The NamedEntityDeathMessages plugin interface.
 */
public interface NEDMPlugin {
    /**
     * Gets the logger.
     */
    Object pluginLogger();

    /**
     * Gets the config path.
     */
    String pluginConfigPath();

    /**
     * Use whatever logger is being used.
     * @param message The message to log
     */
    default void useLogger(String message) {
        Object logger = pluginLogger();

        if (logger instanceof java.util.logging.Logger) {
            ((java.util.logging.Logger) logger).info(message);
        } else if (logger instanceof org.slf4j.Logger) {
            ((org.slf4j.Logger) logger).info(message);
        } else {
            System.out.println(message);
        }
    }

    /**
     * Gets the server type.
     * @return The server type
     */
    default String getServerType() {
        return "unknown";
    }

    /**
     * Register hooks.
     */
    void registerHooks();


    /**
     * Registers event listeners.
     */
    void registerEventListeners();

    /**
     * Registers commands.
     */
    void registerCommands();

    /**
     * Starts the NamedEntityDeathMessages plugin.
     */
    default void pluginStart() {
        try {
            useLogger("[NamedEntityDeathMessages] NamedEntityDeathMessages is running on " + getServerType() + "!");

            // Start the NamedEntityDeathMessages
            NEDM.start(pluginLogger());

            // Register hooks
            registerHooks();

            // Register event listeners
            registerEventListeners();

            // Register commands
            registerCommands();

            useLogger("[NamedEntityDeathMessages] NamedEntityDeathMessages has been enabled!");

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Stops the NamedEntityDeathMessages plugin.
     */
    default void pluginStop() {
        try {
            NEDM.stop();
            useLogger("[NamedEntityDeathMessages] NamedEntityDeathMessages has been disabled!");
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
