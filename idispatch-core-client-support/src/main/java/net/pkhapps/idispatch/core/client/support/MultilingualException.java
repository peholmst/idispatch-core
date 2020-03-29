package net.pkhapps.idispatch.core.client.support;

import org.jetbrains.annotations.NotNull;

/**
 * Base class for exceptions that provide the error message in multiple languages.
 */
public abstract class MultilingualException extends RuntimeException {

    private final MultilingualString message;

    public MultilingualException(@NotNull MultilingualString message) {
        super(message.defaultValue());
        this.message = message;
    }

    /**
     * The multilingual error message.
     */
    public @NotNull MultilingualString message() {
        return message;
    }
}
