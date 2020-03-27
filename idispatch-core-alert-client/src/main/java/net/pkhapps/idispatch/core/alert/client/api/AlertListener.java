package net.pkhapps.idispatch.core.alert.client.api;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for a listener that receives {@link Alert}s.
 */
@FunctionalInterface
public interface AlertListener {

    /**
     * Called when an alert has been received.
     *
     * @param alert the alert
     */
    void onAlert(@NotNull Alert alert);
}
