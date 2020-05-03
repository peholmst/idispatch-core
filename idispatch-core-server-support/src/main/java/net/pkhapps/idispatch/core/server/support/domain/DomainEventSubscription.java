package net.pkhapps.idispatch.core.server.support.domain;

/**
 * Handle interface for domain event subscriptions. The primary purpose of this interface is to make it possible
 * to cancel the subscription when no longer needed, thereby releasing system resources.
 *
 * @see DomainEventPublisher#subscribe(Class, DomainEventSubscriber)
 * @see DomainEventSubscriber
 */
public interface DomainEventSubscription {

    /**
     * Cancels the subscription. After this, the subscriber will not receive any domain events at all.
     */
    void cancel();
}
