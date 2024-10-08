```toc

```

Annotations in [[Jackson main|Jackson]] used to tell Jackson that the Java object has a constructor (a "creator") which can match the fields of a JSON object to the fields of the Java object
Whiteout some of them Parser will not work

This annotations can be:
- [[Jackson serialize annotations|Serialization]] or write annotations, used to correctly serialize the object
- [[Jackson deserialize annotations]] or read annotations, used to deserialize the object
- **Global** annotations to configure Jackson properly for both operations

All Jackson annotations can be found here: *[Jackson annotations doc](https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-annotations/latest/index.html)*

Lower will be some of the most simple and used ones

## Global annotations
### @JsonProperty

This is **obligatory** information
We can add the @JsonProperty annotation to indicate the property name
in JSON.
Let’s use @JsonProperty to serialize/deserialize the property name when
we’re dealing with non-standard getters and setters:
```java
public class MyBean {
	public int id;
	private String name;
	@JsonProperty(“name”)
	public void setTheName(String name) {
		this.name = name;
	}
	@JsonProperty(“name”)
	public String getTheName() {
		return name;
	}
}
```

Our test:
```java
@Test
public void whenUsingJsonProperty_thenCorrect()
throws IOException {
	MyBean bean = new MyBean(1, “My bean”);
	String result = new ObjectMapper().writeValueAsString(bean);
	
	assertThat(result, containsString(“My bean”));
	assertThat(result, containsString(“1”));
	
	MyBean resultBean = new ObjectMapper()
		.readerFor(MyBean.class)
		.readValue(result);
	assertEquals(“My bean”, resultBean.getTheName());
}
```
### @JsonAlias
The _@JsonAlias_ defines one or more alternative names for a property during deserialization.

Let's see how this annotation works with a quick example:
```java
public class AliasBean {
    @JsonAlias({ "fName", "f_name" })
    private String firstName;   
    private String lastName;
}
```

Here we have a POJO, and we want to deserialize JSON with values such as _fName_, _f_name_, and _firstName_ into the _firstName_ variable of the POJO.

Below is a test making sure this annotation works as expected:
```java
@Test
public void whenDeserializingUsingJsonAlias_thenCorrect() throws IOException {
    String json = "{\"fName\": \"John\", \"lastName\": \"Green\"}";
    AliasBean aliasBean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);
    assertEquals("John", aliasBean.getFirstName());
}
```


> [!NOTE]
> When serializing Alias will use the java notation for object tags
Example:

IN:
```XML
            <Planet>
                <Name>Qwerer</Name>
                <Diameter>12500</Diameter>
                <OrbitalPeriod>11</OrbitalPeriod>
            </Planet>
```
OUT:
```XML
<Planet>
	<name>Qwerer</name>
	<radius>12500.0</radius>
	<orbitalPeriod>11.0</orbitalPeriod>
</Planet>
```


### @JsonFormat

The @JsonFormat annotation specifies a format when serializing Date/
Time values.
In the following example – we use @JsonFormat to control the format of the property eventDate:
```java
public class Event {
	public String name;
	@JsonFormat(
		shape = JsonFormat.Shape.STRING,
		pattern = "dd-MM-yyyy hh:mm:ss")
	public Date eventDate;
}
```

Without it dates WILL NOT be parsed and written correctly
### @JsonUnwrapped
@JsonUnwrapped defines values that should be unwrapped/flattened
when serialized/deserialized.
Let’s see exactly how that works; we’ll use the annotation to unwrap the
property name:
```java
public class UnwrappedUser {
	public int id;
	@JsonUnwrapped
	public Name name;
	
	public static class Name {
		public String firstName;
		public String lastName;
	}
}
```

Serialization:
```java
@Test
public void whenSerializingUsingJsonUnwrapped_thenCorrect()
throws JsonProcessingException, ParseException {
	UnwrappedUser.Name name = new UnwrappedUser.Name(“John”, “Doe”);
	UnwrappedUser user = new UnwrappedUser(1, name);
	
	String result = new ObjectMapper().writeValueAsString(user);
	
	assertThat(result, containsString(“John”));
	assertThat(result, not(containsString(“name”)));
}
```
Here’s how the output looks like – the fields of the static nested class
unwrapped along with the other field:

