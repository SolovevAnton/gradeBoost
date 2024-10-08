```toc

```
## Theory
ObjectMapper is the main actor class of [[Jackson main|Jackson library]]. 
ObjectMapper class ObjectMapper provides functionality for reading and writing [[JavaScript Object Notation (JSON)|JSON]], either to and from basic POJOs (Plain Old Java Objects), or to and from a general-purpose JSON Tree Model (JsonNode), as well as related functionality for performing conversions. 
It is also highly customizable to work both with different styles of JSON content, and to support more advanced Object concepts such as polymorphism and Object identity. 
ObjectMapper also acts as a factory for more advanced ObjectReader and ObjectWriter classes.

It also has some *[Official javaDoc](https://www.javadoc.io/doc/com.fasterxml.jackson.core/jackson-databind/2.9.8/com/fasterxml/jackson/databind/ObjectMapper.html)*


> [!important]
> 1. If you add some additional module, first you should *register* it, otherwise it will throw
> 2. When use Generics remember to use NOT the *.class* method, but  `TypeReference<>() {}`
> 3.  Default constructor, getters and setters are ==required== in ObjectMapper JSON Parser 

## ObjectMapper creation

The class is a part of a package:
```java
import com.fasterxml.jackson.databind.ObjectMapper;
```

The class itself have a defection:
```java
public class ObjectMapper
   extends ObjectCodec
      implements Versioned, Serializable
```

It has nested classes:
**static class ObjectMapper.DefaultTypeResolverBuilder**
Customized TypeResolverBuilder that provides type resolver builders used with so-called "default typing" (see enableDefaultTyping() for details).

**static class ObjectMapper.DefaultTyping**
Enumeration used with enableDefaultTyping() to specify what kind of types (classes) default typing should be used for.

## Fields of Object Mapper

all have [[Access control modifiers in java scope.png|protected]] modifier

```java
protected ConfigOverrides 	_configOverrides //Currently active per-type configuration overrides, accessed by declared type of property.
protected DeserializationConfig 	_deserializationConfig //Configuration object that defines basic global settings for the serialization process
protected DefaultDeserializationContext 	_deserializationContext //Blueprint context object; stored here to allow custom sub-classes.
protected InjectableValues 	_injectableValues //Provider for values to inject in deserialized POJOs.
protected JsonFactory 	_jsonFactory //Factory used to create JsonParser and JsonGenerator instances as necessary.
protected SimpleMixInResolver 	_mixIns //Mapping that defines how to apply mix-in annotations: key is the type to received additional annotations, and value is the type that has annotations to "mix in".
protected Set<Object> 	_registeredModuleTypes //Set of module types (as per Module.getTypeId() that have been registered; kept track of iff MapperFeature.IGNORE_DUPLICATE_MODULE_REGISTRATIONS is enabled, so that duplicate registration calls can be ignored (to avoid adding same handlers multiple times, mostly).
protected ConcurrentHashMap<JavaType,JsonDeserializer<Object>> 	_rootDeserializers //We will use a separate main-level Map for keeping track of root-level deserializers.
protected SerializationConfig 	_serializationConfig //Configuration object that defines basic global settings for the serialization process
protected SerializerFactory 	_serializerFactory //Serializer factory used for constructing serializers.
protected DefaultSerializerProvider 	_serializerProvider //Object that manages access to serializers used for serialization, including caching.
protected SubtypeResolver 	_subtypeResolver //Thing used for registering sub-types, resolving them to super/sub-types as needed.
protected TypeFactory 	_typeFactory //Specific factory used for creating JavaType instances; needed to allow modules to add more custom type handling (mostly to support types of non-Java JVM languages)
protected static AnnotationIntrospector 	DEFAULT_ANNOTATION_INTROSPECTOR 
protected static BaseSettings 	DEFAULT_BASE //Base settings contain defaults used for all ObjectMapper instances.
```

## Constructors

```java
ObjectMapper() //Default constructor, which will construct the default JsonFactory as necessary, use SerializerProvider as its SerializerProvider, and BeanSerializerFactory as its SerializerFactory.
ObjectMapper(JsonFactory jf) //Constructs instance that uses specified JsonFactory for constructing necessary JsonParsers and/or JsonGenerators.
ObjectMapper(JsonFactory jf, DefaultSerializerProvider sp, DefaultDeserializationContext dc) //Constructs instance that uses specified JsonFactory for constructing necessary JsonParsers and/or JsonGenerators, and uses given providers for accessing serializers and deserializers.
protected 	ObjectMapper(ObjectMapper src) //Copy-constructor, mostly used to support copy().
```

## Methods of the class
### Static methods

```java
static List<Module> 	findModules() //Method for locating available methods, using JDK ServiceLoader facility, along with module-provided SPI.
static List<Module> 	findModules(ClassLoader classLoader) //Method for locating available methods, using JDK ServiceLoader facility, along with module-provided SPI.
```

### Main Methods
There are a lot of methods. Look for them in *[Official javaDoc](https://www.javadoc.io/doc/com.fasterxml.jackson.core/jackson-databind/2.9.8/com/fasterxml/jackson/databind/ObjectMapper.html)*
Main Methods of the class are:
```java
boolean 	canDeserialize(JavaType type) //Method that can be called to check whether mapper thinks it could deserialize an Object of given type.
boolean 	canDeserialize(JavaType type, AtomicReference<Throwable> cause) //Method similar to canDeserialize(JavaType) but that can return actual Throwable that was thrown when trying to construct serializer: this may be useful in figuring out what the actual problem is.
boolean 	canSerialize(Class<?> type) //Method that can be called to check whether mapper thinks it could serialize an instance of given Class.
boolean 	canSerialize(Class<?> type, AtomicReference<Throwable> cause) //Method similar to canSerialize(Class) but that can return actual Throwable that was thrown when trying to construct serializer: this may be useful in figuring out what the actual problem is.
```

```java
ObjectMapper 	clearProblemHandlers() //Method for removing all registered DeserializationProblemHandlers instances from this mapper.
```
Value conversion:
```java
<T> T 	convertValue(Object fromValue, Class<T> toValueType) //Convenience method for doing two-step conversion from given value, into instance of given value type, if (but only if!) conversion is needed.
<T> T 	convertValue(Object fromValue, JavaType toValueType) //See convertValue(Object, Class)
<T> T 	convertValue(Object fromValue, TypeReference<?> toValueTypeRef) //See convertValue(Object, Class)
```

Copy:
```java
ObjectMapper 	copy() //Method for creating a new ObjectMapper instance that has same initial configuration as this 
```

There are a lot of methods to config the mapper. They all Modifies and returns this instance; no new object is created.
One of the examples, but there are more of them:
```java
ObjectMapper 	disable(SerializationFeature first, SerializationFeature... f) //Method for enabling specified DeserializationConfig features.
ObjectMapper 	disableDefaultTyping() //Method for disabling automatic inclusion of type information; if so, only explicitly annotated types (ones with JsonTypeInfo) will have additional embedded type information.
ObjectMapper 	enable(DeserializationFeature feature) //Method for enabling specified DeserializationConfig features.
```

#### Methods to create ObjectReader:
```java
ObjectReader reader() //they all have the same return type
Similar Methods:

    reader(Base64Variant defaultBase64)
    reader(Class<?> type) //Depricated
    reader(ContextAttributes attrs)
    reader(DeserializationFeature feature)
    reader(DeserializationFeature first, DeserializationFeature... other)
    reader(FormatSchema schema)
    reader(InjectableValues injectableValues)
    reader(JavaType type)
    reader(JsonNodeFactory f)
    reader(TypeReference<?> type)
```
Also there is a method *readerFor(Class`<?>` type)* - It is a factory method that is used to construct ObjectReader, allowing read or update operations for instances of the specified type

The reader() method and its variations are the factory methods that are used to construct an instance of ObjectReader. Based on the requirement, we can pass the Base64 encoding variant for Base64-encoded binary data, default attributes, features enabled, injectable values, JsonNodeFactory for constructing JSON trees.

#### Methods to deserialize Object

**readTree()** and its variations are used to de-serialize the JSON content as a tree expressed using a set of JsonNode instances.
```java
JsonNode  /*OR*/  <T extends TreeNode> T readTree(byte[] content)  

1.  readTree(File file)
2.  readTree(InputStream in)
3.  readTree(JsonParser p)
4.  readTree(Reader r)
5.  readTree(String content)
6.  readTree(URL source)
```

**readValue()** and its variations are used to de-serialize JSON content  
1.  From the given file into a given Java type, into a non-container type, or into a Java type reference to which it is passed as an argument.
2.  From given JSON content into String.
3.  From given resource into given Java type.

Variations are used based on different requirements.

```java
<T> T   OR   <T> MappingIterator<T> readValue(byte[] src, Class<T> valueType)  
Similar Methods  

1.  readValue(byte[] src, int offset, int len, JavaType valueType)
2.  readValue(byte[] src, int offset, int len, Class<T> valueType)
3.  readValue(byte[] src, JavaType valueType)
4.  readValue(DataInput src, Class<T> valueType)
5.  readValue(File src, Class<T> valueType)
6.  readValue(DataInput src, JavaType valueType)
7.  readValue(File src, JavaType valueType)
8.  readValue(File src, TypeReference valueTypeRef)
9.  readValue(InputStream src, JavaType valueType)
10.  readValue(byte[] src, TypeReference valueTypeRef)
11.  readValue(InputStream src, Class<T> valueType)
12.  readValue(InputStream src, TypeReference valueTypeRef)
13.  readValue(JsonParser p, JavaType valueType)
14.  readValue(JsonParser p, Class<T> valueType)
15.  readValue(JsonParser p, ResolvedType valueType)
16.  readValue(byte[] src, int offset, int len, TypeReference valueTypeRef)
17.  readValue(Reader src, TypeReference valueTypeRef)
18.  readValue(JsonParser p, TypeReference<?> valueTypeRef)
19.  readValue(Reader src, JavaType valueType)
20.  readValue(String content, TypeReference valueTypeRef)
21.  readValue(Reader src, Class<T> valueType)
22.  readValue(String content, Class<T> valueType)
23.  readValue(String content, JavaType valueType)
24.  readValue(URL src, TypeReference valueTypeRef)readValue(URL src, Class<T> valueType)
25.  readValue(URL src, JavaType valueType)
```

**readValues()** and its variations are used to read the sequence of Objects from the parser stream.
```java
<T> MappingIterator<T>  readValues(JsonParser p, Class<T> valueType)  
Similar methods:  

1.  readValues(JsonParser p, JavaType valueType)
2.  readValues(JsonParser p, ResolvedType valueType)
3.  readValues(JsonParser p, TypeReference<?>valueTypeRef)
```


> [!warning] If type reference is used to Collection interface it will create array list
> ```java
> private Collecton`<IdHolder>` values = new HashSet`<>`();  
> values = objectMapper.readValue(fileToStoreData, new TypeReference`<>`() {  
> });
> ```
> THIS WILL RESULT IN VALUES BEING ARRAY LIST!
> So this:
> ```java
> values.add(1);
> values.add(1);
> ```
> will result in:
> `values = [1,1]` not `values = [1]` as expected!


**Example:**
```java
this.tv = objectMapper.readValue(new File(fileName), TV.class); //for single instance
this.tvs = objectMapper.readValue(bufferedInputStream, TV[].class); //for array of objects
this.tvs = objectMapper.readValue(bufferedReader, new TypeReference<>() {}); //for List<TV> observe how TypeReference is used for Generics
```

or, to create a Map:
```java
String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
Map<String, Object> map
= objectMapper.readValue(json, new
TypeReference<Map<String,Object>>(){});
/*
OUT:
{color=Black, type=BMW}
```

If the field that Jackson is trying to read from a file is missing, by default it will be given null value:
```java
File file = new File("CorruptedUsers.json");  
UsersRepository corruptedUsersInRep = new UsersRepository(file.toURL());  
System.out.println(corruptedUsersInRep);
/*
OUT:
UsersRepository{users=[user{id=0, name='null', username='null', email='null', address=Address{street='Victor Plains', suite='Suite 879', city= ...etc
```
#### To serialize object

| Method                                           | Return Type | Description                                                                          |
| ------------------------------------------------ | ----------- | ------------------------------------------------------------------------------------ |
| writeTree(JsonGenerator jgen, JsonNode rootNode) | void        | It is used for serializing the provided JSON Tree by using the given JsonGenerator.  |
| writeValue(File resultFile, Object value)        | void        | It is used to serialize Java value as JSON output and write it to the given file.    |
| writeValue(JsonGenerator g, Object value).       | void        | It is used to serialize Java values as JSON output by using the given JsonGenerator. |
| writeValue(OutputStream out, Object value)       | void        | It is used to serialize Java value as JSON output by using the given OutputStream.   |
| writeValue(Writer w, Object value)               | void        | It is used to serialize Java values as JSON output by using the given Writer.        |
| writeValueAsBytes(Object value)                  | byte[]      | It is used for serializing Java values as a byte array.                              |
|writeValueAsString(Object value)| String|

Method that can be used to serialize any Java value as a String.|

**Example**
```java
public void save(String fileName) throws IOException {  
    objectMapper.writeValue(new File(fileName), tv);  
}
```

#### To create Object Writer

**write()** and its variations are used to construct ObjectWriter with default settings, given Base64 encoding variant for Base64-encoded binary data, character escaping details for output, default attributes, DateFormat, filter provider, schema object to JsonGenerator used for writing content, pretty printer for indentation, or feature enabled.
```java
ObjectWriter writer()  

1.  writer(Base64Variant defaultBase64)
2.  writer(CharacterEscapes escapes)
3.  writer(ContextAttributes attrs)
4.  writer(DateFormat df)
5.  writer(FilterProvider filterProvider)
6.  writer(FormatSchema schema)
7.  writer(PrettyPrinter pp)
8.  writer(SerializationFeature feature)
9.  writer(SerializationFeature first, SerializationFeature... other)
```
There is also `ObjectWriter writerFor(Class<?> rootType)` It is used to construct ObjectWriter for serializing the objects using a specified root type instead of the actual runtime type of value.
Other method
```java
writerWithView(Class<?> serializationView) ObjectWriter //It is a factory method that is used to construct ObjectWriter for serializing objects using specified JSON View.
```

#### To register modules
To have some module of Jackson to work, first it have to be registered. And you should do it only once. Good work for: [[Initializers and their order in Java|dynamic initializers]]
The registerModule() and its variations are used to register a module having the capability to extend the functionality provided by the mapper.
```java
ObjectMapper registerModule(Module module)  
Similar methods:  

1.  registerModules(Iterable<Module> modules)
2.  registerModules(Module... modules)
```

Also can be useful:
```java
ObjectMapper 	findAndRegisterModules() //Convenience method that is functionally equivalent to: mapper.registerModules(mapper.findModules()); 
```

Example for date usage:
```java
public class TVArrayRepository {
//some fields and methods
{    //TODO для работы с LocalDateTime  
    this.objectMapper.registerModule(new JavaTimeModule());  
}
}
```
