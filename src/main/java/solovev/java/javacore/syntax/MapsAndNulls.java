package solovev.java.javacore.syntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapsAndNulls {
    Map<Integer, Integer> hashMap = new HashMap<>();
    Map<Integer, Integer> linkedMap = new LinkedHashMap<>();
    Map<Integer, Integer> treeMap = new TreeMap<>();

    @BeforeEach
    public void mapSet() {
        hashMap = new HashMap<>();
        linkedMap = new LinkedHashMap<>();
        treeMap = new TreeMap<>();
    }

    @Test
    public void nullToHashmap() {
        hashMap.put(null, null);
        hashMap.put(null, null);
        hashMap.put(1, 1);
    }

    @Test
    public void nullToLinkedMap() {
        linkedMap.put(null, null);
        linkedMap.put(null, null);
        linkedMap.put(1, 1);
    }

    @Test
    public void nullToTreeMap() {
       assertThrows(NullPointerException.class,() -> treeMap.put(null, null));
    }

    @Test
    public void rewriteTreeMapToNotThrow(){
        Comparator<Integer> brokenComparator = (a,b) -> 0;
        treeMap = new TreeMap<>(brokenComparator);
        treeMap.put(null,null);
        treeMap.put(1,1);
    }

}
