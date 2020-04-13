package net.pkhapps.idispatch.core.server.support.domain.persistence;

import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.LeafCreator;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.LeafValue;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.PersistableAttribute;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 *
 * @param <ClassT>
 * @param <AttributeT>
 */
public class Attribute<ClassT, AttributeT> {

    /**
     *
     */
    public static final Set<Class<?>> LEAF_TYPES = Set.of(
            String.class,
            Integer.class,
            Integer.TYPE,
            Long.class,
            Long.TYPE,
            Double.class,
            Double.TYPE,
            Boolean.class,
            Boolean.TYPE,
            Instant.class,
            Enum.class
    );
    private static final Logger LOGGER = LoggerFactory.getLogger(Attribute.class);
    private final Field field;
    private final String name;
    private final boolean collection;
    private final Class<AttributeT> type;
    private final boolean leaf;
    private final TreeModel<AttributeT> treeModel;
    private final Serializer<AttributeT> serializer;
    private final Deserializer<AttributeT> deserializer;

    // TODO Improve exception messages

    @SuppressWarnings("unchecked")
    Attribute(@NotNull Field field, @NotNull Function<TypeVariable<?>, Class<?>> genericTypeResolver) {
        LOGGER.trace("Creating attribute for field [{}]", field);
        var annotation = field.getAnnotation(PersistableAttribute.class);
        if (annotation == null) {
            LOGGER.error("Field [{}] does not have the SerializableAttribute annotation", field);
            throw new IllegalStateException("The field does not have the SerializableAttribute annotation");
        }
        if (Modifier.isFinal(field.getModifiers())) {
            LOGGER.error("Field [{}] is final", field);
            throw new IllegalStateException("The field cannot be final");
        }
        this.field = field;
        if (!field.trySetAccessible()) {
            LOGGER.error("Field [{}] cannot be accessed. Did you open [{}] to [{}]?", field,
                    field.getDeclaringClass().getPackage().getName(),
                    this.getClass().getModule().getName());
            throw new IllegalStateException("The field cannot be accessed");
        }
        if (annotation.name().isEmpty()) {
            this.name = field.getName();
        } else {
            this.name = annotation.name();
        }
        LOGGER.trace("  Attribute name: [{}]", this.name);
        this.collection = isCollectionType(field.getType());
        LOGGER.trace("  Attribute is collection: [{}]", this.collection);
        Class<?> rawType;
        if (this.collection) {
            if (annotation.type() == Object.class) {
                LOGGER.error("Field [{}] does not declare an explicit type", field);
                throw new IllegalStateException("Need to specify explicit type on collection attributes");
            }
            rawType = annotation.type();
        } else {
            if (annotation.type() != Object.class) {
                if (!field.getType().isAssignableFrom(annotation.type())) {
                    LOGGER.error("Field [{}] does not have a type that is assignable from [{}]", field, annotation.type());
                    throw new IllegalStateException("Field type is not assignable from explicitly declared type");
                }
                rawType = annotation.type();
            } else {
                var genericType = field.getGenericType();
                if (genericType instanceof TypeVariable) {
                    rawType = genericTypeResolver.apply((TypeVariable<?>) genericType);
                    if (rawType == null) {
                        LOGGER.error("Generic type [{}] cannot be resolved", genericType);
                        throw new IllegalStateException("Generic type " + genericType + " cannot be resolved");
                    }
                } else {
                    rawType = field.getType();
                }
            }
        }

        var leafValueMethod = findMethodWithAnnotation(LeafValue.class, rawType);
        var leafCreatorConstructor = findConstructorWithAnnotation(LeafCreator.class, rawType);
        if (leafValueMethod.isPresent() && leafCreatorConstructor.isPresent()) {
            this.type = (Class<AttributeT>) leafValueMethod.get().getReturnType();
            this.serializer = new MethodSerializer<>(leafValueMethod.get());
            this.deserializer = new ConstructorDeserializer<>(leafCreatorConstructor.get());
            // TODO You could check the constructor parameters here to fail early in case they are wrong
        } else {
            this.type = (Class<AttributeT>) rawType;
            this.serializer = (Serializer<AttributeT>) IdentitySerializerAndDeserializer.INSTANCE;
            this.deserializer = (Deserializer<AttributeT>) IdentitySerializerAndDeserializer.INSTANCE;
        }
        LOGGER.trace("  Attribute type: [{}]", this.type);
        this.leaf = isLeafType(this.type);
        LOGGER.trace("  Attribute is leaf: [{}]", this.leaf);
        if (this.leaf) {
            treeModel = null;
        } else {
            treeModel = TreeModel.of(this.type);
        }
    }

    private static boolean isCollectionType(@NotNull Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }

    private static boolean isLeafType(@NotNull Class<?> type) {
        return (LEAF_TYPES.stream().anyMatch(t -> t.isAssignableFrom(type)));
    }

    private static @NotNull Optional<Method> findMethodWithAnnotation(@NotNull Class<? extends Annotation> annotationClass,
                                                                      @NotNull Class<?> type) {
        var t = requireNonNull(type);
        while (t != null && t != Object.class) {
            for (var m : t.getDeclaredMethods()) {
                if (m.isAnnotationPresent(annotationClass)) {
                    return Optional.of(m);
                }
            }
            t = t.getSuperclass();
        }
        return Optional.empty();
    }

