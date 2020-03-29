package net.pkhapps.idispatch.core.alert.client.api.spi;

import net.pkhapps.idispatch.core.alert.client.api.AlertClient;
import net.pkhapps.idispatch.core.client.support.api.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ServiceLoader;

/**
 * SPI for creating new {@link AlertClient} instances. Implementations of this interface should register themselves
 * using the Java {@linkplain ServiceLoader service loader}.
 */
public interface AlertClientFactory {

    /**
     * Fetches an implementation of the factory using the service loader. If there are more than one implementation
     * to choose from, the first one will be selected.
     *
     * @return the factory
     * @throws IllegalStateException if no implementations are available
     */
    static @NotNull AlertClientFactory getInstance() {
        return ServiceLoader
                .load(AlertClientFactory.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No AlertClientFactory implementation available"));
    }

    /**
     * Creates a new {@link AlertClient} instance.
     *
     * @param context a context containing the necessary information to create the client
     * @return a new instance of {@link AlertClient}
     */
    @NotNull AlertClient createAlertClient(@NotNull Context context);
}
