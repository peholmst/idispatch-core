package net.pkhapps.idispatch.core.auth.client.api.spi;

import net.pkhapps.idispatch.core.auth.client.api.AuthenticationClient;
import net.pkhapps.idispatch.core.client.support.api.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ServiceLoader;

/**
 * SPI for creating new {@link AuthenticationClient} instances. Implementations of this interface should register
 * themselves using the Java {@linkplain ServiceLoader service loader}.
 */
public interface AuthenticationClientFactory {

    /**
     * Fetches an implementation of the factory using the service loader. If there are more than one implementation
     * to choose from, the first one will be selected.
     *
     * @return the factory
     * @throws IllegalStateException if no implementations are available
     */
    static @NotNull AuthenticationClientFactory getInstance() {
        return ServiceLoader
                .load(AuthenticationClientFactory.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No AuthenticationClientFactory implementation available"));
    }

    /**
     * Creates a new {@link AuthenticationClient} instance.
     *
     * @param context a context containing the necessary information to create the client
     * @return a new instance of {@link AuthenticationClient}
     */
    @NotNull AuthenticationClient createAuthenticationClient(@NotNull Context context);
}
