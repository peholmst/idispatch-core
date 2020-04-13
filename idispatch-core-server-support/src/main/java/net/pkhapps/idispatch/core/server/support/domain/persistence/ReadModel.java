package net.pkhapps.idispatch.core.server.support.domain.persistence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * TODO Document me
 *
 * @param <ClassT>
 */
public interface ReadModel<ClassT> {

    /**
     * @param attribute
     * @param <AttributeT>
     * @return
     */
    <AttributeT> @Nullable AttributeT readLeaf(@NotNull Attribute<ClassT, AttributeT> attribute);

    /**
     * @param attribute
     * @param <AttributeT>
     * @return
     */
    <AttributeT> @Nullable ReadModel<AttributeT> readTree(@NotNull Attribute<ClassT, AttributeT> attribute);

    /**
     * @param attribute
     * @param <AttributeT>
     * @return
     */
    <AttributeT> @NotNull Stream<AttributeT> readLeafCollection(@NotNull Attribute<ClassT, AttributeT> attribute);

    /**
     * @param attribute
     * @param <AttributeT>
     * @return
     */
    <AttributeT> @NotNull Stream<ReadModel<AttributeT>> readTreeCollection(
            @NotNull Attribute<ClassT, AttributeT> attribute);
}
