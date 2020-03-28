package net.pkhapps.idispatch.core.auth.client.api.user;

import net.pkhapps.idispatch.core.auth.client.api.AuthenticationRequest;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * Request for authenticating human users.
 */
public class UserAuthenticationRequest implements AuthenticationRequest<UserAuthenticationProcess> {

    private final String tenantId;
    private final String username;

    /**
     * Creates a new user authentication request for the given tenant and user.
     *
     * @param tenantId the tenant ID
     * @param username the username
     */
    public UserAuthenticationRequest(@NotNull String tenantId, @NotNull String username) {
        this.tenantId = requireNonNull(tenantId);
        this.username = requireNonNull(username);
    }

    @Override
    public @NotNull String tenantId() {
        return tenantId;
    }

    /**
     * The username of the user that wants to authenticate.
     */
    public @NotNull String username() {
        return username;
    }
}
