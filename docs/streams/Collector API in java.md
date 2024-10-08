```toc

```
## Theory
A mutable reduction operation that accumulates input elements into a mutable result container, optionally transforming the accumulated result into a final representation after all input elements have been processed. 
Reduction operations can be performed either sequentially or in parallel. 

> [!NOTE]
> Most of the time collector that you need can be returned by one of the methods of : [[Collectors class| Collectors class]] 

Libraries that implement reduction based on Collector, such as Stream.collect(Collector), must adhere to the following constraints:

-  The first argument passed to the accumulator function, both arguments passed to the combiner function, and the argument passed to the finisher function must be the result of a previous invocation of the result supplier, accumulator, or combiner functions.
- The implementation should not do anything with the result of any of the result supplier, accumulator, or combiner functions other than to pass them again to the accumulator, combiner, or finisher functions, or return them to the caller of the reduction operation.
- If a result is passed to the combiner or finisher function, and the same object is not returned from that function, it is never used again.
- Once a result is passed to the combiner or finisher function, it is never passed to the accumulator function again.
- For non-concurrent collectors, any result returned from the result supplier, accumulator, or combiner functions must be serially thread-confined. This enables collection to occur in parallel without the Collector needing to implement any additional synchronization. The reduction implementation must manage that the input is properly partitioned, that partitions are processed in isolation, and combining happens only after accumulation is complete.
- For concurrent collectors, an implementation is free to (but not required to) implement reduction concurrently. A concurrent reduction is one where the accumulator function is called concurrently from multiple threads, using the same concurrently-modifiable result container, rather than keeping the result isolated during accumulation. A concurrent reduction should only be applied if the collector has the Collector.Characteristics.UNORDERED characteristics or if the originating data is unordered.

## Creating a  custom collector
Is part of the package:
```java
import java.util.stream;
```
However its easier to use one of the methods of the [[Collectors class|collectors class]] to create a needed Collector

And has following description:
```java
public interface Collector<T,A,R>

//where
`T` - the type of input elements to the reduction operation

`A` - the mutable accumulation type of the reduction operation (often hidden as an implementation detail)

`R` - the result type of the reduction operation
```

A Collector is specified by four functions that work together to accumulate entries into a mutable result container, and optionally perform a final transform on the result. They are:

- creation of a new result container (supplier())
- incorporating a new data element into a result container (accumulator())
- combining two result containers into one (combiner())
   combine function is called only when a `ReduceTask` completes, and `ReduceTask` instances are used only when evaluating a pipeline in parallel. 
- performing an optional final transform on the container (finisher())

The identity constraint says that for any partially accumulated result, combining it with an empty result container must produce an equivalent result. That is, for a partially accumulated result `a` that is the result of any series of accumulator and combiner invocations, `a` must be equivalent to `combiner.apply(a, supplier.get())`.

<u>Example:</u>
```java
public static Collector<Integer, List<Integer> , List<Integer>> customAsList = new Collector<Integer, List<Integer> , List<Integer>>() {  
        @Override  
        public Supplier<List<Integer>> supplier() { //creates where to store stuff  
            return ArrayList<Integer> :: new;  
        }  
  
        @Override  
        public BiConsumer<List<Integer>, Integer> accumulator() { //how to add stuff to  
            return (list,i) -> list.add(i);  
        }  
  
        @Override  
        public BinaryOperator<List<Integer>> combiner() {  
            return (list1,list2) ->    { //this is not done by lambda one line because addAll method returns boolean, which is not suitable for this method return type  
                list1.addAll(list2);  
                return list1;  
            };  
        }  
  
        @Override  
        public Function<List<Integer> , List<Integer>> finisher() { //here list is sorted to reverse order  
            return list -> { // this is not done by one line, since sort returns void  
                list.sort(Comparator.reverseOrder());  
                return list;  
            };  
        }  
  
        @Override  
            public Set<Collector.Characteristics> characteristics () {  
                return Set.of(Characteristics.CONCURRENT);//returns characteristics of this collector  
            }  
  
        };  
    public static void main(String[] args) {  
        int[] arr1 = {1,2,3,4,5,6,7,8,9,10};  
        List<Integer> list = Arrays.stream(arr1).boxed().toList();  
  
        System.out.println(list.stream()  
                        .filter( i -> i > 5)  
                        .collect(customAsList));  //OUT: [10, 9, 8, 7, 6]
  
        System.out.println(customAsList.characteristics());  //OUT:[CONCURRENT]
  
        //the collector produces the same result as:  
  
        List<Integer> container = customAsList.supplier().get();  
        for(Integer i : list) {  
            customAsList.accumulator().accept(container,i);  
        }  
        customAsList.finisher().apply(container);  
  
        System.out.println(container); //OUT: [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
```

## Methods

### Static:
```java
static <T,A,R> Collector<T,A,R> 	of(Supplier<A> supplier, BiConsumer<A,T> accumulator, BinaryOperator<A> combiner, Function<A,R> finisher, Collector.Characteristics... characteristics)//Returns a new Collector described by the given supplier, accumulator, combiner, and finisher functions.
static <T,R> Collector<T,R,R> 	of(Supplier<R> supplier, BiConsumer<R,T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics) //Returns a new Collector described by the given supplier, accumulator, and combiner functions.
```

### Other:
```java
BiConsumer<A,T> 	accumulator() //A function that folds a value into a mutable result container.
Set<Collector.Characteristics> 	characteristics() // Returns a Set of Collector.Characteristics indicating the characteristics of this Collector.
BinaryOperator<A> 	combiner()
A function that accepts two partial results and merges them.
Function<A,R> 	finisher() // Perform the final transformation from the intermediate accumulation type A to the final result type R.
Supplier<A> 	supplier() // A function that creates and returns a new mutable result container.
```
