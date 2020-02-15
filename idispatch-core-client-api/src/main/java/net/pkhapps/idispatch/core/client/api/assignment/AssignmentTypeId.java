package net.pkhapps.idispatch.core.client.api.assignment;

import net.pkhapps.idispatch.core.client.api.util.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * ID identifying an {@linkplain AssignmentType assignment type}.
 */
public class AssignmentTypeId extends ObjectId {

    public AssignmentTypeId(@NotNull String uuid) {
        super(uuid);
    }
}
