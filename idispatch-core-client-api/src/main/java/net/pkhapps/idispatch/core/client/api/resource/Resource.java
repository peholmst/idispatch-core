package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.org.OrganizationId;
import net.pkhapps.idispatch.core.client.api.util.IdentifiableObject;
import org.jetbrains.annotations.NotNull;

/**
 * A resource is something (typically a vehicle with a crew) that can be dispatched to deal with incidents.
 */
public class Resource extends IdentifiableObject<ResourceId> {

    private final OrganizationId organization;
    private final StationId station;
    private final String callSign;
    private final ResourceType type;

    public Resource(@NotNull ResourceId id,
                    @NotNull OrganizationId organization,
                    @NotNull StationId station,
                    @NotNull String callSign,
                    @NotNull ResourceType type) {
        super(id);
        this.organization = organization;
        this.station = station;
        this.callSign = callSign;
        this.type = type;
    }

    /**
     * The organization that owns/operates the resource.
     */
    public @NotNull OrganizationId getOrganization() {
        return organization;
    }

    /**
     * The station where the resource is normally stationed.
     */
    public @NotNull StationId getStation() {
        return station;
    }

    /**
     * The call sign of the resource. This can be used by humans to uniquely identify the resource.
     */
    public @NotNull String getCallSign() {
        return callSign;
    }

    /**
     * The type of the resource.
     */
    public @NotNull ResourceType getType() {
        return type;
    }
}
