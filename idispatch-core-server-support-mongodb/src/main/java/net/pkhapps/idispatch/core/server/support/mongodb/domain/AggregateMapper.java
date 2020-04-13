package net.pkhapps.idispatch.core.server.support.mongodb.domain;

import net.pkhapps.idispatch.core.server.support.domain.AggregateRoot;
import net.pkhapps.idispatch.core.server.support.domain.DomainObjectId;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 */
public interface AggregateMapper {

    <AggregateRootT extends AggregateRoot<?>> @NotNull BsonDocument toDocument(
            @NotNull Class<AggregateRootT> aggregateType, @NotNull AggregateRootT aggregate);

    <AggregateRootT extends AggregateRoot<?>> @NotNull AggregateRootT toAggregate(
            @NotNull Class<AggregateRootT> aggregateType, @NotNull BsonDocument document);

    @NotNull BsonValue toBsonValue(@NotNull DomainObjectId id);
}
