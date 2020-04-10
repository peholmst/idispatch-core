package net.pkhapps.idispatch.core.client.support.api;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Value object for a multilingual string.
 */
public class MultilingualString {

    private final Map<Locale, String> values;
    private final Locale defaultLocale;

    /**
     * Creates a new {@code MultilingualString} with the given values.
     *
     * @param values        a map of values
     * @param defaultLocale the default locale
     * @throws IllegalArgumentException if the {@code values} map does not contain an entry for the default locale
     */
    public MultilingualString(@NotNull Map<Locale, String> values, @NotNull Locale defaultLocale) {
        this.defaultLocale = requireNonNull(defaultLocale);
        if (!values.containsKey(defaultLocale)) {
            throw new IllegalArgumentException("There is no value in the map for the default locale");
        }
        this.values = Map.copyOf(values);
    }

    /**
     * Creates a new {@code MultilingualString} with a single value.
     *
     * @param locale the locale of the value
     * @param value  the value
     */
    public MultilingualString(@NotNull Locale locale, @NotNull String value) {
        this(Map.of(locale, value), locale);
    }

    /**
     * TODO Document me
     *
     * @param finnish
     * @param swedish
     */
    public MultilingualString(@NotNull String finnish, @NotNull String swedish) {
        this(Map.of(Locales.FINNISH, finnish, Locales.SWEDISH, swedish), Locales.FINNISH);
    }

    /**
     * The locales for which there are values in this multilingual string.
     */
    public @NotNull Set<Locale> locales() {
        return Collections.unmodifiableSet(values.keySet());
    }

    /**
     * Returns the value of the given locale.
     *
     * @param locale the locale
     * @return the value or the {@linkplain #defaultValue() default value} if no value exists for the given locale
     */
    public @NotNull String value(@NotNull Locale locale) {
        return Optional.ofNullable(values.get(locale)).orElseGet(this::defaultValue);
    }

    /**
     * Returns the value for the default locale.
     */
    public @NotNull String defaultValue() {
        return values.get(defaultLocale);
    }
}
