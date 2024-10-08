```toc

```
## Theory

```java
public final class Collectors extends Object
```

Implementations of [[Collector API in java|Collector]] that implement various useful [[Stream API in java#Reduction methods|reduction operations]], such as accumulating elements into collections, summarizing elements according to various criteria, etc.
Is used to create a collector with given characteristics
Part of the:
```java
import java.util.stream.Collectors;
```

### Examples
The following are examples of using the predefined collectors to perform common mutable reduction tasks:
```java
     // Accumulate names into a List
     List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());

     // Accumulate names into a TreeSet
     Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));

     // Convert elements to strings and concatenate them, separated by commas
     String joined = things.stream()
                           .map(Object::toString)
                           .collect(Collectors.joining(", "));

     // Compute sum of salaries of employee
     int total = employees.stream()
     .collect(Collectors.summingInt(Employee::getSalary)));

     // Group employees by department
     Map<Department, List<Employee>> byDept
         = employees.stream()
         .collect(Collectors.groupingBy(Employee::getDepartment));

     // Compute sum of salaries by department
     Map<Department, Integer> totalByDept
         = employees.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment,
                    Collectors.summingInt(Employee::getSalary)));

     // Partition students into passing and failing
     Map<Boolean, List<Student>> passingFailing =
         students.stream()
                 .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));

```

## Methods
All methods in the Collectors class are static. All the methods return [[Collector API in java|Collector]] instances:
### Collections creating

#### toCollections
```java
static <T,C extends Collection<T>>
Collector<T,?,C> 	toCollection(Supplier<C> collectionFactory)//Returns a Collector that accumulates the input elements into a new Collection, in encounter order.
static <T> Collector<T,?,List<T>> 	toList() //Returns a Collector that accumulates the input elements into a new List.
static <T> Collector<T,?,Set<T>> 	toSet() //Returns a Collector that accumulates the input elements into a new Set.
```
#### To Map:
```java
static <T,K,U> Collector<T,?,Map<K,U>> 	toMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper)//Returns a Collector that accumulates elements into a Map whose keys and values are the result of applying the provided mapping functions to the input elements.
static <T,K,U> Collector<T,?,Map<K,U>> 	toMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper, BinaryOperator<U> mergeFunction) //Returns a Collector that accumulates elements into a Map whose keys and values are the result of applying the provided mapping functions to the input elements.
static <T,K,U,M extends Map<K,U>>
Collector<T,?,M> 	toMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier) //Returns a Collector that accumulates elements into a Map whose keys and values are the result of applying the provided mapping functions to the input elements.
```
#### To Concurrent Map
```java
static <T,K,U> Collector<T,?,ConcurrentMap<K,U>> 	toConcurrentMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper) //Returns a concurrent Collector that accumulates elements into a ConcurrentMap whose keys and values are the result of applying the provided mapping functions to the input elements.
static <T,K,U> Collector<T,?,ConcurrentMap<K,U>> 	toConcurrentMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper, BinaryOperator<U> mergeFunction) //Returns a concurrent Collector that accumulates elements into a ConcurrentMap whose keys and values are the result of applying the provided mapping functions to the input elements.
static <T,K,U,M extends ConcurrentMap<K,U>>
Collector<T,?,M> 	toConcurrentMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier) //Returns a concurrent Collector that accumulates elements into a ConcurrentMap whose keys and values are the result of applying the provided mapping functions to the input elements.
```

### Math functions and statistics

#### Averaging
```java
static <T> Collector<T,?,Double> 	averagingDouble(ToDoubleFunction<? super T> mapper) //Returns a Collector that produces the arithmetic mean of a double-valued function applied to the input elements.
static <T> Collector<T,?,Double> 	averagingInt(ToIntFunction<? super T> mapper) //Returns a Collector that produces the arithmetic mean of an integer-valued function applied to the input elements.
static <T> Collector<T,?,Double> 	averagingLong(ToLongFunction<? super T> mapper) // Returns a Collector that produces the arithmetic mean of a long-valued function applied to the input elements.
```
#### Summing and counting
```java
static <T> Collector<T,?,Double> 	summingDouble(ToDoubleFunction<? super T> mapper) //Returns a Collector that produces the sum of a double-valued function applied to the input elements.
static <T> Collector<T,?,Integer> 	summingInt(ToIntFunction<? super T> mapper) //Returns a Collector that produces the sum of a integer-valued function applied to the input elements.
static <T> Collector<T,?,Long> 	summingLong(ToLongFunction<? super T> mapper)//Returns a Collector that produces the sum of a long-valued function applied to the input elements.
```

```java
static <T> Collector<T,?,Long> 	counting() //Returns a Collector accepting elements of type T that counts the number of input elements.
```

#### Max and min:

```java
static <T> Collector<T,?,Optional<T>> 	maxBy(Comparator<? super T> comparator) //Returns a Collector that produces the maximal element according to a given Comparator, described as an Optional<T>.
static <T> Collector<T,?,Optional<T>> 	minBy(Comparator<? super T> comparator) //Returns a Collector that produces the minimal element according to a given Comparator, described as an Optional<T>.
```

#### Summary statistics

```java
summarizingDouble(ToDoubleFunction<? super T> mapper)//Returns a Collector which applies an double-producing mapping function to each input element, and returns summary statistics for the resulting values.
static <T> Collector<T,?,IntSummaryStatistics> 	summarizingInt(ToIntFunction<? super T> mapper) //Returns a Collector which applies an int-producing mapping function to each input element, and returns summary statistics for the resulting values.
static <T> Collector<T,?,LongSummaryStatistics> 	summarizingLong(ToLongFunction<? super T> mapper) //Returns a Collector which applies an long-producing mapping function to each input element, and returns summary statistics for the resulting values.

```

<u>Example</u>
```java
int[] arr1 = {1,2,3,4,5,6,7,8,9,10};  
List<Integer> list = Arrays.stream(arr1).boxed().toList();  
  
System.out.println(list.stream()  
                .filter( i -> i > 5)  
                .collect(Collectors.summarizingInt(Integer :: valueOf)));
//OUT:
//IntSummaryStatistics{count=5, sum=40, min=6, average=8,000000, max=10}
```

#### Joining for strings
```java
static Collector<CharSequence,?,String> 	joining() //Returns a Collector that concatenates the input elements into a String, in encounter order.
static Collector<CharSequence,?,String> 	joining(CharSequence delimiter) //Returns a Collector that concatenates the input elements, separated by the specified delimiter, in encounter order.
static Collector<CharSequence,?,String> 	joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix) //Returns a Collector that concatenates the input elements, separated by the specified delimiter, with the specified prefix and suffix, in encounter order.
```

#### Grouping By
Create a pivot tables, as Maps. And the Key for that maps, are the  types that Function argument returns

```java
static <T,K> Collector<T,?,Map<K,List<T>>> 	groupingBy(Function<? super T,? extends K> classifier) //Returns a Collector implementing a "group by" operation on input elements of type T, grouping elements according to a classification function, and returning the results in a Map.

static <T,K,A,D> Collector<T,?,Map<K,D>> 	groupingBy(Function<? super T,? extends K> classifier, Collector<? super T,A,D> downstream) //Returns a Collector implementing a cascaded "group by" operation on input elements of type T, grouping elements according to a classification function, and then performing a reduction operation on the values associated with a given key using the specified downstream Collector.

static <T,K,D,A,M extends Map<K,D>>
Collector<T,?,M> 	groupingBy(Function<? super T,? extends K> classifier, Supplier<M> mapFactory, Collector<? super T,A,D> downstream) //Returns a Collector implementing a cascaded "group by" operation on input elements of type T, grouping elements according to a classification function, and then performing a reduction operation on the values associated with a given key using the specified downstream Collector.

static <T,K> Collector<T,?,ConcurrentMap<K,List<T>>> 	groupingByConcurrent(Function<? super T,? extends K> classifier) //Returns a concurrent Collector implementing a "group by" operation on input elements of type T, grouping elements according to a classification function.

static <T,K,A,D> Collector<T,?,ConcurrentMap<K,D>> 	groupingByConcurrent(Function<? super T,? extends K> classifier, Collector<? super T,A,D> downstream) //Returns a concurrent Collector implementing a cascaded "group by" operation on input elements of type T, grouping elements according to a classification function, and then performing a reduction operation on the values associated with a given key using the specified downstream Collector.

static <T,K,A,D,M extends ConcurrentMap<K,D>>
Collector<T,?,M> 	groupingByConcurrent(Function<? super T,? extends K> classifier, Supplier<M> mapFactory, Collector<? super T,A,D> downstream) //Returns a concurrent Collector implementing a cascaded "group by" operation on input elements of type T, grouping elements according to a classification function, and then performing a reduction operation on the values associated with a given key using the specified downstream Collector.
```

<u>Example:</u>
```java
System.out.println(list.stream()  
                .collect(Collectors.groupingBy( i -> i % 2 == 0))); //here is Collector<T,?,Map<K,List<T>>> groupingBy(Function<? super T,? extends K> classifier)

//OUT:
{false=[1, 3, 5, 7, 9], true=[2, 4, 6, 8, 10]}

```
and to do some additional operations:
```java
System.out.println(  
        list.stream()  
        .collect(Collectors.groupingBy(( i -> i % 2 == 0),Collectors.counting())));
// OUT:
{false=5, true=5}

//or without extra collecotr:
Map<String, Float> averageSalariesMap = employees  
        .stream()  
        .collect(Collectors.groupingBy(  
                (employee) -> employee.jobTitle  
        ))  
        .entrySet()  
        .stream()  
        .collect(Collectors.toMap(  
                (entry) -> entry.getKey(),  
                (entry) -> entry.getValue()  
                                .stream()  
                                .map((employee) -> employee.salary)  
                                .reduce(0f, (acc, x) -> acc + x) / entry.getValue().size()
));
        
```

#### Partition(Раздел, разделение) By
Its the same as groupBy, the only difference, it uses predicate, and divide the array only it two groups with true or false

```java
static <T> Collector<T,?,Map<Boolean,List<T>>> 	partitioningBy(Predicate<? super T> predicate) //Returns a Collector which partitions the input elements according to a Predicate, and organizes them into a Map<Boolean, List<T>>.

static <T,D,A> Collector<T,?,Map<Boolean,D>> 	partitioningBy(Predicate<? super T> predicate, Collector<? super T,A,D> downstream) //Returns a Collector which partitions the input elements according to a Predicate, reduces the values in each partition according to another Collector, and organizes them into a Map<Boolean, D> whose values are the result of the downstream reduction.
```

#### Mapping
Used to map value to some other before collecting:
The `Collectors.mapping` method is often used in conjunction with `Collectors.groupingBy` to perform more complex reductions, such as transforming the elements before grouping or collecting them into a specific data structure.

```java
public static <T, U, A, R> Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper, Collector<? super U, A, R> downstream)
```
- **T**: The type of input elements to the reduction operation.
- **U**: The type of elements to be collected.
- **A**: The intermediate accumulation type of the downstream collector.
- **R**: The result type of the downstream reduction

Parameters
- **mapper**: A function that maps the input elements to a different type.
- **downstream**: A collector that will accumulate the results of the mapping function.

example:

```JAVA
public static Map<String, List<Long>> toMap(List<Test> list) { return list.stream() 
.collect(
Collectors.groupingBy( Test::getDate, Collectors.mapping(Test::getId, Collectors.toList())));
```



#### Reducing
```java
static <T> Collector<T,?,Optional<T>> 	reducing(BinaryOperator<T> op) //Returns a Collector which performs a reduction of its input elements under a specified BinaryOperator.

static <T> Collector<T,?,T> 	reducing(T identity, BinaryOperator<T> op) //Returns a Collector which performs a reduction of its input elements under a specified BinaryOperator using the provided identity.

static <T,U> Collector<T,?,U> 	reducing(U identity, Function<? super T,? extends U> mapper, BinaryOperator<U> op) //Returns a Collector which performs a reduction of its input elements under a specified mapping function and BinaryOperator.
```

#### Other
``` java
static <T,A,R,RR> Collector<T,A,RR> collectingAndThen(Collector<T,A,R> downstream, Function<R,RR> finisher) //Adapts a Collector to perform an additional finishing transformation.

static <T,U,A,R> Collector<T,?,R> 	mapping(Function<? super T,? extends U> mapper, Collector<? super U,A,R> downstream) //Adapts a Collector accepting elements of type U to one accepting elements of type T by applying a mapping function to each input element before accumulation.
```