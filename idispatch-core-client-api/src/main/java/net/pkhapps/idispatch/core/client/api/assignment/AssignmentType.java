package net.pkhapps.idispatch.core.client.api.assignment;

import net.pkhapps.idispatch.core.client.api.util.IdentifiableObject;
import net.pkhapps.idispatch.core.client.api.util.MultilingualString;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;

/**
 * Every {@linkplain Assignment assignment} has an assignment type that gives a general idea of what the assignment is
 * about and what kind of resources are going to be needed there.
 */
public class AssignmentType extends IdentifiableObject<AssignmentTypeId> {

    private final String code;
    private final MultilingualString name;
    private final SortedSet<AssignmentPriority> applicablePriorities;

    public AssignmentType(@NotNull AssignmentTypeId id,
                          @NotNull String code,
                          @NotNull MultilingualString name,
                          @NotNull Collection<AssignmentPriority> applicablePriorities) {
        super(id);
        this.code = requireNonNull(code);
        this.name = requireNonNull(name);
        if (applicablePriorities.isEmpty()) {
            throw new IllegalArgumentException("applicablePriorities cannot be empty");
        }
        this.applicablePriorities = Collections.synchronizedSortedSet(new TreeSet<>(applicablePriorities));
    }

    /**
     * The code of the assignment type. This code can be used by humans to uniquely identify an assignment type.
     */
    public @NotNull String getCode() {
        return code;
    }

    /**
     * The name of the assignment type.
     */
    public @NotNull MultilingualString getName() {
        return name;
    }

    /**
     * The priorities that are applicable to assignments of this type (never empty).
     */
    public @NotNull SortedSet<AssignmentPriority> getApplicablePriorities() {
        return applicablePriorities;
    }
}
