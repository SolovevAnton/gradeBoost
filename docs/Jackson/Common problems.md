
There are several problems associated with [[Jackson main|jackson]]. 

```toc

```

## Class cast Lincked hash map 
code:
```java
@Test
void givenJsonString_whenDeserializingToList_thenThrowingClassCastException() 
  throws JsonProcessingException {
    String jsonString = readFile("/to-java-collection/books.json");
    List<Book> bookList = objectMapper.readValue(jsonString, ArrayList.class);
    assertThat(bookList).size().isEqualTo(3);
    assertThatExceptionOfType(ClassCastException.class)
      .isThrownBy(() -> bookList.get(0).getBookId())
      .withMessageMatching(".*java.util.LinkedHashMap cannot be cast to .*com.baeldung.jackson.tocollection.Book.*");
}
```

What is thrown:
**java.lang.ClassCastException: class java.util.LinkedHashMap cannot be cast to class**
Why?:
If we take a closer look at the exception message _class java.util.LinkedHashMap cannot be cast to class … Book_, a couple of questions may come up.

We’ve declared the variable _bookList_ with the type _List`<`Book`>`_, but why does Jackson try to cast the LinkedHashMap_type to our _Book_ class? Furthermore, where does the _LinkedHashMap_ come from?

First, we indeed declared _bookList_ with the type _List`<`Book`>`_. However, when we called the [_objectMapper.readValue()_](https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html#readValue(java.lang.String,%20java.lang.Class)) method, we passed _ArrayList.class_ as the _Class_ object. Therefore, Jackson will deserialize the JSON content to an [_ArrayList_](https://www.baeldung.com/java-arraylist) object, but it has no idea what type of elements should be in the _ArrayList_ object.

Second, when Jackson attempts to deserialize an object in JSON but no target type information is given, it’ll use the default type: _[LinkedHashMap](https://www.baeldung.com/java-linked-hashmap)_. In other words, after the deserialization, we’ll get an _ArrayList`<`LinkedHashMap`>`_ object. In the _Map_, the keys are the names of the properties, for example, _“bookId”_, _“title”_ and so on.

Possible solution: 
Type reference, or:
```java
CollectionType listType = 
objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);

//or with generic
JavaType type = objectMapper.getTypeFactory().constructParametricType(ResponseResult.class, listType);
//
      objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
    List<Book> bookList = objectMapper.readValue(jsonString, listType);
```

https://www.baeldung.com/jackson-linkedhashmap-cannot-be-cast

## Generic deserialization
https://www.baeldung.com/java-deserialize-generic-type-with-jackson