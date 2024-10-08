package solovev.java.javacore.syntax;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
//every nested class is separate task
public class StreamsOnCodeWars {

    //task:https://www.codewars.com/kata/570f6436b29c708a32000826/train/java
    public static class NoNRepeatedString {
        public static Character firstNonRepeated(String source) {
            return source
                    .chars()
                    .mapToObj(i -> Character.valueOf((char) i))
                    .filter(c -> source.indexOf(c) == source.lastIndexOf(c))
                    .findFirst()
                    .get();
        }
    }

    //task: https://www.codewars.com/kata/54ac045d35adf4e6e2000e43/train/java
    public static class DragonsCurve {
        public IntFunction<String> mapFunction = i -> i == 'a'
                ? "aRbFR"
                : i == 'b'
                ? "LFaLb"
                : String.valueOf((char) i);
        ; //Make the function; map the chars to Strings
        //a -> aRbFR, b -> LFaLb, otherwise -> itself

        /**
         * Make the curve; stream the chars repeatedly (starting with Fa) through the mapFunction n times
         * Then remove the a and b (createFilter function is useful for that)
         */
        public String createCurve(int n) {
            return createCurve(n, "Fa"); //That's an IntStream with 'F' and 'a' ready to be acted upon
        }

        private String createCurve(int n, String s) {
            return n > 0
                    ? createCurve(n - 1, s.chars().mapToObj(mapFunction)
                    .collect(Collectors.joining()))
                    : s.replaceAll("[ab]", "");
        }

        /**
         * How many of the specified char are in the given curve?
         * Hint: createFilter could be useful for this
         */
        public long howMany(char c, String curve) {
            return curve.chars()
                    .filter(i -> i == c)
                    .count(); //Determined by die roll; guaranteed to be random
        }

        /**
         * Create a predicate to filter the specified char; keep or remove based on keep variable
         */
        public IntPredicate createFilter(char filterWhat, boolean keep) {
            IntPredicate pred = i -> i == filterWhat;
            return keep ? pred : pred.negate(); //Dat predicate
        }
    }

    //task: https://www.codewars.com/kata/5829c2c8ef8d4474300000fa/train/java
    public static class TripleSorter {
        private interface Student {
            int getGpa();
            int getAge();
            String getFullName();
        }
        public static String sort(List<Student> students) {
            Comparator<Student> comp = Comparator
                    .comparingInt(Student::getGpa).reversed()
                    .thenComparing(TripleSorter::extractLetter)
                    .thenComparing(Student::getAge);
            return students
                    .stream()
                    .sorted(comp)
                    .map(Student::getFullName)
                    .collect(Collectors.joining(","));
        }
        private static char extractLetter(Student student){
            return student.getFullName().split(" ")[1].charAt(0);
        }
    }

    //task: https://www.codewars.com/kata/51e0007c1f9378fa810002a9/train/java
    public static class ParserWithStream{
        private static int pos;
        private static int i;
        private static int[] res;
        private static Map<Integer,Runnable> actions = Map.of(
                (int)'i', () -> pos++,
                (int)'d', () -> pos--,
                (int)'s', () -> pos*= pos,
                (int)'o', () -> res[i++] = pos
        );

        public static int[] parse(String data) {
            pos = 0;
            i = 0;
            res = new int[data.replaceAll("[^o]","").length()];
            data
                    .chars()
                    .forEach(i -> actions.get(i).run());
            return res;
        }
    }

}
