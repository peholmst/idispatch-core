package net.pkhapps.idispatch.core.client.api.org;

import net.pkhapps.idispatch.core.client.api.util.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * ID identifying an organization that uses iDispatch Core.
 */
public final class OrganizationId extends ObjectId {

    public OrganizationId(@NotNull String uuid) {
        super(uuid);
    }
}
