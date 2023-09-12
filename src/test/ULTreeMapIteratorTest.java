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
            treeMap = new ULTreeMap<>(Integer::compare);
        }

        @Test
        public void testEmptyTreeIterator() {
            Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();
            assertFalse(iterator.hasNext());
        }

        @Test
        public void testIteratorWithSingleElement() {
            treeMap.put(1, "One");
            Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();

            assertTrue(iterator.hasNext());
            ULTreeMap.Mapping<Integer, String> mapping = iterator.next();
            assertEquals(1, (int) mapping.getKey());
            assertEquals("One", mapping.getValue());

            assertFalse(iterator.hasNext());
        }

        @Test
        public void testIteratorInOrderTraversal() {
            treeMap.put(2, "Two");
            treeMap.put(1, "One");
            treeMap.put(3, "Three");
            Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();

            assertTrue(iterator.hasNext());
            ULTreeMap.Mapping<Integer, String> firstMapping = iterator.next();
            assertEquals(1, (int) firstMapping.getKey());
            assertEquals("One", firstMapping.getValue());

            assertTrue(iterator.hasNext());
            ULTreeMap.Mapping<Integer, String> secondMapping = iterator.next();
            assertEquals(2, (int) secondMapping.getKey());
            assertEquals("Two", secondMapping.getValue());

            assertTrue(iterator.hasNext());
            ULTreeMap.Mapping<Integer, String> thirdMapping = iterator.next();
            assertEquals(3, (int) thirdMapping.getKey());
            assertEquals("Three", thirdMapping.getValue());

            assertFalse(iterator.hasNext());
        }

        @Test(expected = ConcurrentModificationException.class)
        public void testConcurrentModificationException() {
            treeMap.put(1, "One");
            Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();

            treeMap.put(2, "Two"); // Modify the tree while iterating, should throw ConcurrentModificationException
            iterator.next();
        }

        @Test(expected = NoSuchElementException.class)
        public void testNoSuchElementException() {
            treeMap.put(1, "One");
            Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();

            iterator.next(); // First element
            iterator.next(); // No more elements, should throw NoSuchElementException
        }

        @Test(expected = UnsupportedOperationException.class)
        public void testRemoveUnsupportedOperationException() {
            treeMap.put(1, "One");
            Iterator<ULTreeMap.Mapping<Integer, String>> iterator = treeMap.iterator();

            iterator.remove(); // Should throw UnsupportedOperationException
        }
    }