```json
{
	“id”:1,
	“firstName”:”John”,
	“lastName”:”Doe”
}
```

### Other
- **@JsonView** indicates the View in which the property will be included for serialization/deserialization.
- **@JsonManagedReferenc**e and **@JsonBackReference** annotations can handle parent/child relationships and work around loops.
- **@JsonIdentityInfo** indicates that Object Identity should be used when serializing/deserializing values – for instance, to deal with infinite recursion type of problems.
- **@JsonFilter** annotation specifies a filter to use during serialization. To include in serialization everything except some given filter
- 
## Read + Write Annotations

Jackson contains a set of annotations that affect both the reading of Java objects from JSON, as well as the writing of Java objects into JSON.

### @JsonIgnore

The Jackson annotation `@JsonIgnore` is used to tell Jackson to ignore a certain property (field) of a Java object. The property is ignored both when reading JSON into Java objects, and when writing Java objects into JSON. Here is an example class that uses the `@JsonIgnore` annotation:

```java
import com.fasterxml.jackson.annotation.JsonIgnore;
public class PersonIgnore {
    @JsonIgnore
    public long    personId = 0;
    public String  name = null;
}
```

In the above class the property `personId` will not be read from JSON or written to JSON.

### @JsonIgnoreProperties

to ignore all unknown struff use:
```java
@JsonIgnoreProperties(ignoreUnknown = true)
```
The `@JsonIgnoreProperties` Jackson annotation is used to specify a list of properties of a class to ignore. The `@JsonIgnoreProperties` annotation is placed above the class declaration instead of above the individual properties (fields) to ignore. Here is an example showing how to use the `@JsonIgnoreProperties` annotation:

```java
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"firstName", "lastName"})
public class PersonIgnoreProperties {
    public long    personId = 0;
    public String  firstName = null;
    public String  lastName  = null;
}
```

In this example both the properties `firstName` and `lastName` will be ignored because their names are listed inside the `@JsonIgnoreProperties` annotation declaration above the class declaration.

### @JsonIgnoreType

The `@JsonIgnoreType` Jackson annotation is used to mark a whole type (class) to be ignored everywhere that type is used. Here is an example that shows you how you could use the `@JsonIgnoreType` annotation:

```java
import com.fasterxml.jackson.annotation.JsonIgnoreType;

public class PersonIgnoreType {
    @JsonIgnoreType
    public static class Address {
        public String streetName  = null;
        public String houseNumber = null;
        public String zipCode     = null;
        public String city        = null;
        public String country     = null;
    }
    public long    personId = 0;
    public String  name = null;
    public Address address = null;
}
```

In the above example all `Address` instances will be ignored.

### @JsonAutoDetect

The Jackson annotation `@JsonAutoDetect` is used to tell Jackson to include properties which are not public, both when reading and writing objects. Here is an example class showing you how you can use the `@JsonAutoDetect` annotation:

```java
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class PersonAutoDetect 
{
    private long  personId = 123;
    public String name     = null;
}
```

The `JsonAutoDetect.Visibility` class contains constants matching the visibility levels in Java, meaning `ANY`, `DEFAULT`, `NON_PRIVATE`, `NONE`, `PROTECTED_AND_PRIVATE` and `PUBLIC_ONLY`.

## Jackson Serialization Annotations
 These annotations covered : [[Jackson serialize annotations]]
 
## Jackson Deserialization Annotations
 These annotations covered : [[Jackson deserialize annotations]]
 
## Jackson custom annotations
Next, let’s see how to create a custom Jackson annotation.
We can make use of the @JacksonAnnotationsInside annotation:
```java
@Retention(RetentionPolicy.RUNTIME)
	@JacksonAnnotationsInside
	@JsonInclude(Include.NON_NULL)
	@JsonPropertyOrder({ “name”, “id”, “dateCreated” })
	public @interface CustomAnnotation {}
```

