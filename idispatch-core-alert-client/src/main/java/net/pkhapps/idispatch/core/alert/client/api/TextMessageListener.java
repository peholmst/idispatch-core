package net.pkhapps.idispatch.core.alert.client.api;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for a listener that receives {@link TextMessage}s.
 */
@FunctionalInterface
public interface TextMessageListener {

    /**
     * Called when a text message has been received.
     *
     * @param textMessage the text message
     */
    void onTextMessage(@NotNull TextMessage textMessage);
}
