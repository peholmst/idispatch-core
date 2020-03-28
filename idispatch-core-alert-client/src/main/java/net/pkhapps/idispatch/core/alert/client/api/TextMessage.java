package net.pkhapps.idispatch.core.alert.client.api;

import org.jetbrains.annotations.NotNull;

/**
 * Interface defining a text message. Text messages are used to send out important information to resources without
 * necessarily dispatching them to an assignment.
 */
public interface TextMessage extends Message {

    /**
     * The message body.
     */
    @NotNull String body();
}
