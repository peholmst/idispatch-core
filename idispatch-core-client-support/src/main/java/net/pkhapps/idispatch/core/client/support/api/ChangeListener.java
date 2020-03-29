package net.pkhapps.idispatch.core.client.support.api;

/**
 * Interface for a listener that listens for changes of some particular property/attribute.
 *
 * @param <T> the type of the property/attribute.
 */
@FunctionalInterface
public interface ChangeListener<T> {

    /**
     * Called when the value of the property/attribute has changed. The values may or may not be {@code null} depending
     * on the property/attribute in question.
     *
     * @param oldValue the old value.
     * @param newValue the new (current) value.
     */
    void onChange(T oldValue, T newValue);
}