    private static @NotNull Optional<Constructor<?>> findConstructorWithAnnotation(@NotNull Class<? extends Annotation> annotationClass,
                                                                                   @NotNull Class<?> type) {
        for (var c : type.getDeclaredConstructors()) {
            if (c.isAnnotationPresent(annotationClass)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    /**
     * @return
     */
    public boolean isTree() {
        return !leaf;
    }

    /**
     * @return
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * @return
     */
    public boolean isCollection() {
        return collection;
    }

    /**
     * @return
     */
    public @NotNull TreeModel<AttributeT> treeModel() {
        if (treeModel == null) {
            throw new IllegalStateException("This attribute is a leaf and therefore has no TreeModel");
        }
        return treeModel;
    }

    /**
     * @return
     */
    public @NotNull String name() {
        return name;
    }

    /**
     * @return
     */
    public @NotNull Class<AttributeT> type() {
        return type;
    }

    /**
     * @param source
     * @return
     */
    @Nullable AttributeT readValue(@NotNull ClassT source) {
        if (collection) {
            throw new IllegalStateException("Attribute is a collection, use readCollection instead");
        }
        try {
            field.trySetAccessible();
            return serializer.serialize(field.get(source));
        } catch (Exception ex) {
            throw new IllegalStateException("Could not read field value from object", ex);
        }
    }

    /**
     * @param destination
     * @param value
     */
    void writeValue(@NotNull ClassT destination, @Nullable AttributeT value) {
        if (collection) {
            throw new IllegalStateException("Attribute is a collection, use writeCollection instead");
        }
        try {
            field.trySetAccessible();
            if (!field.getType().isPrimitive() || value != null) {
                field.set(destination, deserializer.deserialize(value));
            } else {
                LOGGER.warn("Tried to write null to primitive field [{}]", field);
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Could not write field value to object", ex);
        }
    }

    /**
     * @param source
     * @return
     */
    @NotNull Stream<AttributeT> readCollection(@NotNull ClassT source) {
        if (!collection) {
            throw new IllegalStateException("Attribute is not a collection, use readValue instead");
        }
        try {
            field.trySetAccessible();
            var value = (Collection<?>) field.get(source);
            if (value == null) {
                return Stream.empty();
            }
            return value.stream().map(serializer::serialize);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not read field value from object", ex);
        }
    }

    /**
     * @param destination
     * @param value
     */
    void writeCollection(@NotNull ClassT destination, @Nullable Stream<AttributeT> value) {
        if (!collection) {
            throw new IllegalStateException("Attribute is not a collection, use writeValue instead");
        }
        try {
            field.trySetAccessible();
            Collection<Object> result;
            if (Set.class.isAssignableFrom(field.getType())) {
                result = new HashSet<>();
            } else {
                result = new ArrayList<>();
            }
            if (value != null) {
                value.map(deserializer::deserialize).forEach(result::add);
            }
            field.set(destination, result);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not write field value to object", ex);
        }
    }

    @FunctionalInterface
    private interface Serializer<AttributeT> {
        @Contract("null -> null")
        AttributeT serialize(Object raw);
    }

    @FunctionalInterface
    private interface Deserializer<AttributeT> {
        @Contract("null -> null")
        Object deserialize(AttributeT serialized);
    }

    private static class MethodSerializer<AttributeT> implements Serializer<AttributeT> {

        private final Method leafValue;

        MethodSerializer(@NotNull Method leafValue) {
            this.leafValue = requireNonNull(leafValue);
        }

        @SuppressWarnings("unchecked")
        @Override
        public AttributeT serialize(Object raw) {
            if (raw == null) {
                return null;
            }
            try {
                leafValue.trySetAccessible();
                return (AttributeT) leafValue.invoke(raw);
            } catch (Exception ex) {
                throw new IllegalStateException("Could not read method value from object", ex);
            }
        }
    }

    private static class ConstructorDeserializer<AttributeT> implements Deserializer<AttributeT> {

        private final Constructor<?> constructor;

        ConstructorDeserializer(@NotNull Constructor<?> constructor) {
            this.constructor = requireNonNull(constructor);
        }

        @Override
        public Object deserialize(AttributeT serialized) {
            if (serialized == null) {
                return null;
            }
            try {
                constructor.trySetAccessible();
                return constructor.newInstance(serialized);
            } catch (Exception ex) {
                throw new IllegalStateException("Could not create new object", ex);
            }
        }
    }

    private static class IdentitySerializerAndDeserializer<AttributeT> implements Serializer<AttributeT>,
            Deserializer<AttributeT> {

        static final IdentitySerializerAndDeserializer<?> INSTANCE = new IdentitySerializerAndDeserializer<>();

        @SuppressWarnings("unchecked")
        @Override
        public AttributeT serialize(Object raw) {
            return (AttributeT) raw;
        }

        @Override
        public Object deserialize(AttributeT serialized) {
            return serialized;
        }
    }
}
