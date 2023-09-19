package test;

import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import avlmap.ULTreeMap;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
public class ULTreeMapIteratorTest {

        private ULTreeMap<Integer, String> treeMap;

    @Before
    public void setUp() {
        treeMap = new ULTreeMap<>();
        treeMap.insert(2, "Two");
        treeMap.insert(1, "One");
        treeMap.insert(3, "Three");
    }

    @Test
    public void testEmptyTreeIterator() {
        ULTreeMap<Integer, String> emptyTree = new ULTreeMap<>();
        Iterator<ULTreeMap.Mapping<Integer, String>> iterator = emptyTree.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorWithSingleElement() {
        ULTreeMap<Integer, String> singleElementTree = new ULTreeMap<>();
        singleElementTree.insert(1, "One");
        Iterator<ULTreeMap.Mapping<Integer, String>> iterator = singleElementTree.iterator();

        assertTrue(iterator.hasNext());
        ULTreeMap.Mapping<Integer, String> mapping = iterator.next();
        assertEquals(1, (int) mapping.getKey());
        assertEquals("One", mapping.getValue());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorInOrderTraversal() {
        StringBuilder result = new StringBuilder();
        for (ULTreeMap.Mapping<Integer, String> entry : treeMap) {
            result.append(entry.getKey()).append(entry.getValue());
        }
        assertEquals("1One2Two3Three", result.toString());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testConcurrentModificationException() {
        Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();
        treeMap.put(4, "Four"); // Modify the tree while iterating, should throw ConcurrentModificationException
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() {
        Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.next(); // No more elements, should throw NoSuchElementException
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveUnsupportedOperationException() {
        Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();
        iterator.remove(); // Should throw UnsupportedOperationException
    }
    }