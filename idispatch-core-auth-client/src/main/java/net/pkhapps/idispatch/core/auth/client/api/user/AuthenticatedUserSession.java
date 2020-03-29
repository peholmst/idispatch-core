package net.pkhapps.idispatch.core.auth.client.api.user;

import net.pkhapps.idispatch.core.auth.client.api.AuthenticatedSession;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * Extended version of {@link AuthenticatedSession} that provides additional operations for human users.
 */
public interface AuthenticatedUserSession extends AuthenticatedSession {

    /**
     * The date and time on which the user's password expires. After this, the user can authenticate but is not able
     * to perform any operations until the password has been {@linkplain #changeUserPassword(String, String) changed}.
     */
    @NotNull Instant userPasswordExpiresOn();

    /**
     * Changes the password of the current user.
     *
     * @param currentPassword the user's current password
     * @param newPassword     the user's new password
     * @throws net.pkhapps.idispatch.core.auth.client.api.InvalidCredentialsException if the current password was incorrect
     * @throws UnsuitablePasswordException                                            if the new password does not meet the requirements
     */
    void changeUserPassword(String currentPassword, String newPassword);
}
