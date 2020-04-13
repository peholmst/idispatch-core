package net.pkhapps.idispatch.core.server.support.domain.persistence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO Document me
 *
 * @param <ClassT>
 */
public class MapReadWriteModel<ClassT> implements WriteModel<ClassT>, ReadModel<ClassT> {

    private final Map<String, Object> map;

    public MapReadWriteModel() {
        this(new HashMap<>());
    }

    public MapReadWriteModel(@NotNull Map<String, Object> map) {
        this.map = map;
    }

    public @NotNull Map<String, Object> map() {
        return map;
    }

    @Override
    public <AttributeT> void writeLeaf(@NotNull Attribute<ClassT, AttributeT> attribute, @Nullable AttributeT value) {
        map.put(attribute.name(), value);
    }

    @Override
    public <AttributeT> void writeTree(@NotNull Attribute<ClassT, AttributeT> attribute, @Nullable Consumer<WriteModel<AttributeT>> writer) {
        if (writer == null) {
            map.put(attribute.name(), null);
        } else {
            var subModel = new MapReadWriteModel<AttributeT>();
            writer.accept(subModel);
            map.put(attribute.name(), subModel.map());
        }
    }

    @Override
    public <AttributeT> void writeLeafCollection(@NotNull Attribute<ClassT, AttributeT> attribute, @NotNull Stream<AttributeT> values) {
        map.put(attribute.name(), values.collect(Collectors.toList()));
    }

    @Override
    public <AttributeT> void writeTreeCollection(@NotNull Attribute<ClassT, AttributeT> attribute, @NotNull Stream<Consumer<WriteModel<AttributeT>>> writers) {
        var result = writers.map(writer -> {
            var subModel = new MapReadWriteModel<AttributeT>();
            writer.accept(subModel);
            return subModel.map();
        }).collect(Collectors.toList());
        map.put(attribute.name(), result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <AttributeT> @Nullable AttributeT readLeaf(@NotNull Attribute<ClassT, AttributeT> attribute) {
        return (AttributeT) map.get(attribute.name());
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <AttributeT> ReadModel<AttributeT> readTree(@NotNull Attribute<ClassT, AttributeT> attribute) {
        var subMap = map.get(attribute.name());
        if (subMap instanceof Map) {
            return new MapReadWriteModel<>((Map<String, Object>) subMap);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <AttributeT> Stream<AttributeT> readLeafCollection(@NotNull Attribute<ClassT, AttributeT> attribute) {
        var collection = map.get(attribute.name());
        if (collection instanceof Collection) {
            return ((Collection<AttributeT>) collection).stream();
        }
        return Stream.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <AttributeT> Stream<ReadModel<AttributeT>> readTreeCollection(@NotNull Attribute<ClassT, AttributeT> attribute) {
        var collection = map.get(attribute.name());
        if (collection instanceof Collection) {
            return ((Collection<Map<String, Object>>) collection).stream().map(MapReadWriteModel::new);
        }
        return Stream.empty();
    }
}