Now, if we use the new annotation on an entity:
```java
@CustomAnnotation
public class BeanWithCustomAnnotation {
	public int id;
	public String name;
	public Date dateCreated;
}
```

We can see how it does combine the existing annotations into a simpler,
custom one that we can use as a shorthand:
```java
@Test
public void whenSerializingUsingCustomAnnotation_thenCorrect()
throws JsonProcessingException {
BeanWithCustomAnnotation bean
= new BeanWithCustomAnnotation(1, “My bean”, null);

String result = new ObjectMapper().writeValueAsString(bean);

assertThat(result, containsString(“My bean”));
assertThat(result, containsString(“1”));
assertThat(result, not(containsString(“dateCreated”)));
}
```

The output of the serialization process:
```json
{
“name”:”My bean”,
“id”:1
}
```

## Disable Jackson Annotation
We can disable all Jackson annotations. We can do this by disabling the *MapperFeature.USE_ANNOTATIONS* as in the following example:
```java
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ “name”, “id” })
public class MyBean {
	public int id;
	public String name;
}
```

Now, after disabling annotations, these should have no effect and the
defaults of the library should apply:

```java
@Test
public void whenDisablingAllAnnotations_thenAllDisabled()
throws IOException {
MyBean bean = new MyBean(1, null);
ObjectMapper mapper = new ObjectMapper();
mapper.disable(MapperFeature.USE_ANNOTATIONS);

String result = mapper.writeValueAsString(bean);

assertThat(result, containsString(“1”));
assertThat(result, containsString(“name”));
}
```

The result of serialization before disabling annotations:
```json
{“id”:1}
```
The result of serialization after disabling annotations:
```json
{
“id”:1,
“name”:null
}
```

## Important Annotations of other Parsers
### XML
#### @JacksonXmlElementWrapper
This is the annotation to work with list in XML:
There are three attributes:

namespace
localName - to set local name for wrapper ( wrapper Plants, LocalName Planet)
useWrapping - to set if wrapping is used or not:


Optional property that can be used to explicitly disable wrapping, usually via mix-ins, or when using AnnotationIntrospector pairs.

Using example:
The _XmlMapper_ is able to serialize an entire Java bean into a document. To convert a Java object to XML, we'll take a simple example with a nested object and arrays.

Our intent is to serialize a _Person_ object, along with its composed _Address_ object, into XML.

Our final XML will look something like:

```xml
<Person>
    <firstName>Rohan</firstName>
    <lastName>Daye</lastName>
    <phoneNumbers>
        <phoneNumbers>9911034731</phoneNumbers>
        <phoneNumbers>9911033478</phoneNumbers>
    </phoneNumbers>
    <address>
        <streetName>Name1</streetName>
        <city>City1</city>
    </address>
    <address>
        <streetName>Name2</streetName>
        <city>City2</city>
    </address>
</Person>
```

Notice that our phone numbers are encapsulated in a _phoneNumbers_ wrapper, while our address isn't.

We can express this nuance via the _@JacksonXMLElementWrapper_ annotation in our _Person_ class:

```java
public final class Person {
    private String firstName;
    private String lastName;
    private List<String> phoneNumbers = new ArrayList<>();
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Address> address = new ArrayList<>();

    //standard setters and getters
}
```

In fact, we can change the wrapping element name with _@JacksonXmlElementWrapper(localName = ‘phoneNumbers')._ Or, if we don't want to wrap our elements, we can disable the mapping with _@JacksonXmlElementWrapper(useWrapping = false)_.

Then we'll define our _Address_ type:

```java
public class Address {
    String streetName;
    String city;
    //standard setters and getters
}
```

**Jackson takes care of the rest for us.** Like before, we can simply call _writeValue_ again:

```java
private static final String XML = "<Person>...</Person>";

@Test
public void whenJavaSerializedToXmlFile_thenSuccess() throws IOException {
    XmlMapper xmlMapper = new XmlMapper();
    Person person = testPerson(); // test data
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    xmlMapper.writeValue(byteArrayOutputStream, person); 
    assertEquals(XML, byteArrayOutputStream.toString()); 
}
```
