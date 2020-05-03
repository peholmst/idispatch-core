package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Holder class for retrieving the current {@link DomainContext}. Clients should typically access it through
 * {@link DomainContext#current()}.
 */
public final class DomainContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainContextHolder.class);
    private static volatile DomainContext INSTANCE;

    private DomainContextHolder() {
    }

    /**
     * Returns the current domain context. If no context has been set, a {@linkplain DefaultDomainContext default}
     * context is created.
     *
     * @return the current domain context
     */
    public static @NotNull DomainContext getCurrent() {
        if (INSTANCE == null) {
            synchronized (DomainContextHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DefaultDomainContext();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Sets the current domain context. This method is intended for testing only or for cases where a context
     * other than the default one is needed.
     *
     * @param domainContext the domain context to set
     */
    public static void setCurrent(@NotNull DomainContext domainContext) {
        requireNonNull(domainContext);
        synchronized (DomainContextHolder.class) {
            if (INSTANCE != null) {
                LOGGER.warn("Replacing DomainContext [{}] with [{}]", INSTANCE, domainContext);
            }
            INSTANCE = domainContext;
        }
    }
}
