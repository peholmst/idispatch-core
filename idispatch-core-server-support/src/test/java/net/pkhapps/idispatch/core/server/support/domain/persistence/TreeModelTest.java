package net.pkhapps.idispatch.core.server.support.domain.persistence;

import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.LeafCreator;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.LeafValue;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.SerializableAttribute;
import org.assertj.core.data.Index;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TreeModelTest {

    @Test
    public void createTreeModel() {
        var model = TreeModel.of(TestParent.class);
        assertThat(model.attributes().get("myString")).satisfies(attribute ->
                attributeRequirements(attribute, "myString", String.class, false, true));
        assertThat(model.attributes().get("myInteger")).satisfies(attribute ->
                attributeRequirements(attribute, "myInteger", Integer.TYPE, false, true));
        assertThat(model.attributes().get("myLong")).satisfies(attribute ->
                attributeRequirements(attribute, "myLong", Long.TYPE, false, true));
        assertThat(model.attributes().get("myBool")).satisfies(attribute ->
                attributeRequirements(attribute, "myBool", Boolean.TYPE, false, true));
        assertThat(model.attributes().get("myDouble")).satisfies(attribute ->
                attributeRequirements(attribute, "myDouble", Double.TYPE, false, true));
        assertThat(model.attributes().get("myEnum")).satisfies(attribute ->
                attributeRequirements(attribute, "myEnum", TestEnum.class, false, true));
        assertThat(model.attributes().get("myChild")).satisfies(attribute ->
                attributeRequirements(attribute, "myChild", TestChild.class, false, false));
        assertThat(model.attributes().get("myChildren")).satisfies(attribute ->
                attributeRequirements(attribute, "myChildren", TestChild.class, true, false));
        assertThat(model.attributes().get("myStringList")).satisfies(attribute ->
                attributeRequirements(attribute, "myStringList", String.class, true, true));
        assertThat(model.attributes().get("myLeafWrapper")).satisfies(attribute ->
                attributeRequirements(attribute, "myLeafWrapper", String.class, false, true));
        assertThat(model.attributes().get("myInstant")).satisfies(attribute ->
                attributeRequirements(attribute, "myInstant", Instant.class, false, true));
        assertThat(model.attributes().get("myLeafWrapperList")).satisfies(attribute ->
                attributeRequirements(attribute, "myLeafWrapperList", String.class, true, true));
        assertThat(model.attributes().size()).isEqualTo(12);

        var childModel = model.attributes().get("myChildren").treeModel();
        assertThat(childModel.attributes().get("anIntegerObject")).satisfies(attribute ->
                attributeRequirements(attribute, "anIntegerObject", Integer.class, false, true));
        assertThat(childModel.attributes().get("aLongObject")).satisfies(attribute ->
                attributeRequirements(attribute, "aLongObject", Long.class, false, true));
        assertThat(childModel.attributes().get("aDoubleObject")).satisfies(attribute ->
                attributeRequirements(attribute, "aDoubleObject", Double.class, false, true));
        assertThat(childModel.attributes().get("aBooleanObject")).satisfies(attribute ->
                attributeRequirements(attribute, "aBooleanObject", Boolean.class, false, true));
        assertThat(childModel.attributes().size()).isEqualTo(4);
    }

    private void attributeRequirements(@NotNull Attribute<?, ?> attribute, @NotNull String expectedName,
                                       @NotNull Class<?> expectedType,
                                       boolean expectedCollection, boolean expectedLeaf) {
        assertThat(attribute.name()).isEqualTo(expectedName);
        assertThat(attribute.type()).isEqualTo(expectedType);
        assertThat(attribute.isCollection()).isEqualTo(expectedCollection);
        assertThat(attribute.isLeaf()).isEqualTo(expectedLeaf);
        assertThat(attribute.isTree()).isEqualTo(!expectedLeaf);
        if (!expectedLeaf) {
            assertThat(attribute.treeModel()).isNotNull();
        } else {
            assertThatThrownBy(attribute::treeModel).isInstanceOf(IllegalStateException.class);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void writeObject() {
        var model = TreeModel.of(TestParent.class);
        var object = new TestParent();
        object.myString = "foobar";
        object.myInteger = 123;
        object.myLong = 456L;
        object.myBoolean = true;
        object.myDouble = 3.14;
        object.myEnum = TestEnum.FOO;
        object.myChild = new TestChild(10, 20L, true, 123.456);
        object.myChildren = Set.of(
                new TestChild(1, 2L, true, 3.1),
                new TestChild(4, 5L, false, 6.1)
        );
        object.myStringList = List.of("hello", "world");
        object.myLeafWrapper = new TestLeafWrapper("wrapped string");
        object.myInstant = Instant.now();
        object.myLeafWrapperList = List.of(new TestLeafWrapper("first"), new TestLeafWrapper("second"));

        var writeModel = new MapReadWriteModel<TestParent>();
        model.writeObject(object, writeModel);

        assertThat(writeModel.map().get("myString")).isEqualTo(object.myString);
        assertThat(writeModel.map().get("myInteger")).isEqualTo(object.myInteger);
        assertThat(writeModel.map().get("myLong")).isEqualTo(object.myLong);
        assertThat(writeModel.map().get("myBool")).isEqualTo(object.myBoolean);
        assertThat(writeModel.map().get("myDouble")).isEqualTo(object.myDouble);
        assertThat(writeModel.map().get("myEnum")).isEqualTo(object.myEnum);
        assertThat(writeModel.map().get("myLeafWrapper")).isEqualTo(object.myLeafWrapper.toString());
        assertThat(writeModel.map().get("myInstant")).isEqualTo(object.myInstant);
        assertThat(writeModel.map().get("myStringList")).isEqualTo(object.myStringList);
        assertThat(writeModel.map().get("myLeafWrapperList")).isEqualTo(List.of("first", "second"));
        assertThat(writeModel.map()).hasSize(12);

        var myChild = (Map<String, Object>) writeModel.map().get("myChild");
        assertThat(myChild.get("anIntegerObject")).isEqualTo(object.myChild.anIntegerObject);
        assertThat(myChild.get("aLongObject")).isEqualTo(object.myChild.aLongObject);
        assertThat(myChild.get("aBooleanObject")).isEqualTo(object.myChild.aBooleanObject);
        assertThat(myChild.get("aDoubleObject")).isEqualTo(object.myChild.aDoubleObject);
        assertThat(myChild).hasSize(4);

        var myChildren = (List<Map<String, Object>>) writeModel.map().get("myChildren");
        assertThat(myChildren).hasSize(2);
    }

    @Test
    public void readObject() {
        var readModel = new MapReadWriteModel<TestParent>();
        var now = Instant.now();
        readModel.map().put("myString", "foobar");
        readModel.map().put("myInteger", 123);
        readModel.map().put("myLong", 456L);
        readModel.map().put("myBool", true);
        readModel.map().put("myDouble", 3.14);
        readModel.map().put("myEnum", TestEnum.BAR);
        readModel.map().put("myChild", Map.of("anIntegerObject", 10, "aLongObject", 20L));
        readModel.map().put("myChildren", List.of(Map.of("aBooleanObject", true, "aDoubleObject", 3.1)));
        readModel.map().put("myStringList", List.of("hello", "world"));
        readModel.map().put("myLeafWrapper", "wrapped string");
        readModel.map().put("myLeafWrapperList", List.of("first", "second"));
        readModel.map().put("myInstant", now);

        var model = TreeModel.of(TestParent.class);
        var object = model.readObject(readModel);

        assertThat(object.myString).isEqualTo("foobar");
        assertThat(object.myInteger).isEqualTo(123);
        assertThat(object.myLong).isEqualTo(456L);
        assertThat(object.myBoolean).isTrue();
        assertThat(object.myDouble).isEqualTo(3.14);
        assertThat(object.myEnum).isEqualTo(TestEnum.BAR);
        assertThat(object.myChild.anIntegerObject).isEqualTo(10);
        assertThat(object.myChild.aLongObject).isEqualTo(20L);
        assertThat(object.myChild.aBooleanObject).isNull();
        assertThat(object.myChild.aDoubleObject).isNull();
        assertThat(object.myChildren).hasOnlyOneElementSatisfying(child -> {
            assertThat(child.aDoubleObject).isEqualTo(3.1);
            assertThat(child.aBooleanObject).isTrue();
            assertThat(child.aLongObject).isNull();
            assertThat(child.anIntegerObject).isNull();
        });
        assertThat(object.myStringList).containsExactly("hello", "world");
        assertThat(object.myLeafWrapper.toString()).isEqualTo("wrapped string");
        assertThat(object.myLeafWrapperList).satisfies(wrapper -> assertThat(wrapper.toString()).isEqualTo("first"), Index.atIndex(0));
        assertThat(object.myLeafWrapperList).satisfies(wrapper -> assertThat(wrapper.toString()).isEqualTo("second"), Index.atIndex(1));
        assertThat(object.myInstant).isEqualTo(now);
    }

    public enum TestEnum {
        FOO, BAR
    }

    public static class TestParent {
        @SerializableAttribute
        private String myString;
        @SerializableAttribute
        private int myInteger;
        @SerializableAttribute
        private long myLong;
        @SerializableAttribute(name = "myBool")
        private boolean myBoolean;
        @SerializableAttribute
        private double myDouble;
        @SerializableAttribute
        private TestEnum myEnum;
        @SerializableAttribute
        private TestChild myChild;
        @SerializableAttribute(type = TestChild.class)
        private Set<TestChild> myChildren;
        @SerializableAttribute(type = String.class)
        private List<String> myStringList;
        @SerializableAttribute
        private TestLeafWrapper myLeafWrapper;
        @SerializableAttribute
        private Instant myInstant;
        @SerializableAttribute(type = TestLeafWrapper.class)
        private List<TestLeafWrapper> myLeafWrapperList;
    }

    public static class TestChild {
        @SerializableAttribute
        private Integer anIntegerObject;
        @SerializableAttribute
        private Long aLongObject;
        @SerializableAttribute
        private Boolean aBooleanObject;
        @SerializableAttribute
        private Double aDoubleObject;
        private UUID aUUIDToBeIgnored;

        private TestChild() {
        }

        public TestChild(Integer anIntegerObject, Long aLongObject, Boolean aBooleanObject, Double aDoubleObject) {
            this.anIntegerObject = anIntegerObject;
            this.aLongObject = aLongObject;
            this.aBooleanObject = aBooleanObject;
            this.aDoubleObject = aDoubleObject;
        }
    }

    public static class TestLeafWrapper {
        private final String myChild;

        @LeafCreator
        public TestLeafWrapper(String myChild) {
            this.myChild = myChild;
        }

        @Override
        @LeafValue
        public String toString() {
            return myChild;
        }
    }
}
