package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 *
 * @param <IdT>
 */
public interface IdentifiableDomainObject<IdT extends DomainObjectId> extends DomainObject {

    /**
     * @return
     */
    @NotNull IdT id();
}
