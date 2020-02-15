package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.util.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * ID identifying a {@linkplain Station station}.
 */
public class StationId extends ObjectId {

    public StationId(@NotNull String uuid) {
        super(uuid);
    }
}
