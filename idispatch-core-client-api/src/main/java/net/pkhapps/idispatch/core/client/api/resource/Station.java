package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.location.Location;
import net.pkhapps.idispatch.core.client.api.org.OrganizationId;
import net.pkhapps.idispatch.core.client.api.util.IdentifiableObject;
import net.pkhapps.idispatch.core.client.api.util.MultilingualString;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * A station is a place where resources are stationed when they are not on assignments. A station is owned or
 * operated by a specific organization.
 */
public class Station extends IdentifiableObject<StationId> {

    private final OrganizationId organization;
    private final MultilingualString name;
    private final Location location;

    public Station(@NotNull StationId id,
                   @NotNull OrganizationId organization,
                   @NotNull MultilingualString name,
                   @NotNull Location location) {
        super(id);
        this.organization = requireNonNull(organization);
        this.name = requireNonNull(name);
        this.location = requireNonNull(location);
    }

    /**
     * The organization that owns/operates the station.
     */
    public @NotNull OrganizationId getOrganization() {
        return organization;
    }

    /**
     * The name of the station.
     */
    public @NotNull MultilingualString getName() {
        return name;
    }

    /**
     * The location of the station.
     */
    public @NotNull Location getLocation() {
        return location;
    }
}
