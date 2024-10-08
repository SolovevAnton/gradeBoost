```toc

```

## Theory
Jackson is a simple [[Java main|java]] based library to serialize java objects to [[JavaScript Object Notation (JSON)|JSON]] and vice versa.

Overall guide of *[Jackson Usage](https://www.google.com/search?q=big+int+java&client=firefox-b-d&sxsrf=APwXEdeovhQecjVOjCZP7hY6UpVCXgmHsw%3A1682680171150&ei=a6lLZPbQCKjisAe-i7jwAg&ved=0ahUKEwi2svyOuMz-AhUoMewKHb4FDi4Q4dUDCA8&uact=5&oq=big+int+java&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIGCAAQBxAeMgYIABAHEB4yBggAEAcQHjIICAAQBxAeEAoyCAgAEAcQHhAKMggIABAHEB4QCjIICAAQBxAeEAoyCAgAEAcQHhAKMgYIABAHEB4yCAgAEAcQHhAKOgoIABBHENYEELADSgQIQRgAUJ4JWKYKYOELaAFwAXgAgAF3iAHZAZIBAzEuMZgBAKABAcgBCMABAQ&sclient=gws-wiz-serp)*

### Features
-   **Easy to use.** - jackson API provides a high level façade to simplify commonly used use cases. 
-   **No need to create mapping.** - jackson API provides default mapping for most of the objects to be serialized.
-   **Performance.** - jackson is quiet fast and is of low memory footprint and is suitable for large object graphs or systems.
-   **Clean JSON.** - jackson creates a clean and compact JSON results which is easy to read.
-   **No Dependency.** - jackson library does not require any other library apart from jdk.
-   **Open Source** - jackson library is open source and is free to use.

### Three ways of processing JSON

Jackson provides three alternative ways to process JSON
-   **Streaming API** - reads and writes JSON content as discrete events. JsonParser reads the data whereas JsonGenerator writes the data. It is most powerful approach among the three and is of lowest overhead and fastest in read/write opreations. It is Analogus to Stax parser for XML.
-   **Tree Model** - prepares a in-memory tree representation of the JSON document. ObjectMapper build tree of JsonNode nodes. It is most flexible approach. It is analogus to DOM parser for XML.
-   **Data Binding** - converts JSON to and from POJO (Plain Old Java Object) using property accessor or using annotations. It is of two type.
    -   **Simple Data Binding** - Converts JSON to and from Java Maps, Lists, Strings, Numbers, Booleans and null objects.
    -   **Full Data Binding** - Converts JSON to and from any JAVA type.
        ObjectMapper reads/writes JSON for both types of data bindings. Data Binding is most convenient way and is analogus to JAXB parer for XML.

ObjectMapper reads/writes JSON for both types of data bindings. Data Binding is most convenient way and is analogus to JAXB parer for XML.

## Start working with Jackson

To connect the library to your Maven based project tool add dependency in pom.xml file

```XML
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->  
<dependency>  
    <groupId>com.fasterxml.jackson.core</groupId>  
    <artifactId>jackson-databind</artifactId>  
    <version>2.14.2</version>  
</dependency>
```


> [!error] 
> All dependencies must be the same version as Jackson core. Else will throw: **NoSuchMethodError**

If you need time working module: `LocalDateTime` and etc, add dependency:
```XML
<dependencies>
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310 -->  
<dependency>  
    <groupId>com.fasterxml.jackson.datatype</groupId>  
    <artifactId>jackson-datatype-jsr310</artifactId>  
    <version>2.14.2</version>  
</dependency>
</dependencies>
```

and dont forget to add in repository:
```java
{  
    //TODO для работы с LocalDateTime  
    this.objectMapper.registerModule(new JavaTimeModule());  
}
```

To serialize/deserialize object use:
Create ObjectMapper object. It is a reusable object.
```java
ObjectMapper mapper = new ObjectMapper();
```

Step 2: DeSerialize JSON to Object.

Use readValue() method to get the Object from the JSON. Pass json string/ source of json string and object type as parameter.
```java
//Object to JSON Conversion
Student student = mapper.readValue(jsonString, Student.class);
```

Step 3: Serialize Object to JSON.
Use writeValueAsString() method to get the JSON string representation of an object.
```java
//Object to JSON Conversion		
jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(studenet)
```


### Additional Formats

#### CSV files and TXT
Dependency
```xml
    <dependency>
        <groupId>com.fasterxml.jackson.dataformat</groupId>
        <artifactId>jackson-dataformat-csv</artifactId>
        <version>2.12.0</version>
    </dependency>
```
Object mapper:
*[CvsMapper](https://javadoc.io/doc/com.fasterxml.jackson.dataformat/jackson-dataformat-csv/latest/index.html)*
Example:
```java
/**  
 * Schema used to parse this csv */
 private CsvSchema schema = CsvSchema.emptySchema()  
        .withColumnSeparator(';')  
        .withHeader()  
        .withSkipFirstDataRow(true);  
/**  
 * CSV mapper with this schema */
 private ObjectReader objectReader = new CsvMapper()  
        .findAndRegisterModules()  
        .readerFor(BoardingPass.class)  
        .with(schema);
```


> [!hint]
> Txt files with CSV like structure can be parsed with CVS mapper!

usefull about writing in cvs: https://cowtowncoder.medium.com/writing-csv-with-jackson-204fdb3c9dac

#### XML
Dependency:
```XML
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.11.1</version>
</dependency>
```

Object mapper:
XmlMapper inherits methods from the object mapper
The only difference is with Lists. and some annotations. For example for the property use *@JacksonXmlProperty*

For lists to work check [[Jackson Annotations Main#@JacksonXmlElementWrapper|@JacksonXmlElementWrapper]] annotation.
Or short example:
Gaxaly:
```XML
<?xml version="1.0" encoding="UTF-8"?>  
    <Galaxy>  
        <Name>GalaxyOne</Name>  
        <Planets>  
            <Planet>  
                <Name>Qwerer</Name>  
                <Diameter>12500</Diameter>  
            <OrbitalPeriod>11</OrbitalPeriod>  
            </Planet>  
            <Planet>  
                <Name>Yogasana Vijnana: the Science of Yoga</Name>  
                <Diameter>15000</Diameter>  
            <OrbitalPeriod>24</OrbitalPeriod>  
            </Planet>  
        </Planets>  
    </Galaxy>
```
```java
@JacksonXmlElementWrapper(useWrapping = false)  
@JacksonXmlProperty(localName = "Galaxy")  
private final LinkedHashSet<Galaxy> galaxies = new LinkedHashSet<>();
```

## Main content of Jackson

- [[Jackson ObjectMapper class]] - is the main class in Jackson use to serialize/deserialize objects 
- *[ObjectReader class](https://www.javadoc.io/static/com.fasterxml.jackson.core/jackson-databind/2.9.8/com/fasterxml/jackson/databind/ObjectReader.html)* -  Builder object that can be used for per-serialization configuration of deserialization parameters, such as root type to use or object to update (instead of constructing new instance).
- *[ObjectWriter class](https://www.javadoc.io/static/com.fasterxml.jackson.core/jackson-databind/2.9.8/com/fasterxml/jackson/databind/ObjectWriter.html)* - Builder object that can be used for per-serialization configuration of serialization parameters, such as JSON View and root type to use. (and thus fully thread-safe with no external synchronization); new instances are constructed for different configurations. Instances are initially constructed by ObjectMapper and can be reused in completely thread-safe manner with no explicit synchronization
- [[Jackson Annotations Main]] - [[Annotations in Java|Java Annotations]] used for parsing , and sometimes NESSESARY for parsing to work

## Jackson data binding

Looks like this:

| JSON Type         | Java Type                    |
|-------------------|------------------------------|
| object            | LinkedHashMap`<String,Object>` |
| array             | ArrayList`<Object>`            |
| string            | String                       |
| complete number   | Integer, Long or BigInteger  |
| fractional number | Double / BigDecimal          |
| true | false      | Boolean                      |
| null              | null                         |

## Jackson - Tree Model

Tree Model prepares a in-memory tree representation of the JSON  Document. ObjectMapper build tree of JsonNode nodes. It is most flexible approach. It is analogus to DOM parser for XML

This is mostly about having Objects in objects

**Create Tree from JSON**
ObjectMapper provides a pointer to root node of the tree after reading the JSON. Root Node can be used to traverse the complete tree. 

```java
//Create an ObjectMapper instance
ObjectMapper mapper = new ObjectMapper();	
String jsonString = "{\"name\":\"Mahesh Kumar\", \"age\":21,\"verified\":false,\"marks\": [100,90,85]}";
//create tree from JSON
JsonNode rootNode = mapper.readTree(jsonString);
```

**Traversing Tree Model**
Get each node using relative path to the root node while traversing tree and process the data. 
```java
JsonNode nameNode = rootNode.path("name");
System.out.println("Name: "+ nameNode.textValue());
 
JsonNode marksNode = rootNode.path("marks");
Iterator<JsonNode> iterator = marksNode.elements()
```

Example:
```java
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = "{\"name\":\"Mahesh Kumar\",  \"age\":21,\"verified\":false,\"marks\": [100,90,85]}";
         JsonNode rootNode = mapper.readTree(jsonString);

         JsonNode nameNode = rootNode.path("name");
         System.out.println("Name: "+ nameNode.textValue());

         JsonNode ageNode = rootNode.path("age");
         System.out.println("Age: " + ageNode.intValue());

         JsonNode verifiedNode = rootNode.path("verified");
         System.out.println("Verified: " + (verifiedNode.booleanValue() ? "Yes":"No"));

         JsonNode marksNode = rootNode.path("marks");
         Iterator<JsonNode> iterator = marksNode.elements();
         System.out.print("Marks: [ ");

         while (iterator.hasNext()) {
            JsonNode marks = iterator.next();
            System.out.print(marks.intValue() + " "); 
         }
 /*
 OUT:
Name: Mahesh Kumar
Age: 21
Verified: No
Marks: [ 100 90 85   
```

Or it can be read from tree:
```java
         ObjectMapper mapper = new ObjectMapper();

         JsonNode rootNode = mapper.createObjectNode();
         JsonNode marksNode = mapper.createArrayNode();
         ((ArrayNode)marksNode).add(100);
         ((ArrayNode)marksNode).add(90);
         ((ArrayNode)marksNode).add(85);
         ((ObjectNode) rootNode).put("name", "Mahesh Kumar");
         ((ObjectNode) rootNode).put("age", 21);
         ((ObjectNode) rootNode).put("verified", false);
         ((ObjectNode) rootNode).put("marks",marksNode);

         mapper.writeValue(new File("student.json"), rootNode);

         rootNode = mapper.readTree(new File("student.json"));

         Student student = mapper.treeToValue(rootNode, Student.class);

         System.out.println("Name: "+ student.getName());
         System.out.println("Age: " + student.getAge());
         System.out.println("Verified: " + (student.isVerified() ? "Yes":"No"));
         System.out.println("Marks: "+Arrays.toString(student.getMarks()));
/*
OUT:
Name: Mahesh Kumar
Age: 21
Verified: No
Marks: [ 100 90 85 ]  
```

## Streaming API
Streaming API reads and writes JSON content as discrete events. JsonParser reads the data whereas JsonGenerator writes the data. It is most powerful approach among the three and is of lowest overhead and fastest in read/write opreations. It is Analogus to Stax parser for XML.

In this Article, we'll showcase using Jackson streaming APIs to read and write JSON data. Streaming API works with concept of token and every details of Json is to be handle carefuly.

```java
         JsonFactory jsonFactory = new JsonFactory();
         JsonGenerator jsonGenerator = jsonFactory.createGenerator(new File("student.json"), JsonEncoding.UTF8);
         jsonGenerator.writeStartObject();
         // "name" : "Mahesh Kumar"
         jsonGenerator.writeStringField("name", "Mahesh Kumar"); 
         // "age" : 21
         jsonGenerator.writeNumberField("age", 21);
         // "verified" : false
         jsonGenerator.writeBooleanField("verified", false);
         // "marks" : [100, 90, 85]
         jsonGenerator.writeFieldName("marks"); 
         // [
         jsonGenerator.writeStartArray(); 
         // 100, 90, 85
         jsonGenerator.writeNumber(100); 
         jsonGenerator.writeNumber(90); 
         jsonGenerator.writeNumber(85); 
         // ]
         jsonGenerator.writeEndArray(); 
         jsonGenerator.writeEndObject(); 
         jsonGenerator.close();        
         //result student.json
         //{ //   "name":"Mahesh Kumar",
         //   "age":21,
         //   "verified":false,
         //   "marks":[100,90,85]
         //}
```