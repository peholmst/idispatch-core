package net.pkhapps.idispatch.core.client.api.assignment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Every {@linkplain Assignment assignment} has a priority that indicates how rapidly the resources need to respond to
 * the scene of the incident.
 */
public class AssignmentPriority implements Comparable<AssignmentPriority> {

    private final String priority;

    public AssignmentPriority(@NotNull String priority) {
        this.priority = requireNonNull(priority);
    }

    @Override
    public String toString() {
        return priority;
    }

    @Override
    public int compareTo(@NotNull AssignmentPriority o) {
        return priority.compareTo(o.priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentPriority that = (AssignmentPriority) o;
        return priority.equals(that.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority);
    }
}
