```toc

```

This is [[Jackson main|Jackson annotations]] used to deserialize objects. Here are only most used annotations
Jackson contains a set of annotations that only affect how Jackson parses JSON into objects - meaning they affect Jackson's reading of JSON. I refer to these as "read annotations". The following sections explains Jackson's read annotations.

### @JsonAlias
![[Jackson Annotations Main#@JsonAlias]]

### @JsonSetter

The Jackson annotation `@JsonSetter` is used to tell Jackson that is should match the name of this setter method to a property name in the JSON data, when reading JSON into objects. This is useful if the property names used internally in your Java class is not the same as the property name used in the JSON file.

The following `Person` class uses the name `personId` for its id property:

```java
public class Person {
    private long   personId = 0;
    private String name     = null;
    public long getPersonId() { return this.personId; }
    public void setPersonId(long personId) { this.personId = personId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

But in this JSON object the name `id` is used instead of `personId`:

```JSON
{
  "id"   : 1234,
  "name" : "John"
}
```

Without some help Jackson cannot map the `id` property from the JSON object to the `personId` field of the Java class.

The `@JsonSetter` annotation instructs Jackson to use a setter method for a given JSON field. In our case we add the `@JsonSetter` annotation above the `setPersonId()` method. Here is how adding the `@JsonSetter` annotation looks like:

```java
public class Person {
    private long   personId = 0;
    private String name     = null;
    public long getPersonId() { return this.personId; }
    @JsonSetter("id")
    public void setPersonId(long personId) { this.personId = personId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```
The value specified inside the `@JsonSetter` annotation is the name of the JSON field to match to this setter method. In this case the name is `id` since that is the name of the field in the JSON object we want to map to the `setPersonId()` setter method.

### @JsonAnySetter
The Jackson annotation `@JsonAnySetter` instructs Jackson to call the same setter method for all unrecognized fields in the JSON object. By "unrecognized" I mean all fields that are not already mapped to a property or setter method in the Java object. Look at this `Bag` class:
```java
public class Bag {
    private Map<String, Object> properties = new HashMap<>();
    public void set(String fieldName, Object value){
        this.properties.put(fieldName, value);
    }
    public Object get(String fieldName){
        return this.properties.get(fieldName);
    }
}
```

And then look at this JSON object:
```json
{
  "id"   : 1234,
  "name" : "John"
}
```
Jackson cannot directly map the `id` and `name` property of this JSON object to the `Bag` class, because the `Bag` class contains no public fields or setter methods.
You can tell Jackson to call the `set()` method for all unrecognized fields by adding the `@JsonAnySetter` annotation, like this:

```java
public class Bag {
    private Map<String, Object> properties = new HashMap<>();
    @JsonAnySetter
    public void set(String fieldName, Object value){
        this.properties.put(fieldName, value);
    }
    public Object get(String fieldName){
        return this.properties.get(fieldName);
    }
}
```

Now Jackson will call the `set()` method with the name and value of all unrecognized fields in the JSON object.

Keep in mind that this only has effect on _unrecognized_ fields. If, for instance, you added a public `name` property or `setName(String)` method to the `Bag` Java class, then the `name` field in the JSON object would be mapped to that property / setter instead.

### @JsonCreator

The Jackson annotation `@JsonCreator` is used to tell Jackson that the Java object has a constructor (a "creator") which can match the fields of a JSON object to the fields of the Java object.

The `@JsonCreator` annotation is useful in situations where the `@JsonSetter` annotation cannot be used. For instance, immutable objects do not have any setter methods, so they need their initial values injected into the constructor. Look at this `PersonImmutable` class as example:

```java
public class PersonImmutable {
    private long   id   = 0;
    private String name = null;
    public PersonImmutable(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
```

To tell Jackson that it should call the constructor of `PersonImmutable` we must add the `@JsonCreator` annotation to the constructor. But that alone is not enough. We must also annotate the parameters of the constructor to tell Jackson which fields from the JSON object to pass to which constructor parameters. Here is how the `PersonImmutable` class looks with the `@JsonCreator` and `@JsonProperty` annotations added:

```java
public class PersonImmutable {
    private long   id   = 0;
    private String name = null;
    @JsonCreator
    public PersonImmutable(
            @JsonProperty("id")  long id,
            @JsonProperty("name") String name  ) {
        this.id = id;
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
```

Notice the annotation above the constructor and the annotations before the constructor parameters. Now Jackson is capable of creating a `PersonImmutable` from this JSON object:

```java
{
  "id"   : 1234,
  "name" : "John"
}
```

### @JacksonInject

The Jackson annotation `@JacksonInject` is used to inject values into the parsed objects, instead of reading those values from the JSON. For instance, imagine you are downloading person JSON objects from various different sources, and would like to know what source a given person object came from. The sources may not themselves contain that information, but you can have Jackson inject that into the Java objects created from the JSON objects.

To mark a field in a Java class as a field that needs to have its value injected by Jackson, add the `@JacksonInject` annotation above the field. Here is an example `PersonInject` class with the `@JacksonInject` annotation added above the `source` field:

```java
public class PersonInject {
    public long   id   = 0;
    public String name = null;
    @JacksonInject
    public String source = null;
}
```

In order to have Jackson inject values into the `source` field you need to do a little extra when creating the Jackson `ObjectMapper`. Here is what it takes to have Jackson inject values into the Java objects:
```java
InjectableValues inject = new
	InjectableValues.Std().addValue(String.class, "jenkov.com");
PersonInject personInject = new ObjectMapper().reader(inject)
                        .forType(PersonInject.class)
                        .readValue(new File("data/person.json"));
```

Notice how the value to inject into the `source` attribute is set in the `InjectableValues` `addValue()` method. Notice also that the value is only tied to the type `String` - not to any specific field name. It is the `@JacksonInject` annotation that specifies what field the value is to be injected into.

If you were to download person JSON objects from multiple sources and have a different source value injected for each source, you would have to repeat the above code for each source.

### @JsonDeserialize

The Jackson annotation `@JsonDeserialize` is used to specify a custom de-serializer class for a given field in a Java object. For instance, imagine you wanted to optimize the on-the-wire formatting of the boolean values `false` and `true` to `0` and `1`.

First you would need to add the `@JsonDeserialize` annotation to the field you want to use the custom deserializer for. Here is how adding the `@JsonDeserialize` annotation to a field looks like:

```java
public class PersonDeserialize {
    public long    id      = 0;
    public String  name    = null;
    @JsonDeserialize(using = OptimizedBooleanDeserializer.class)
    public boolean enabled = false;
}
```

Second, here is what the `OptimizedBooleanDeserializer` class referenced from the `@JsonDeserialize` annotation looks like:

```java
public class OptimizedBooleanDeserializer
    extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws
        IOException, JsonProcessingException {
        String text = jsonParser.getText();
        if("0".equals(text)) return false;
        return true;
    }
}
```

Notice that the `OptimizedBooleanDeserializer` class extends `JsonDeserializer` with the generic type `Boolean` . Doing so makes the `deserialize()` method return a `Boolean` object. If you were to deserialize another type (e.g a `java.util.Date`) you would have to specify that type inside the generics brackets.

You obtain the value of the field to deserialize by calling the `getText()` method of the `jsonParser` parameter. You can then deserialize that text into whatever value and type your deserializer is targeted at (a `Boolean` in this example).

Finally you need to see what it looks like to deserialize an object with a custom deserializer and the `@JsonDeserializer` annotation:
```java
PersonDeserialize person = objectMapper
        .reader(PersonDeserialize.class)
        .readValue(new File("data/person-optimized-boolean.json"));
```
Notice how we first need to create a reader for the `PersonDeserialize` class using the `ObjectMapper`'s `reader()` method, and then we call `readValue()` on the object returned by that method.

## Jackson polymorphic type handling annotations

This is the annotations to properly parse subclasses:
- *@JsonTypeInfo* – indicates details of what type information to include in
serialization
• *@JsonSubTypes* – indicates sub-types of the annotated type
• *@JsonTypeName* – defines a logical type name to use for annotated class

Let’s look at a more complex example and use all three – @JsonTypeInfo,
@JsonSubTypes, and @JsonTypeName – to serialize/deserialize the entity
Zoo:

```java
public class Zoo {
	public Animal animal;
	@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = As.PROPERTY,
		property = “type”)
	@JsonSubTypes({
		@JsonSubTypes.Type(value = Dog.class, name = “dog”),
		@JsonSubTypes.Type(value = Cat.class, name = “cat”)
	})
	public static class Animal {
		public String name;
	}
	@JsonTypeName(“dog”)
		public static class Dog extends Animal {
		public double barkVolume;
	}
	@JsonTypeName(“cat”)
	public static class Cat extends Animal {
		boolean likesCream;
		public int lives;
	}
}
```

Serialization:

```java
@Test
public void whenSerializingPolymorphic_thenCorrect()
throws JsonProcessingException {
	Zoo.Dog dog = new Zoo.Dog(“lacy”);
	Zoo zoo = new Zoo(dog);
	
	String result = new ObjectMapper()
		.writeValueAsString(zoo);
	assertThat(result, containsString(“type”));
	assertThat(result, containsString(“dog”));
```

Here’s what serializing the Zoo instance with the Dog will result in:
```JSON
{
“animal”: {
“type”: “dog”,
“name”: “lacy”,
“barkVolume”: 0
}
}
```

Now for de-serialization – let’s start with the following JSON input:
```json
{
	“animal”:{
	“name”:”lacy”,
	“type”:”cat”
	}
}
```

And let’s see how that gets unmarshalled to a Zoo instance:
```java
@Test
public void whenDeserializingPolymorphic_thenCorrect()
throws IOException {
String json =
	“{\”animal\”:{\”name\”:\”lacy\”,\”type\”:\”cat\”}}”;
	Zoo zoo = new ObjectMapper()
		.readerFor(Zoo.class)
		.readValue(json);
assertEquals(“lacy”, zoo.animal.name);
assertEquals(Zoo.Cat.class, zoo.animal.getClass());
}
```
