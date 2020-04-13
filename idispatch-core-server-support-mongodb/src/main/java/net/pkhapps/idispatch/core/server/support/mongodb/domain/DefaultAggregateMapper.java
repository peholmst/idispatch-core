package net.pkhapps.idispatch.core.server.support.mongodb.domain;

import net.pkhapps.idispatch.core.server.support.domain.AggregateRoot;
import net.pkhapps.idispatch.core.server.support.domain.DomainObjectId;
import net.pkhapps.idispatch.core.server.support.domain.persistence.TreeModel;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Document me
 */
public class DefaultAggregateMapper implements AggregateMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAggregateMapper.class);

    @Override
    public @NotNull <AggregateRootT extends AggregateRoot<?>> BsonDocument toDocument(
            @NotNull Class<AggregateRootT> aggregateType, @NotNull AggregateRootT aggregate) {
        LOGGER.debug("Converting aggregate [{}] to BSON document", aggregate);
        return TreeModel.of(aggregateType).writeObject(aggregate, new BsonReadWriteModel<>()).document();
    }

    @Override
    public <AggregateRootT extends AggregateRoot<?>> @NotNull AggregateRootT toAggregate(
            @NotNull Class<AggregateRootT> aggregateType, @NotNull BsonDocument document) {
        LOGGER.debug("Converting BSON document [{}] to aggregate", document);
        return TreeModel.of(aggregateType).readObject(new BsonReadWriteModel<>(document));
    }

    @Override
    public @NotNull BsonValue toBsonValue(@NotNull DomainObjectId id) {
        // TODO Maybe use the @LeafValue annotation
        return new BsonString(id.toString());
    }
}
