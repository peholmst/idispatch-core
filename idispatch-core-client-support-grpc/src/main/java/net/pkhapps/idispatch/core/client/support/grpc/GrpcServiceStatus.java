package net.pkhapps.idispatch.core.client.support.grpc;

import net.pkhapps.idispatch.core.client.support.api.ChangeListener;
import net.pkhapps.idispatch.core.client.support.api.Registration;
import net.pkhapps.idispatch.core.client.support.api.ServiceStatus;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Implement me!
 */
public class GrpcServiceStatus implements ServiceStatus {

    @Override
    public @NotNull State state() {
        return null;
    }

    @Override
    public @NotNull Registration addStateChangeListener(@NotNull ChangeListener<State> stateChangeListener) {
        return null;
    }
}
