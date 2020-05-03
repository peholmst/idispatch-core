package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

/**
 * Interface defining an envelop that contains a {@linkplain DomainEvent domain event}. The envelope contains additional
 * metadata about the event and a method for {@linkplain #acknowledge() acknowledging} that the event has been properly
 * received and processed.
 *
 * @param <EventT> the type of the event contained in the envelope
 */
public interface DomainEventEnvelope<EventT extends DomainEvent> {

    /**
     * Acknowledges that the event has been received and processed. If an event is not acknowledged, the event publisher
     * will try to send the event again until the event expires or the event is acknowledged.
     */
    void acknowledge();

    /**
     * Returns the domain event contained in the envelope.
     *
     * @return the domain event
     */
    @NotNull EventT payload();

    /**
     * Returns an ID that can be used to uniquely identify this event. This may be useful when implementing idempotent
     * event subscribers.
     *
     * @return the domain event ID
     */
    @NotNull DomainEventId eventId();

    /**
     * Returns the timestamp of when the event was originally published.
     *
     * @return the domain event timestamp
     */
    @NotNull Instant eventTimestamp();

    /**
     * Returns the duration that the domain event remains alive. After this, the event is considered stale and will no
     * longer be published to subscribers that have failed to acknowledge it.
     *
     * @return the domain event time to live
     * @see #isAlive()
     */
    @NotNull Duration timeToLive();

    /**
     * Checks if this event is still alive, meaning it's time-to-live has not yet expired. The current time is retrieved
     * from the {@linkplain DomainContext#clock() clock} of the {@linkplain DomainContextHolder#getCurrent() current domain context}.
     *
     * @return true if the event is still alive, false if it is not
     * @see #eventTimestamp()
     * @see #timeToLive()
     */
    default boolean isAlive() {
        return isAlive(DomainContextHolder.getCurrent().clock());
    }

    /**
     * Checks if this event is still alive, meaning it's time-to-live has not yet expired.
     *
     * @param clock the clock to use for getting the current time
     * @return true if the event is still alive, false if it is not
     * @see #eventTimestamp()
     * @see #timeToLive()
     */
    default boolean isAlive(@NotNull Clock clock) {
        var now = clock.instant();
        var deadline = eventTimestamp().plus(timeToLive());
        return deadline.isBefore(now);
    }
}
