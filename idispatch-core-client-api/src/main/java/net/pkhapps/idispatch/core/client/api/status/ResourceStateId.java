package net.pkhapps.idispatch.core.client.api.status;

import net.pkhapps.idispatch.core.client.api.util.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * ID identifying a {@link ResourceState}.
 */
public class ResourceStateId extends ObjectId {

    public ResourceStateId(@NotNull String uuid) {
        super(uuid);
    }
}
