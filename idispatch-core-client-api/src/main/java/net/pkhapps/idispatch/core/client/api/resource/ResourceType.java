package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.util.IdentifiableObject;
import net.pkhapps.idispatch.core.client.api.util.MultilingualString;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * A resource type is used to describe a {@linkplain Resource resource}. A real-world dispatch system such as ERICA
 * would not use types but instead assign different qualities or capabilities to a certain resource. However, iDispatch
 * is not a real-world system and so using a single type to describe a resource is more than enough. This type could be
 * e.g. "pumper", "water tender", "command", "ladder", "basic life support ambulance","advanced life support ambulance",
 * etc.
 */
public class ResourceType extends IdentifiableObject<ResourceTypeId> {

    private final MultilingualString name;

    public ResourceType(@NotNull ResourceTypeId id,
                        @NotNull MultilingualString name) {
        super(id);
        this.name = requireNonNull(name);
    }

    /**
     * The name of the resource type.
     */
    public @NotNull MultilingualString getName() {
        return name;
    }
}
