package BST;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST <Key extends Comparable <Key>> implements Iterable<Key> {

    private Node root;          // root of BST
    private int N;              // number of nodes

    private class Node {
        private Key key;              // sorted by key
        private Node left, right;     // left and right subtrees

        Node(Key key) {
            this.key = key;
            N++;
        }
    }

    public BST() {
        root = null;
        N = 0;
    }

    public int size() { return N; }

    /***************************************************************************
     *  Insert key-value pair into BST
     *  If key already exists, update with new value
     ***************************************************************************/
    public void put(Key key) {
        //if (value == null) delete(key);
        root = insert(root, key);
    }

    private Node insert(Node x, Key key) {
        if (x == null) return new Node(key);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = insert(x.left,  key);
        else if (cmp > 0) x.right = insert(x.right, key);
        else x.key = key;
        return x;
    }

    /***************************************************************************
     *  Search BST for given key, and return associated value if found,
     *  return null if not found
     ***************************************************************************/
    // does there exist a key-value pair with given key?
    public boolean contains(Key key) {
        return find(key) != null;
    }

    // return value associated with the given key, or null if no such key exists
    public Node find(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x;
        }
        return null;
    }


    /***************************************************************************
     *  Delete key and associated value.
     ***************************************************************************/
    public void delete(Key key) {
        throw new RuntimeException("Deletion operation not supported");
    }

    /***************************************************************************
     *  Iterate using an inorder traversal. Implement with a stack.
     *  Iterating through N elements takes O(N) time.
     ***************************************************************************/
    public Iterator<Key> iterator() { return new Inorder(); }

    // an iterator
    private class Inorder implements Iterator<Key> {
        private Stack<Node> stack = new Stack<Node>();

        public Inorder() {
            pushLeft(root);
        }

        // don't implement remove() - it's optional and would mutate the BST
        public void remove()      { throw new UnsupportedOperationException();  }
        public boolean hasNext()  { return !stack.isEmpty();                    }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node x = stack.pop();
            pushLeft(x.right);
            return x.key;
        }

        public void pushLeft(Node x) {
            while (x != null) {
                stack.push(x);
                x = x.left;
            }
        }

    }
}
