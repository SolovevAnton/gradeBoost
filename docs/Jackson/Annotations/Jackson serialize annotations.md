```toc

```

This is [[Jackson main|Jackson annotations]] used to serialize objects. Here are only most used annotations

## @JsonInclude
The Jackson annotation `@JsonInclude` tells Jackson only to include properties under certain circumstances. For instance, that properties should only be included if they are non-null, non-empty, or have non-default values. Here is an example that shows how you can use the `@JsonInclude` annotation:
```java
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PersonInclude {
    public long  personId = 0;
    public String name = null;
}
```
This example will only include the `name` property if the value set for it is non-empty, meaning is not null and is not an empty string.
A more saying name for the `@JsonInclude` annotation could have been `@JsonIncludeOnlyWhen`, but that would have been longer to write.
And the values can be:
### Enum JsonInclude.Include

```java
ALWAYS //Value that indicates that property is to be always included, independent of value of the property.
CUSTOM //Value that indicates that separate filter Object (specified by JsonInclude.valueFilter() for value itself, and/or JsonInclude.contentFilter() for contents of structured types) is to be used for determining inclusion criteria.
NON_ABSENT //Value that indicates that properties are included unless their value is: null "absent" value of a referential type (like Java 8 `Optional`, or {link java.util.concurrent.atomic.AtomicReference}); that is, something that would not deference to a non-null value.
NON_DEFAULT //Meaning of this setting depends on context: whether annotation is specified for POJO type (class), or not.
NON_EMPTY //Value that indicates that only properties with null value, or what is considered empty, are not to be included.
NON_NULL //Value that indicates that only properties with non-null values are to be included.
USE_DEFAULTS //Pseudo-value used to indicate that the higher-level defaults make sense, to avoid overriding inclusion value.
```

## @JsonGetter
This is optional annotation
The @JsonGetter annotation is an alternative to the @JsonProperty annotation to mark a method as a getter method.
In the following example – we specify the method getTheName() as the
getter method of name property of MyBeanentity:

```java
public class MyBean {
public int id;
private String name;
@JsonGetter(“name”)
public String getTheName() {
return name;
}
}
```

## @JsonAnyGetter
The @JsonAnyGetter annotation allows the flexibility of using a Map field as standard properties.

When seeing the `@JsonAnyGetter` annotation Jackson will obtain the `Map` returned from the method which `@JsonAnyGetter` annotates, and will treat each key-value pair in that `Map` as a property. In other words, all key-value pairs in the `Map` will be serialized to JSON as part of the `PersonAnyGetter` object:
```java
public class ExtendableBean {
public String name;
private Map<String, String> properties;
@JsonAnyGetter
public Map<String, String> getProperties() {
return properties;
}
}
/*
out:
{
“name”:”My bean”,
“attr2”:”val2”,
“attr1”:”val1”
}
```

## @JsonPropertyOrder
We can use the @JsonPropertyOrder annotation to specify the order of
properties on serialization.

Example:
```java
@JsonPropertyOrder({ 
“name”, 
“id” 
})
public class MyBean {
public int id;
public String name;
}
/*
Out:
{
“name”:”My bean”,
“id”:1
}
```

We can also use @JsonPropertyOrder(alphabetic=true) to order the properties
alphabetically. And in that case the output of serialization will be:
```java
{
“id”:1,
“name”:”My bean”
}
```

## @JsonRawValue
The `@JsonRawValue` Jackson annotation tells Jackson that this property value should written directly as it is to the JSON output. If the property is a `String` Jackson would normally have enclosed the value in quotation marks, but if annotated with the `@JsonRawValue` property Jackson won't do that.

To make it more clear what `@JsonRawValue` does, look at this class without the `@JsonRawValue` in use:

```java
public class PersonRawValue {
    public long   personId = 0;
    public String address  = "$#";
}
/*
out:
{"personId":0,"address":"$#"}
```

Now we add the `@JsonRawValue` to the `address` property, like this:

```java
public class PersonRawValue {
    public long   personId = 0;
    @JsonRawValue
    public String address  = "$#";
}
```

Jackson will now omit the quotation marks when serializing the `address` property. The serialized JSON will thus look like this:

```
{"personId":0,"address":$#}
```

This is of course invalid JSON, so why would you want that?

Well, if the `address` property contained a JSON string then that JSON string would be serialized into the final JSON object as part of the JSON object structure, and not just into a string in the `address` field in the JSON object. To see how that would work, let us change the value of the `address` property like this:

```java
public class PersonRawValue {
    public long   personId = 0;
    @JsonRawValue
    public String address  =
            "{ \"street\" : \"Wall Street\", \"no\":1}";
}
```

Jackson would serialize this into this JSON:
```JSON
{"personId":0,"address":{ "street" : "Wall Street", "no":1}}
```

Notice how the JSON string is now part of the serialized JSON structure.

Without the `@JsonRawValue` annotation Jackson would have serialized the object to this JSON:
```JSON
{"personId":0,"address":"{ \"street\" : \"Wall Street\", \"no\":1}"}
```

Notice how the value of the `address` property is now enclosed in quotation marks, and all the quotation marks inside the value are escaped.

## @JsonValue
The Jackson annotation `@JsonValue` tells Jackson that Jackson should not attempt to serialize the object itself, but rather call a method on the object which serializes the object to a JSON string. Note that Jackson will escape any quotation marks inside the String returned by the custom serialization, so you cannot return e.g. a full JSON object. For that you should use `@JsonRawValue` instead (see previous section).

The `@JsonValue` annotation is added to the method that Jackson is to call to serialize the object into a JSON string. Here is an example showing how to use the `@JsonValue` annotation:
```java
public class PersonValue {
    public long   personId = 0;
    public String name = null;
    @JsonValue
    public String toJson(){
        return this.personId + "," + this.name;
    }
}
/*
OUT
"0,null"
```

The quotation marks are added by Jackson. Remember, any quotation marks in the value string returned by the object are escaped

## @JsonSerialize
The `@JsonSerialize` Jackson annotation is used to specify a custom serializer for a field in a Java object. Here is an example Java class that uses the `@JsonSerialize` annotation:

```java
public class PersonSerializer {
    public long   personId = 0;
    public String name     = "John";
    @JsonSerialize(using = OptimizedBooleanSerializer.class)
    public boolean enabled = false;
}
```

Notice the `@JsonSerialize` annotation above the `enabled` field.
The `OptimizedBooleanSerializer` will serialize a `true` value to `1` and a `false` value `0`. 
Here is the code:
```java
public class OptimizedBooleanSerializer extends JsonSerializer<Boolean> {
    @Override
    public void serialize(Boolean aBoolean, JsonGenerator jsonGenerator, 
        SerializerProvider serializerProvider) 
    throws IOException, JsonProcessingException {

        if(aBoolean){
            jsonGenerator.writeNumber(1);
        } else {
            jsonGenerator.writeNumber(0);
        }
    }
}
```

## Jackson polymorphic type handling annotations

the same as:
![[Jackson deserialize annotations#Jackson polymorphic type handling annotations]]