package net.pkhapps.idispatch.core.server.support.grpc.spi;

import io.grpc.BindableService;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * TODO Document me
 */
public interface GrpcModule {

    @NotNull Stream<BindableService> services();
}
