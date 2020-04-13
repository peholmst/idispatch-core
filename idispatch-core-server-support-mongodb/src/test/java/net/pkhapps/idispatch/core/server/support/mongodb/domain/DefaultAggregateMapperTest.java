package net.pkhapps.idispatch.core.server.support.mongodb.domain;

import net.pkhapps.idispatch.core.server.support.domain.AggregateRoot;
import net.pkhapps.idispatch.core.server.support.domain.UUIDDomainObjectId;
import net.pkhapps.idispatch.core.server.support.domain.ValueObject;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.ForPersistenceOnly;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.LeafCreator;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.PersistAsTree;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.PersistableAttribute;
import org.bson.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultAggregateMapperTest {

    private final DefaultAggregateMapper mapper = new DefaultAggregateMapper();

    @Test
    public void toDocument() {
        var aggregate = new MyAggregate(new MyAggregateId());
        aggregate.setMyBoolean(true);
        aggregate.setMyInteger(Integer.MAX_VALUE - 1000);
        aggregate.setMyLong(Integer.MAX_VALUE + 1000L);
        aggregate.setMyDouble(3.14);
        aggregate.setMyString("hello world");
        aggregate.getMyList().add(new MyValueObject("first", 1));
        aggregate.getMyList().add(new MyValueObject("second", 2));

        var document = mapper.toDocument(MyAggregate.class, aggregate);

        assertThat(document.getString("_id").getValue()).isEqualTo(aggregate.id().toString());
        assertThat(document.getInt64("_version").getValue()).isEqualTo(aggregate.version());
        assertThat(document.getBoolean("myBoolean").getValue()).isEqualTo(aggregate.isMyBoolean());
        assertThat(document.getInt32("myInteger").getValue()).isEqualTo(aggregate.getMyInteger());
        assertThat(document.getInt64("myLong").getValue()).isEqualTo(aggregate.getMyLong());
        assertThat(document.getDouble("myDouble").getValue()).isEqualTo(aggregate.getMyDouble());
        assertThat(document.getString("myString").getValue()).isEqualTo(aggregate.getMyString());
        var myList = document.getArray("myList");
        var first = (BsonDocument) myList.get(0);
        var second = (BsonDocument) myList.get(1);

        assertThat(first.getString("aString").getValue()).isEqualTo("first");
        assertThat(first.getInt32("anInt").getValue()).isEqualTo(1);

        assertThat(second.getString("aString").getValue()).isEqualTo("second");
        assertThat(second.getInt32("anInt").getValue()).isEqualTo(2);

        System.out.println(document.toJson());
    }

    @Test
    public void toAggregate() {
        var document = new BsonDocument();
        document.put("_id", new BsonString(UUID.randomUUID().toString()));
        document.put("_version", new BsonInt64(0));
        document.put("myBoolean", new BsonBoolean(true));
        document.put("myInteger", new BsonInt32(123));
        document.put("myLong", new BsonInt64(456));
        document.put("myDouble", new BsonDouble(3.14));
        document.put("myString", new BsonString("hello world"));

        var myList = new BsonArray();
        document.put("myList", myList);

        var first = new BsonDocument();
        first.put("aString", new BsonString("first"));
        first.put("anInt", new BsonInt32(1));
        myList.add(first);

        var second = new BsonDocument();
        second.put("aString", new BsonString("second"));
        second.put("anInt", new BsonInt32(2));
        myList.add(second);

        var aggregate = mapper.toAggregate(MyAggregate.class, document);
        // TODO Assertions
    }

    public static class MyAggregateId extends UUIDDomainObjectId {

        @LeafCreator
        public MyAggregateId(@NotNull String uuid) {
            super(uuid);
        }

        public MyAggregateId() {
        }
    }

    @PersistAsTree
    public static class MyAggregate extends AggregateRoot<MyAggregateId> {

        @PersistableAttribute
        private String myString;

        @PersistableAttribute
        private boolean myBoolean;

        @PersistableAttribute
        private int myInteger;

        @PersistableAttribute
        private long myLong;

        @PersistableAttribute
        private double myDouble;

        @PersistableAttribute(type = MyValueObject.class)
        private List<MyValueObject> myList = new ArrayList<>();

        // TODO Add instant and enum fields to test

        @ForPersistenceOnly
        private MyAggregate() {
        }

        public MyAggregate(@NotNull MyAggregateId id) {
            super(id);
        }

        public String getMyString() {
            return myString;
        }

        public void setMyString(String myString) {
            this.myString = myString;
        }

        public boolean isMyBoolean() {
            return myBoolean;
        }

        public void setMyBoolean(boolean myBoolean) {
            this.myBoolean = myBoolean;
        }

        public int getMyInteger() {
            return myInteger;
        }

        public void setMyInteger(int myInteger) {
            this.myInteger = myInteger;
        }

        public long getMyLong() {
            return myLong;
        }

        public void setMyLong(long myLong) {
            this.myLong = myLong;
        }

        public double getMyDouble() {
            return myDouble;
        }

        public void setMyDouble(double myDouble) {
            this.myDouble = myDouble;
        }

        public List<MyValueObject> getMyList() {
            return myList;
        }
    }

    @PersistAsTree
    public static class MyValueObject implements ValueObject {

        @PersistableAttribute
        private String aString;

        @PersistableAttribute
        private int anInt;

        @ForPersistenceOnly
        private MyValueObject() {
        }

        public MyValueObject(String aString, int anInt) {
            this.aString = aString;
            this.anInt = anInt;
        }

        public String getaString() {
            return aString;
        }

        public int getAnInt() {
            return anInt;
        }
    }
}
