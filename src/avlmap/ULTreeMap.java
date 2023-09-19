//@authors Alex Csorba and Julian Powell
package avlmap;

import java.util.Comparator;

public class ULTreeMap<K,V> implements Cloneable,Iterable<ULTreeMap.Mapping<K,V>>{

    public static class Mapping<K,V>
    {
        K nodeKey;
        V nodeValue;
        public Mapping(K key, V value) {
            this.nodeKey = key;
            this.nodeValue = value;
        }
        public K getKey(){return nodeKey;};
        public V getValue(){return nodeValue;};

    }

    private class Node {
        K key;
        V value;
        Node left;
        Node right;
        Node parent;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node root;
    private Comparator<K> comparator;
    private int size;
    public ULTreeMap() {
        root = null;
    }

    public ULTreeMap(java.util.Comparator<K> compare) {
        root = null;
        comparator = compare;
        size = 0;
    }

    public ULTreeMap<K,V> clone() {
        ULTreeMap<K, V> clone = new ULTreeMap<K, V>();
        clone.root = cloneNode(root);
        clone.comparator = this.comparator;
        clone.size = this.size;
        return clone;
    }

    private Node cloneNode(Node node) {
        Node result = null;

        if (node != null) {
            Node newNode = new Node(node.key, node.value);
            newNode.left = cloneNode(node.left);
            newNode.right = cloneNode(node.right);
            result = newNode;
        }

        return result;
    }

    public void insert(K key, V value) throws DuplicateKeyException{
        // Call the private recursive insert method
        root = insert(root, key, value);
    }
    private Node insert(Node node, K key, V value) throws DuplicateKeyException {
        Node result = node;

        if (node == null) {
            size++;
            result = new Node(key, value);
        } else {
            int cmp = compare(key, node.key);
            if (cmp < 0) {
                node.left = insert(node.left, key, value);
                node.left.parent = node;
            } else if (cmp > 0) {
                node.right = insert(node.right, key, value);
                node.right.parent = node;
            } else {
                throw new DuplicateKeyException("Duplicate key found: " + key);
            }
        }

        return result;
    }

    public void put(K key, V value){
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            size++;
            node = new Node(key, value);
        } else {
            int cmp = compare(key, node.key);
            if (cmp < 0) {
                node.left = put(node.left, key, value);
            } else if (cmp > 0) {
                node.right = put(node.right, key, value);
            } else {
                node.value = value;
            }
        }
        return node;
    }

    public boolean containsKey(K key){
        return get(root, key) != null;
    }

    public V lookup(K key){
        Node node = get(root, key);
        V result = null;
        if (node != null) {
            result = node.value;
        }
        return result;
    }

    public int heightOfKey(K key){
        return 0;
    }

    public void erase(K key){
        root = erase(root, key);
    }
    private Node erase(Node node, K key) {
        if (node == null) {
            return null; // Key not found, return null node
        }
        int cmp = compare(key, node.key);

        Node result = null;
        if (cmp < 0) {
            node.left = erase(node.left, key);
            if (node.left != null) {
                node.left.parent = node; // Update parent pointer of the new left child
            }
        } else if (cmp > 0) {
            node.right = erase(node.right, key);
            if (node.right != null) {
                node.right.parent = node; // Update parent pointer of the new right child
            }
        } else {
            if (node.left == null) {
                size--;
                result = node.right;
                if (result != null) {
                    result.parent = node.parent; // Update parent pointer of the replacement node
                }
            } else if (node.right == null) {
                size--;
                result = node.left;
                if (result != null) {
                    result.parent = node.parent; // Update parent pointer of the replacement node
                }
            } else {
                Node min = node.right;
                while (min.left != null) {
                    min = min.left;
                }
                node.key = min.key;
                node.value = min.value;
                node.right = erase(node.right, min.key);
                if (node.right != null) {
                    node.right.parent = node; // Update parent pointer of the new right child
                }
            }
        }
        return result;
    }

    public java.util.Collection<K> keys(){
        java.util.List<K> list = new java.util.ArrayList<K>();
        inorderTraversal(root, list);
        return list;
    }
    private void inorderTraversal(Node node, java.util.List<K> list) {
        if (node != null) {
            inorderTraversal(node.left, list);
            list.add(node.key);
            inorderTraversal(node.right, list);
        }
    }

    public int size(){
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    public void clear(){
        root = null;
        size = 0;
    }
    private int compare(K a, K b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<K>) a).compareTo(b);
    }


    private Node get(Node node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = compare(key, node.key);

        Node result = null;
        if (cmp == 0) {
            result = node;
        } else if (cmp < 0) {
            result = get(node.left, key);
        } else {
            result = get(node.right, key);
        }

        return result;
    }

    public java.util.Iterator<ULTreeMap.Mapping<K,V>> iterator() {
        return new java.util.Iterator<ULTreeMap.Mapping<K, V>>() {
            private Node current = findMin(root);
            private int expectedModCount = size;

            private Node findMin(Node node) {
                while (node != null && node.left != null) {
                    node = node.left;
                }
                return node;
            }

            public boolean hasNext() {
                if (expectedModCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                return current != null;
            }

            public ULTreeMap.Mapping<K, V> next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }

                Node nextNode = current;
                current = findSuccessor(current);

                return new ULTreeMap.Mapping<>(nextNode.key, nextNode.value);
            }

            private Node findSuccessor(Node node) {
                if (node == null) {
                    return null;
                }

                if (node.right != null) {
                    return findMin(node.right);
                } else {
                    Node parent = node.parent;
                    while (parent != null && node == parent.right) {
                        node = parent;
                        parent = parent.parent;
                    }
                    return parent;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
