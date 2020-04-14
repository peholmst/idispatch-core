package net.pkhapps.idispatch.core.admin.client.api.user;

import net.pkhapps.idispatch.core.client.support.api.HasListing;
import net.pkhapps.idispatch.core.client.support.api.HasLookupById;
import net.pkhapps.idispatch.core.client.support.api.ObjectId;
import net.pkhapps.idispatch.core.client.support.api.ServiceClient;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 */
public interface UserAdminClient extends ServiceClient, HasListing<User, UserFilter>, HasLookupById<User, ObjectId> {

    @NotNull User createUser();
}
