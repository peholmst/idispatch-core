package net.pkhapps.idispatch.core.server.support.domain.persistence;

import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.PersistAsTree;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.PersistableAttribute;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * TODO Document me
 *
 * @param <ClassT>
 */
public class TreeModel<ClassT> {

    private static final ConcurrentMap<Class<?>, TreeModel<?>> MODEL_CACHE = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TreeModel.class);
    private final Map<String, Attribute<ClassT, ?>> attributes;
    private final Constructor<ClassT> defaultConstructor;

    // TODO Improve error logging

    private TreeModel(@NotNull Class<ClassT> clazz) {
        if (clazz == Object.class || clazz.isPrimitive()) {
            throw new IllegalStateException("Cannot create TreeModel for Object or primitives");
        }
        if (!clazz.isAnnotationPresent(PersistAsTree.class)) {
            LOGGER.error("Class [{}] does not have the PersistAsTree annotation", clazz.getName());
            throw new IllegalStateException("Class " + clazz.getName() + " has no PersistAsTree annotation");
        }
        LOGGER.debug("Creating tree model for [{}]", clazz.getName());
        try {
            defaultConstructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException ex) {
            throw new IllegalStateException("Class " + clazz.getName() + " has no default constructor");
        }

        var genericTypeMap = findActualGenericTypes(clazz);
        var attributes = new HashMap<String, Attribute<ClassT, ?>>();
        Class<?> t = clazz;
        while (t != null && t != Object.class) {
            LOGGER.trace("Scanning class {} for attributes", t.getName());
            for (var field : t.getDeclaredFields()) {
                if (field.isAnnotationPresent(PersistableAttribute.class)) {
                    var attribute = new Attribute<ClassT, Object>(field, genericTypeMap::get);
                    LOGGER.trace("Adding field [{}] as attribute [{}]", field.getName(), attribute.name());
                    if (attributes.put(attribute.name(), attribute) != null) {
                        throw new IllegalStateException("Duplicate attribute name " + attribute.name() + " (on field " + field.getName() + ") found in class " + t.getName());
                    }
                }
            }
            t = t.getSuperclass();
        }
        this.attributes = Collections.unmodifiableMap(attributes);
        // TODO Support for back references
        // TODO Detection of cycles
    }

    private static @NotNull Map<TypeVariable<?>, Class<?>> findActualGenericTypes(@NotNull Class<?> clazz) {
        var types = new HashMap<TypeVariable<?>, Class<?>>();
        Class<?> c = clazz;
        while (c != null && c != Object.class) {
            var genericSuperclass = c.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                var parameterizedType = (ParameterizedType) genericSuperclass;
                var rawType = (Class<?>) parameterizedType.getRawType();
                var typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < typeParameters.length; ++i) {
                    var typeVariable = typeParameters[i];
                    var actualType = parameterizedType.getActualTypeArguments()[i];
                    if (actualType instanceof TypeVariable) {
                        actualType = types.get(actualType);
                        if (actualType == null) {
                            throw new IllegalStateException("Cannot determine the actual type of " + typeVariable);
                        }
                    }
                    types.put(typeVariable, (Class<?>) actualType);
                }
            }
            c = c.getSuperclass();
        }
        return types;
    }

    /**
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> @NotNull TreeModel<T> of(@NotNull Class<T> clazz) {
        var model = MODEL_CACHE.computeIfAbsent(clazz, TreeModel::new);
        return (TreeModel<T>) model;
    }

    /**
     * @return
     */
    public @NotNull Map<String, Attribute<ClassT, ?>> attributes() {
        return attributes;
    }

    /**
     * @param attributeName
     * @param attributeType
     * @param <AttributeT>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <AttributeT> @NotNull Attribute<ClassT, AttributeT> attributeByName(@NotNull String attributeName,
                                                                               @NotNull Class<AttributeT> attributeType) {
        var attribute = attributes.get(attributeName);
        if (attribute == null) {
            throw new IllegalArgumentException("No such attribute");
        }
        if (attribute.type() != attributeType) {
            throw new IllegalArgumentException("Attribute is not of type " + attributeType);
        }
        return (Attribute<ClassT, AttributeT>) attribute;
    }

    /**
     * @param source
     * @param destination
     */
    public <WriteModelT extends WriteModel<ClassT>> @NotNull WriteModelT writeObject(@NotNull ClassT source,
                                                                                     @NotNull WriteModelT destination) {
        LOGGER.debug("Writing attributes from [{}] to [{}]", source, destination);
        var start = System.nanoTime();
        attributes().values().forEach(attribute -> writeAttribute(source, attribute, destination));
        var stop = System.nanoTime();
        LOGGER.debug("Writing to {} took {} ms", destination, (stop - start) / 1000000d);
        return destination;
    }

    private <AttributeT> void writeAttribute(@NotNull ClassT source,
                                             @NotNull Attribute<ClassT, AttributeT> attribute,
                                             @NotNull WriteModel<ClassT> destination) {
        LOGGER.trace("Writing attribute [{}] from [{}] to [{}]", attribute.name(), source, destination);
        if (attribute.isCollection()) {
            var values = attribute.readCollection(source);
            if (attribute.isLeaf()) {
                destination.writeLeafCollection(attribute, values);
            } else {
                var writers = values
                        .map(value -> (Consumer<WriteModel<AttributeT>>) (model -> attribute.treeModel().writeObject(value, model)));
                destination.writeTreeCollection(attribute, writers);
            }
        } else {
            var value = attribute.readValue(source);
            if (attribute.isLeaf() || value == null) {
                destination.writeLeaf(attribute, value);
            } else {
                destination.writeTree(attribute, model -> attribute.treeModel().writeObject(value, model));
            }
        }
    }

    /**
     * @param source
     * @return
     */
    public @NotNull ClassT readObject(@NotNull ReadModel<ClassT> source) {
        LOGGER.debug("Reading attributes from [{}]", source);
        var start = System.nanoTime();
        ClassT destination;
        try {
            defaultConstructor.trySetAccessible();
            destination = defaultConstructor.newInstance();
        } catch (Exception ex) {
            throw new IllegalStateException("Could not create new object instance", ex);
        }
        attributes.values().forEach(attribute -> readAttribute(source, attribute, destination));
        var stop = System.nanoTime();
        LOGGER.debug("Reading from [{}] took {} ms", source, (stop - start) / 1000000d);
        return destination;
    }

    private <AttributeT> void readAttribute(@NotNull ReadModel<ClassT> source,
                                            @NotNull Attribute<ClassT, AttributeT> attribute,
                                            @NotNull ClassT destination) {
        LOGGER.trace("Reading attribute [{}] from [{}]", attribute.name(), source);
        if (attribute.isCollection()) {
            Stream<AttributeT> values;
            if (attribute.isLeaf()) {
                values = source.readLeafCollection(attribute);
            } else {
                values = source.readTreeCollection(attribute).map(m -> attribute.treeModel().readObject(m));
            }
            attribute.writeCollection(destination, values);
        } else {
            AttributeT value;
            if (attribute.isLeaf()) {
                value = source.readLeaf(attribute);
            } else {
                value = Optional.ofNullable(source.readTree(attribute))
                        .map(m -> attribute.treeModel().readObject(m))
                        .orElse(null);
            }
            attribute.writeValue(destination, value);
        }
    }
}
