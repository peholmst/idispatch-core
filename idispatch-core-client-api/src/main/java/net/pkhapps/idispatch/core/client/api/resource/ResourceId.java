package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.util.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * ID identifying a {@linkplain Resource resource}.
 */
public class ResourceId extends ObjectId {

    public ResourceId(@NotNull String uuid) {
        super(uuid);
    }
}
