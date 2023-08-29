//@authors Alex Csorba and Julian Powell
package test;
import java.util.Comparator;
import static org.junit.Assert.*;
import org.junit.Test;
import avlmap.DuplicateKeyException;
import avlmap.ULTreeMap;

public class ULTreeMapTest {

    private ULTreeMap<String, Integer> createTestMap() {
        ULTreeMap<String, Integer> map = new ULTreeMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        return map;
    }

    @Test
    public void testInsert() {
        ULTreeMap<String, Integer> map = createTestMap();
        map.insert("six", 6);
        assertEquals(6, map.size());
        assertEquals((Integer) 6, map.lookup("six"));
    }

    @Test(expected = DuplicateKeyException.class)
    public void testInsertDuplicateKey() {
        ULTreeMap<String, Integer> map = createTestMap();
        map.insert("three", 6);
    }

    @Test
    public void testPut() {
        ULTreeMap<String, Integer> map = createTestMap();
        map.put("six", 6);
        assertEquals(6, map.size());
        assertEquals((Integer) 6, map.lookup("six"));
    }

    @Test
    public void testContainsKey() {
        ULTreeMap<String, Integer> map = createTestMap();
        assertTrue(map.containsKey("one"));
        assertFalse(map.containsKey("six"));
    }

    @Test
    public void testLookup() {
        ULTreeMap<String, Integer> map = createTestMap();
        assertEquals((Integer) 1, map.lookup("one"));
        assertNull(map.lookup("six"));
    }

    @Test
    public void testErase() {
        ULTreeMap<String, Integer> map = createTestMap();
        map.erase("three");
        assertEquals(4, map.size());
        assertFalse(map.containsKey("three"));
    }

    @Test
    public void testKeys() {
        ULTreeMap<String, Integer> map = createTestMap();
        java.util.Collection<String> keys = map.keys();
        assertEquals(5, keys.size());
        assertTrue(keys.contains("one"));
        assertTrue(keys.contains("two"));
        assertTrue(keys.contains("three"));
        assertTrue(keys.contains("four"));
        assertTrue(keys.contains("five"));
    }

    @Test
    public void testSize() {
        ULTreeMap<String, Integer> map = createTestMap();
        assertEquals(5, map.size());
    }

    @Test
    public void testEmpty() {
        ULTreeMap<String, Integer> map = new ULTreeMap<>();
        assertTrue(map.empty());
        map.put("one", 1);
        assertFalse(map.empty());
    }

    @Test
    public void testClear() {
        ULTreeMap<String, Integer> map = createTestMap();
        map.clear();
        assertTrue(map.empty());
        assertEquals(0, map.size());
    }
}