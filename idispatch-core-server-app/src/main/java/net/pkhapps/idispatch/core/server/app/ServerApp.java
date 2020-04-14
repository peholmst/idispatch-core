package net.pkhapps.idispatch.core.server.app;

import io.grpc.ServerBuilder;
import net.pkhapps.idispatch.core.server.support.grpc.ExceptionHandlingInterceptor;
import net.pkhapps.idispatch.core.server.support.grpc.spi.GrpcModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

/**
 * TODO Document me
 */
public class ServerApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) throws Exception {
        // TODO Configure server
        var serverBuilder = ServerBuilder
                .forPort(8080)
                .intercept(ExceptionHandlingInterceptor.INSTANCE);
        for (var grpcModule : ServiceLoader.load(GrpcModule.class)) {
            LOGGER.info("Loading gRPC module [{}]", grpcModule);
            grpcModule.services().forEach(serverBuilder::addService);
        }
        var server = serverBuilder.build();
        LOGGER.info("Starting server");
        server.start();
        LOGGER.info("Server started on port {}", server.getPort());
        server.awaitTermination();
        LOGGER.info("Server stopped");
    }
}
