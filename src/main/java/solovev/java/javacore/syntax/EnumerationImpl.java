package solovev.java.javacore.syntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class EnumerationImpl {
    public class Integers implements Enumeration<Integer> {
        private final int[] unmodifiableList = {1,2,3,4,5};
        private final AtomicInteger index = new AtomicInteger(0);

        @Override
        public boolean hasMoreElements() {
            return index.get() < unmodifiableList.length;
        }

        @Override
        public Integer nextElement() {
            if(hasMoreElements()) return unmodifiableList[index.getAndIncrement()];
            throw new NoSuchElementException();
        }

        @Override
        public Iterator<Integer> asIterator() {
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return hasMoreElements();
                }

                @Override
                public Integer next() {
                    return nextElement();
                }
            };
        }
    }
    @Nested
    public class IntegersTest {

        private Integers integers;

        @BeforeEach
        void setUp() {
            integers = new Integers();
        }

        @Test
        void testHasMoreElementsInitiallyTrue() {
            // Test that `hasMoreElements` is true initially
            assertTrue(integers.hasMoreElements(), "Expected `hasMoreElements` to return true initially.");
        }

        @Test
        void testNextElementReturnsCorrectValuesInSequence() {
            // Test that `nextElement` returns elements in the expected sequence
            assertEquals(1, integers.nextElement());
            assertEquals(2, integers.nextElement());
            assertEquals(3, integers.nextElement());
            assertEquals(4, integers.nextElement());
            assertEquals(5, integers.nextElement());
        }

        @Test
        void testHasMoreElementsBecomesFalseAfterLastElement() {
            // Iterate over all elements, then test that `hasMoreElements` becomes false
            for (int i = 0; i < 5; i++) {
                integers.nextElement();
            }
            assertFalse(integers.hasMoreElements(), "Expected `hasMoreElements` to be false after last element.");
        }

        @Test
        void testNextElementThrowsExceptionWhenNoElementsLeft() {
            // Exhaust all elements, then test that `nextElement` throws an exception
            for (int i = 0; i < 5; i++) {
                integers.nextElement();
            }
            assertThrows(NoSuchElementException.class, integers::nextElement, "Expected `nextElement` to throw NoSuchElementException when no elements left.");
        }

        @Test
        void testAsIteratorBehavesLikeEnumeration() {
            // Test that `asIterator` behaves similarly to Enumeration's methods
            Iterator<Integer> iterator = integers.asIterator();
            for (int i = 1; i <= 5; i++) {
                assertTrue(iterator.hasNext(), "Expected `hasNext` to return true while elements are available.");
                assertEquals(i, iterator.next(), "Expected `next` to return element in sequence.");
            }
            assertFalse(iterator.hasNext(), "Expected `hasNext` to return false after last element.");
            assertThrows(NoSuchElementException.class, iterator::next, "Expected `next` to throw NoSuchElementException when no elements left.");
        }
    }
}
