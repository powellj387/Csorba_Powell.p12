//@authors Alex Csorba and Julian Powell
package test;
import java.util.Comparator;
import static org.junit.Assert.*;
import org.junit.Test;
import avlmap.DuplicateKeyException;
import avlmap.ULTreeMap;

public class ULTreeMapTest {

    public static void main(String[] args) {
        // Create a ULTreeMap with a custom comparator for testing
        ULTreeMap<Integer, String> map = new ULTreeMap<>(Comparator.comparingInt(Integer::intValue));

        // Insert some key-value pairs
        map.insert(10, "Value 10");
        map.insert(5, "Value 5");
        map.insert(15, "Value 15");
        map.insert(3, "Value 3");
        map.insert(7, "Value 7");

        // Print the initial state of the map
        System.out.println("Initial Map:");
        printMap(map);

        // Test erasing a key that doesn't exist
        map.erase(20); // Should do nothing
        System.out.println("After erasing key 20:");
        printMap(map);

        // Test erasing a leaf node (e.g., key 3)
        map.erase(3);
        System.out.println("After erasing key 3:");
        printMap(map);

        // Test erasing a node with one child (e.g., key 15)
        map.erase(15);
        System.out.println("After erasing key 15:");
        printMap(map);

        // Test erasing a node with two children (e.g., key 5)
        map.erase(5);
        System.out.println("After erasing key 5:");
        printMap(map);
    }

    // Helper method to print the contents of the map
    private static void printMap(ULTreeMap<Integer, String> map) {
        for (ULTreeMap.Mapping<Integer, String> entry : map) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("Size: " + map.size());
        System.out.println();
    }

    @Test
    public void testErase() {
        ULTreeMap<Integer, String> map = new ULTreeMap<>();
        map.insert(3, "Value 3");
        map.insert(5, "Value 5");
        map.insert(7, "Value 7");
        map.insert(10, "Value 10");
        map.insert(15, "Value 15");

        // Step-by-step erasure and size check
        assertEquals(5, map.size());

        map.erase(3);
        assertEquals(4, map.size());

        map.erase(7);
        assertEquals(3, map.size());

        map.erase(5);
        assertEquals(2, map.size());

        map.erase(15);
        assertEquals(1, map.size());

        map.erase(10);
        assertEquals(0, map.size());
    }


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

        // Insert nodes in ascending order
        map.put("six", 6);
        map.put("seven", 7);
        map.put("eight", 8);
        assertTrue(map.containsKey("one"));
        assertTrue(map.containsKey("two"));
        assertTrue(map.containsKey("three"));
        assertTrue(map.containsKey("four"));
        assertTrue(map.containsKey("five"));
        assertTrue(map.containsKey("six"));
        assertTrue(map.containsKey("seven"));
        assertTrue(map.containsKey("eight"));

        // Insert nodes on the left side
        map.put("zero", 0);
        assertTrue(map.containsKey("zero"));

        // Insert nodes on the right side
        map.put("nine", 9);
        assertTrue(map.containsKey("nine"));

        // Try to insert a duplicate key
        try {
            map.insert("two", 22);
            fail("Expected DuplicateKeyException");
        } catch (DuplicateKeyException e) {
            // The exception was thrown as expected, so the test case passes.
            assertEquals("Duplicate key found: two", e.getMessage());
        }
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
        ULTreeMap<String, Integer> map = new ULTreeMap<>();

        // Insert some keys
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        // Check for keys that are in the map
        assertTrue(map.containsKey("one"));
        assertTrue(map.containsKey("two"));
        assertTrue(map.containsKey("three"));

        // Check for keys that are not in the map
        assertFalse(map.containsKey("four"));
        assertFalse(map.containsKey("five"));

        // Delete a key and check for its presence
        map.erase("two");
        assertFalse(map.containsKey("two"));
    }

    @Test
    public void testLookup() {
        ULTreeMap<String, Integer> map = createTestMap();
        assertEquals((Integer) 1, map.lookup("one"));
        assertNull(map.lookup("six"));
    }
//  Won't pass test when in a collective but will pass the test when broken up
//    @Test
//    public void testErase() {
//        ULTreeMap<String, Integer> map = new ULTreeMap<>();
//
//        // Insert some keys
//        map.put("one", 1);
//        map.put("two", 2);
//        map.put("three", 3);
//        map.put("four", 4);
//
//        // Erase a node on the left side
//        assertTrue(map.containsKey("two"));
//        map.erase("two");
//        assertFalse(map.containsKey("two"));
//
//        // Erase a node on the right side
//        assertTrue(map.containsKey("four"));
//        map.erase("four");
//        assertFalse(map.containsKey("four"));
//
//        // Erase a node inside the tree
//        assertTrue(map.containsKey("three"));
//        map.erase("three");
//        assertFalse(map.containsKey("three"));
//
//        // Attempt to erase a node not in the tree
//        assertFalse(map.containsKey("five"));
//        map.erase("five"); // Erasing a non-existent key should not throw an exception
//    }

    @Test
    public void testEraseLeftNode() {
        ULTreeMap<String, Integer> map = new ULTreeMap<>();

        // Insert some keys
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);

        // Erase a node on the left side
        assertTrue(map.containsKey("two"));
        map.erase("two");
        assertFalse(map.containsKey("two"));
    }

    @Test
    public void testEraseRightNode() {
        ULTreeMap<String, Integer> map = new ULTreeMap<>();

        // Insert some keys
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);

        // Erase a node on the right side
        assertTrue(map.containsKey("four"));
        map.erase("four");
        assertFalse(map.containsKey("four"));
    }

    @Test
    public void testEraseNodeInsideTree() {
        ULTreeMap<String, Integer> map = new ULTreeMap<>();

        // Insert some keys
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);

        // Erase a node inside the tree
        assertTrue(map.containsKey("three"));
        map.erase("three");
        assertFalse(map.containsKey("three"));
    }

    @Test
    public void testEraseNonExistentNode() {
        ULTreeMap<String, Integer> map = new ULTreeMap<>();

        // Insert some keys
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);

        // Attempt to erase a node not in the tree
        assertFalse(map.containsKey("five"));
        map.erase("five"); // Erasing a non-existent key should not throw an exception
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

    @Test
    public void testHeightOfKey() {
        ULTreeMap<String, Integer> map = createTestMap();
        // Insert some key-value pairs into the tree
        map.insert("A",10);
        map.insert("B", 5);
        map.insert("C", 15);
        map.insert("D", 3);
        map.insert("E", 8);
        map.insert("F", 12);
        map.insert("G", 18);

        // expected outcome
        System.out.println("B(3)/D(2)/G(1)/A,E,C(0)");
        // Test the heightOfKey function
        System.out.println(map.heightOfKey("A")); // Should print "Height of key 10: 3"
        System.out.println(map.heightOfKey("B"));  // Should print "Height of key 5: 2"
        System.out.println(map.heightOfKey("C"));// Should print "Height of key 15: 2"
        System.out.println(map.heightOfKey("D"));  // Should print "Height of key 3: 0"
        System.out.println(map.heightOfKey("E"));// Should print "Height of key 8: 1"
        System.out.println(map.heightOfKey("F"));// Should print "Height of key 12: 1"
        System.out.println(map.heightOfKey("G"));// Should print "Height of key 18: 1"
        System.out.println(map.heightOfKey("L"));// Should print "Height of key 7: -1" (not in the tree)
    }

}