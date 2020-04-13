package net.pkhapps.idispatch.core.server.support.domain.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO Document me
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializableAttribute {

    /**
     * @return
     */
    String name() default "";

    /**
     * @return
     */
    Class<?> type() default Object.class;
}
