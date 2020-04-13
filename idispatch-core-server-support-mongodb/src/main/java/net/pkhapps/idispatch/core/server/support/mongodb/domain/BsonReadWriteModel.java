package net.pkhapps.idispatch.core.server.support.mongodb.domain;

import net.pkhapps.idispatch.core.server.support.domain.persistence.Attribute;
import net.pkhapps.idispatch.core.server.support.domain.persistence.ReadModel;
import net.pkhapps.idispatch.core.server.support.domain.persistence.WriteModel;
import org.bson.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 *
 * @param <ClassT>
 */
public class BsonReadWriteModel<ClassT> implements ReadModel<ClassT>, WriteModel<ClassT> {

    private final BsonDocument document;

    /**
     *
     */
    public BsonReadWriteModel() {
        this(new BsonDocument());
    }

    /**
     * @param document
     */
    public BsonReadWriteModel(@NotNull BsonDocument document) {
        this.document = requireNonNull(document);
    }

    /**
     * @return
     */
    public @NotNull BsonDocument document() {
        return document;
    }

    @Override
    public <AttributeT> @Nullable AttributeT readLeaf(@NotNull Attribute<ClassT, AttributeT> attribute) {
        return bsonValueToLeaf(attribute, document.get(attribute.name()));
    }

    @Override
    public @Nullable <AttributeT> ReadModel<AttributeT> readTree(@NotNull Attribute<ClassT, AttributeT> attribute) {
        var subDocument = document.getDocument(attribute.name());
        return subDocument == null ? null : new BsonReadWriteModel<>(subDocument);
    }

    @Override
    public @NotNull <AttributeT> Stream<AttributeT> readLeafCollection(@NotNull Attribute<ClassT, AttributeT> attribute) {
        var array = document.getArray(attribute.name());
        if (array == null) {
            return Stream.empty();
        } else {
            return array.stream().map(value -> bsonValueToLeaf(attribute, value));
        }
    }

    @Override
    public @NotNull <AttributeT> Stream<ReadModel<AttributeT>> readTreeCollection(@NotNull Attribute<ClassT, AttributeT> attribute) {
        var array = document.getArray(attribute.name());
        if (array == null) {
            return Stream.empty();
        } else {
            return array.stream().map(BsonValue::asDocument).map(BsonReadWriteModel::new);
        }
    }

    @Override
    public <AttributeT> void writeLeaf(@NotNull Attribute<ClassT, AttributeT> attribute, @Nullable AttributeT value) {
        document.put(attribute.name(), leafValueToBson(attribute, value));
    }

    @Override
    public <AttributeT> void writeTree(@NotNull Attribute<ClassT, AttributeT> attribute,
                                       @Nullable Consumer<WriteModel<AttributeT>> writer) {
        if (writer == null) {
            document.put(attribute.name(), BsonNull.VALUE);
        } else {
            var model = new BsonReadWriteModel<AttributeT>();
            writer.accept(model);
            document.put(attribute.name(), model.document());
        }
    }

    @Override
    public <AttributeT> void writeLeafCollection(@NotNull Attribute<ClassT, AttributeT> attribute,
                                                 @NotNull Stream<AttributeT> values) {
        var array = new BsonArray();
        values.map(v -> leafValueToBson(attribute, v)).forEach(array::add);
        document.put(attribute.name(), array);
    }

    @Override
    public <AttributeT> void writeTreeCollection(@NotNull Attribute<ClassT, AttributeT> attribute,
                                                 @NotNull Stream<Consumer<WriteModel<AttributeT>>> writers) {
        var array = new BsonArray();
        writers.map(writer -> {
            var model = new BsonReadWriteModel<AttributeT>();
            writer.accept(model);
            return model.document();
        }).forEach(array::add);
        document.put(attribute.name(), array);
    }

    private <AttributeT> @NotNull BsonValue leafValueToBson(@NotNull Attribute<ClassT, AttributeT> attribute,
                                                            @Nullable AttributeT value) {
        if (value == null) {
            return BsonNull.VALUE;
        }
        var type = attribute.type();
        if (String.class == type) {
            return new BsonString((String) value);
        } else if (Integer.class == type || Integer.TYPE == type) {
            return new BsonInt32((int) value);
        } else if (Long.class == type || Long.TYPE == type) {
            return new BsonInt64((long) value);
        } else if (Double.class == type || Double.TYPE == type) {
            return new BsonDouble((double) value);
        } else if (Boolean.class == type || Boolean.TYPE == type) {
            return new BsonBoolean((boolean) value);
        } else if (Instant.class == type) {
            return new BsonDateTime(((Instant) value).toEpochMilli());
        } else if (type.isEnum()) {
            return new BsonString(((Enum<?>) value).name());
        } else {
            throw new IllegalArgumentException("Unsupported leaf type: " + type);
        }
    }

    @Nullable
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <AttributeT> AttributeT bsonValueToLeaf(@NotNull Attribute<ClassT, AttributeT> attribute,
                                                    @Nullable BsonValue value) {
        if (value == null || value instanceof BsonNull) {
            return null;
        }
        var type = attribute.type();
        if (String.class == type) {
            return (AttributeT) value.asString().getValue();
        } else if (Integer.class == type || Integer.TYPE == type) {
            return (AttributeT) Integer.valueOf(value.asInt32().getValue());
        } else if (Long.class == type || Long.TYPE == type) {
            return (AttributeT) Long.valueOf(value.asInt64().getValue());
        } else if (Double.class == type || Double.TYPE == type) {
            return (AttributeT) Double.valueOf(value.asDouble().getValue());
        } else if (Boolean.class == type || Boolean.TYPE == type) {
            return (AttributeT) Boolean.valueOf(value.asBoolean().getValue());
        } else if (Instant.class == type) {
            return (AttributeT) Instant.ofEpochMilli(value.asDateTime().getValue());
        } else if (type.isEnum()) {
            return (AttributeT) Enum.valueOf((Class) type, value.asString().getValue());
        } else {
            throw new IllegalArgumentException("Unsupported leaf type: " + type);
        }
    }
}
