package net.pkhapps.idispatch.core.server.support.domain.persistence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * TODO Document me
 *
 * @param <ClassT>
 */
public interface WriteModel<ClassT> {

    /**
     * @param attribute
     * @param value
     * @param <AttributeT>
     */
    <AttributeT> void writeLeaf(@NotNull Attribute<ClassT, AttributeT> attribute, @Nullable AttributeT value);

    /**
     * @param attribute
     * @param writer
     * @param <AttributeT>
     */
    <AttributeT> void writeTree(@NotNull Attribute<ClassT, AttributeT> attribute, @Nullable Consumer<WriteModel<AttributeT>> writer);

    /**
     * @param attribute
     * @param values
     * @param <AttributeT>
     */
    <AttributeT> void writeLeafCollection(@NotNull Attribute<ClassT, AttributeT> attribute,
                                          @NotNull Stream<AttributeT> values);

    /**
     * @param attribute
     * @param writers
     * @param <AttributeT>
     */
    <AttributeT> void writeTreeCollection(@NotNull Attribute<ClassT, AttributeT> attribute,
                                          @NotNull Stream<Consumer<WriteModel<AttributeT>>> writers);
}
