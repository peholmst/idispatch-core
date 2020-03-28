package net.pkhapps.idispatch.core.status.client.api;

/**
 * Status that can be set by the user.
 */
public interface UserAssignableStatus extends Status {

    /**
     * Sends this status to the status service. If the service accepts the status change,
     * this status will be become the {@linkplain StatusClient#currentStatus() current status} of the resource.
     * If the service does not accept the status change, nothing happens.
     */
    void send();
}
