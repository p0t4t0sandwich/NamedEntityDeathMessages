package dev.neuralnexus.namedentitydeathmessages.common.api;

import dev.neuralnexus.namedentitydeathmessages.common.NEDM;

/**
 * Template API Provider
 */
public class NEDMAPIProvider {
    private static NEDM instance = null;

    /**
     * Get the instance of Template
     * @return The instance of Template
     */
    public static NEDM get() {
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     * @param instance: The instance of Template
     */
    public static void register(NEDM instance) {
        NEDMAPIProvider.instance = instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     */
    public static void unregister() {
        NEDMAPIProvider.instance = null;
    }

    /**
     * Throw this exception when the API hasn't loaded yet, or you don't have the Template plugin installed.
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = "The API hasn't loaded yet, or you don't have the Template plugin installed.";

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}
