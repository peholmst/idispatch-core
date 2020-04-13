package net.pkhapps.idispatch.core.server.support.mongodb.domain;

import com.mongodb.client.MongoCollection;
import net.pkhapps.idispatch.core.server.support.domain.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 *
 * @param <AggregateRootT>
 * @param <IdT>
 */
public abstract class MongoRepository<AggregateRootT extends AggregateRoot<IdT>, IdT extends DomainObjectId>
        extends AbstractRepository<AggregateRootT, IdT> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Supplier<MongoCollection<BsonDocument>> collectionSupplier;
    private final AggregateMapper mapper;
    private final Class<AggregateRootT> aggregateType;

    protected MongoRepository(@NotNull Supplier<MongoCollection<BsonDocument>> collectionSupplier,
                              @NotNull AggregateMapper mapper,
                              @NotNull Class<AggregateRootT> aggregateType) {
        this.collectionSupplier = requireNonNull(collectionSupplier);
        this.mapper = requireNonNull(mapper);
        this.aggregateType = requireNonNull(aggregateType);
    }

    protected MongoRepository(@NotNull Supplier<MongoCollection<BsonDocument>> collectionSupplier,
                              @NotNull Class<AggregateRootT> aggregateType) {
        this(collectionSupplier, new DefaultAggregateMapper(), aggregateType);
    }

    /**
     * @return
     */
    protected @NotNull MongoCollection<BsonDocument> collection() {
        return collectionSupplier.get();
    }

    @Override
    public @NotNull Optional<AggregateRootT> findById(@NotNull IdT id) {
        logger.debug("Looking up aggregate with id [{}]", id);
        var result = collection().find(eq("_id", id.toString())).first();
        //noinspection ConstantConditions (because IntelliJ things result can never be null, which it can)
        return Optional.ofNullable(result).map(doc -> mapper.toAggregate(aggregateType, doc));
    }

    @Override
    public @NotNull AggregateRootT save(@NotNull AggregateRootT aggregate) {
        var unitOfWorkManager = DomainContext.current().unitOfWorkManager();
        if (unitOfWorkManager.currentlyExecutingUnitOfWork()) {
            return doSave(aggregate);
        } else {
            return unitOfWorkManager.execute(() -> doSave(aggregate));
        }
    }

    private AggregateRootT doSave(@NotNull AggregateRootT aggregate) {
        var expectedVersion = aggregate.version();
        var document = mapper.toDocument(aggregateType, aggregate);
        var id = aggregate.id();
        document.put("_version", new BsonInt64(expectedVersion + 1));
        if (aggregate.isPersistent()) {
            logger.debug("Updating aggregate with id [{}]", id);
            var result = collection().replaceOne(
                    and(
                            eq("_id", mapper.toBsonValue(id)),
                            eq("_version", expectedVersion)
                    ), document);
            if (result.getModifiedCount() == 0) {
                throw new OptimisticLockingException();
            }
        } else {
            logger.debug("Inserting aggregate with id [{}]", id);
            collection().insertOne(document);
        }
        DomainContext.current().domainEvents().publishAll(getDomainEvents(aggregate));
        clearDomainEvents(aggregate);
        return mapper.toAggregate(aggregateType, document);
    }

    @Override
    public void deleteById(@NotNull IdT id) {
        logger.debug("Deleting aggregate with id [{}]", id);
        collection().deleteOne(eq("_id", mapper.toBsonValue(id)));
    }
}
