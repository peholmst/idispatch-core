package net.pkhapps.idispatch.core.auth.client.api;

import org.jetbrains.annotations.NotNull;

import javax.security.auth.Subject;
import java.security.PrivilegedAction;
import java.time.Instant;

/**
 * Interface representing an authenticated user session. The session has an {@linkplain #expiresOn() expiration date}
 * that can be extended by {@linkplain #refresh() refreshing} the authentication token that is associated with the
 * session.
 *
 * @see #invalidate()
 * @see #refresh()
 */
public interface AuthenticatedSession {

    /**
     * The {@linkplain Subject} of the authenticated user. All iDispatch client operations should be
     * {@linkplain Subject#doAs(Subject, PrivilegedAction) performed} as this subject.
     */
    @NotNull Subject subject();

    /**
     * The principal of the authenticated user. This method is included here for developer convenience as you can
     * also retrieve the principal from the {@linkplain #subject() subject}.
     */
    @NotNull AuthenticatedPrincipal principal();

    /**
     * Invalidates the session and its associated authentication token.
     */
    void invalidate();

    /**
     * The date and time on which the session expires unless its associated authentication token is
     * {@linkplain #refresh() refreshed}.
     */
    @NotNull Instant expiresOn();

    /**
     * Refreshes the authentication token associated with this session, extending the session lifespan.
     *
     * @throws AuthenticationException if the token cannot be refreshed
     */
    void refresh();

    /**
     * Checks if the session is valid or not. A session is valid as long as it has not been explicitly
     * {@linkplain #invalidate() invalidated} and its {@linkplain #expiresOn() expiration date} is in the future.
     *
     * @return true if the session is valid, false if it is not
     */
    boolean isValid();
}
