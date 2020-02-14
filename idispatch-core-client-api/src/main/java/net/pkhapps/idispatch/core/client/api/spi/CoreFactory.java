package net.pkhapps.idispatch.core.client.api.spi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Properties;
import java.util.ServiceLoader;

/**
 * SPI for creating new {@link Core} instances. Implementations of this interface should register themselves using the
 * Java {@linkplain ServiceLoader service loader}.
 */
public interface CoreFactory {

    /**
     * Fetches an implementation of {@link CoreFactory} using the service loader. If there are more than one
     * implementation to choose from, the first one will be selected.
     *
     * @return the {@link CoreFactory}
     * @throws IllegalStateException if no implementations are available
     */
    static @NotNull CoreFactory getInstance() {
        return ServiceLoader.load(CoreFactory.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No CoreFactory implementation available"));
    }

    /**
     * Creates a new {@link Core} instance, optionally passing configuration properties to the factory. The contents
     * of these properties are defined by the implementation.
     *
     * @param properties any configuration properties
     * @return a new instance of {@link Core}
     * @throws Exception if something went wrong while creating the instance
     */
    @NotNull CoreFactory createCore(@Nullable Properties properties) throws Exception;
}
