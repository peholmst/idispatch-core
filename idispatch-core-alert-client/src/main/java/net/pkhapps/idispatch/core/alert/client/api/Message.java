package net.pkhapps.idispatch.core.alert.client.api;

import net.pkhapps.idispatch.core.client.support.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * Interface defining a message that is sent by the alert service to the client.
 */
public interface Message {

    /**
     * The ID of the message. Each message hat has been sent out has its own ID.
     */
    @NotNull ObjectId messageId();

    /**
     * The date and time on which the message was sent.
     */
    @NotNull Instant sentOn();

    /**
     * The date and time on which the alert was received by the client.
     */
    @NotNull Instant receivedOn();

    /**
     * Acknowledges that the message has been received, read and understood.
     */
    void acknowledge();

    /**
     * The name of the sender of the message.
     */
    @NotNull String sender();
}
