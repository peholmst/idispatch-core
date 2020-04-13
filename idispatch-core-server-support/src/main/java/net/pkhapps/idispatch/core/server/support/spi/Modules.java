package net.pkhapps.idispatch.core.server.support.spi;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.ServiceLoader;

/**
 * TODO document me
 */
public final class Modules {

    private Modules() {
    }

    public static <M extends Module> @NotNull Optional<M> find(@NotNull Class<M> moduleClass) {
        return ServiceLoader
                .load(Module.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .filter(moduleClass::isInstance)
                .map(moduleClass::cast)
                .findFirst();
    }

    public static <M extends Module> @NotNull M require(@NotNull Class<M> moduleClass) {
        return find(moduleClass).orElseThrow(() -> new IllegalStateException("No such module: " + moduleClass));
    }

    @NotNull
    public static Iterable<Module> all() {
        return ServiceLoader.load(Module.class);
    }
}
