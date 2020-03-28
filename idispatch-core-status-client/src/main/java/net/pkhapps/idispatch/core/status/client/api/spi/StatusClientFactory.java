package net.pkhapps.idispatch.core.status.client.api.spi;

import net.pkhapps.idispatch.core.client.support.Context;
import net.pkhapps.idispatch.core.status.client.api.StatusClient;
import org.jetbrains.annotations.NotNull;

import java.util.ServiceLoader;

/**
 * SPI for creating new {@link StatusClient} instances. Implementations of this interface should register themselves
 * using the Java {@linkplain ServiceLoader service loader}.
 */
public interface StatusClientFactory {

    /**
     * Fetches an implementation of the factory using the service loader. If there are more than one implementation
     * to choose from, the first one will be selected.
     *
     * @return the factory
     * @throws IllegalStateException if no implementations are available
     */
    static @NotNull StatusClientFactory getInstance() {
        return ServiceLoader
                .load(StatusClientFactory.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No StatusClientFactory implementation available"));
    }

    /**
     * Creates a new {@link StatusClient} instance.
     *
     * @param context a context containing the necessary information to create the client
     * @return a new instance of {@link StatusClient}
     */
    @NotNull StatusClient createStatusClient(@NotNull Context context);
}
