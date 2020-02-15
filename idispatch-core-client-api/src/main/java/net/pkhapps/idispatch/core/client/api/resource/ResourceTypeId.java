package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.util.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * ID identifying a {@linkplain ResourceType resource type}.
 */
public class ResourceTypeId extends ObjectId {

    public ResourceTypeId(@NotNull String uuid) {
        super(uuid);
    }
}
