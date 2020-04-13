package net.pkhapps.idispatch.core.server.support.security;

import org.jetbrains.annotations.NotNull;

import javax.security.auth.Subject;
import java.security.AccessController;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * TODO document me
 */
public final class Security {

    private Security() {
    }

    /**
     * @return
     */
    public static @NotNull Optional<AuthenticatedPrincipal> optionalPrincipal() {
        return Optional.ofNullable(Subject.getSubject(AccessController.getContext()))
                .flatMap(subject -> subject.getPrincipals(AuthenticatedPrincipal.class).stream().findAny());
    }

    /**
     * @return
     */
    public static @NotNull AuthenticatedPrincipal principal() {
        return optionalPrincipal().orElseThrow(AuthenticationRequiredException::new);
    }

    /**
     * @param authorities
     */
    public static void requireAny(@NotNull GrantedAuthority... authorities) {
        if (Stream.of(authorities).noneMatch(principal()::hasAuthority)) {
            throw new AccessDeniedException();
        }
    }

    /**
     * @param authorities
     */
    public static void requireAll(@NotNull GrantedAuthority... authorities) {
        if (!Stream.of(authorities).allMatch(principal()::hasAuthority)) {
            throw new AccessDeniedException();
        }
    }
}
