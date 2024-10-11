package solovev.java.javacore.syntax;

import java.util.List;

public class PECSExample {

    private interface Class1 {
    }

    private interface Class2 extends Class1 {
    }

    private interface Class3 extends Class2 {
    }

    private void producer(List<? extends Class2> producer) {
        Object zero = producer.getFirst();
        Class1 one = producer.getFirst();
        Class2 two = producer.getFirst();
        //next will gie compilation error
        Class3 three = producer.getFirst();

        Object toAdd0 = null;
        Class1 toAdd1 = null;
        Class2 toAdd2 = null;
        Class3 toAdd3 = null;

        //all below compilation error:
        producer.add(toAdd0);
        producer.add(toAdd1);
        producer.add(toAdd2);
        producer.add(toAdd3);
    }

    private void strict(List<Class2> list) {
        Object zero = list.getFirst();
        Class1 one = list.getFirst();
        Class2 two = list.getFirst();
        //next will give compilation error
        Class3 three = list.getFirst();

        Object toAdd0 = null;
        Class1 toAdd1 = null;
        Class2 toAdd2 = null;
        Class3 toAdd3 = null;

        //this two below compilation error:
        list.add(toAdd0);
        list.add(toAdd1);

        //this will be fine!
        list.add(toAdd2);
        list.add(toAdd3);
    }

    private void consumer(List<? super Class2> consumer) {
        Object toAdd0 = null;
        Class1 toAdd1 = null;
        Class2 toAdd2 = null;
        Class3 toAdd3 = null;

        //this two below compilation error:
        consumer.add(toAdd0);
        consumer.add(toAdd1);

        //this will be fine!
        consumer.add(toAdd2);
        consumer.add(toAdd3);

        //this is fine
        Object zero = consumer.getFirst();
        //all below compilation error
        Class1 one = consumer.getFirst();
        Class2 two = consumer.getFirst();
        Class3 three = consumer.getFirst();
    }
}
