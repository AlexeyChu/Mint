package LinkedBSTwithValues;

import edu.princeton.cs.introcs.StdOut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.NoSuchElementException;

/**
 * Created by alex on 27.10.15.
 */
class LinkedBST<Key extends Comparable <Key>/*, Value*/> /*implements Iterable<Key>*/ {

    private static BufferedReader reader;

    private Node root;

    private class Node {
        private Key key;
        //private Value val;
        private Node leftChild;
        private Node rightChild;
        private int N;

        public Node(Key key,/* Value val,*/ int N) {
            this.key = key;
            // this.val = val;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void put(Key key/*, Value val*/) {
        /*if (val == null) {
            delete(key);
            return;
        }*/
        root = put(root, key/*, val*/);
        assert check();
    }

    private Node put(Node x, Key key/*, Value val*/) {
        if (x == null) return new Node(key/*, val*/, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.leftChild = put(x.leftChild, key/*, val*/);
        else if (cmp > 0) x.rightChild = put(x.rightChild, key/*, val*/);
        // else              x.val   = val;
        x.N = 1 + size(x.leftChild) + size(x.rightChild);
        return x;
    }

    public Node get(Key key) {
        return get(root, key);
    }

    private Node get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.leftChild, key);
        else if (cmp > 0) return get(x.rightChild, key);
        else return x;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x) {
        if (x.leftChild == null) return x.rightChild;
        x.leftChild = deleteMin(x.leftChild);
        x.N = size(x.leftChild) + size(x.rightChild) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x.rightChild == null) return x.leftChild;
        x.rightChild = deleteMax(x.rightChild);
        x.N = size(x.leftChild) + size(x.rightChild) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.leftChild = delete(x.leftChild, key);
        else if (cmp > 0) x.rightChild = delete(x.rightChild, key);
        else {
            if (x.rightChild == null) return x.leftChild;
            if (x.leftChild == null) return x.rightChild;
            Node t = x;
            x = min(t.rightChild);
            x.rightChild = deleteMin(t.rightChild);
            x.leftChild = t.leftChild;
        }
        x.N = size(x.leftChild) + size(x.rightChild) + 1;
        return x;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.leftChild == null) return x;
        else return min(x.leftChild);
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.rightChild == null) return x;
        else return max(x.rightChild);
    }

    public Key floor(Key key) {
        if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.leftChild, key);
        Node t = floor(x.rightChild, key);
        if (t != null) return t;
        else return x;
    }

    public Key ceiling(Key key) {
        if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) {
            Node t = ceiling(x.leftChild, key);
            if (t != null) return t;
            else return x;
        }
        return ceiling(x.rightChild, key);
    }

    public Key select(int k) {
        if (k < 0 || k >= size()) throw new IllegalArgumentException();
        Node x = select(root, k);
        return x.key;
    }

    // Return key of rank k.
    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.leftChild);
        if (t > k) return select(x.leftChild, k);
        else if (t < k) return select(x.rightChild, k - t - 1);
        else return x;
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.leftChild);
        else if (cmp > 0) return 1 + size(x.leftChild) + rank(key, x.rightChild);
        else return size(x.leftChild);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.leftChild, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.rightChild, queue, lo, hi);
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.leftChild), height(x.rightChild));
    }

    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.leftChild);
            queue.enqueue(x.rightChild);
        }
        return keys;
    }

    private boolean check() {
        if (!isBST()) System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) System.out.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.leftChild, min, x.key) && isBST(x.rightChild, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.N != size(x.leftChild) + size(x.rightChild) + 1) return false;
        return isSizeConsistent(x.leftChild) && isSizeConsistent(x.rightChild);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    public static int[] parseLine() throws Exception {
        String[] lines = reader.readLine().split(" ");
        int[] nums = new int[lines.length];
        for (int i = 0; i < lines.length; i++) {
            nums[i] = Integer.parseInt(lines[i]);
        }
        return nums;
    }

    public void printRightChild(Key key) {
        Node q = get(key);
        Node right = q == null ? null : q.rightChild;
        if (right == null)
            System.out.print("null");
        else
            System.out.print(right.key);
    }

    public void print() {
        for (Key s : this.levelOrder())
            StdOut.println(s + " " + this.get(s));

    }

    public static void main(String[] args) throws Exception {
        LinkedBST<Integer> bst = new LinkedBST();
        reader = new BufferedReader(new FileReader(new File("bst.in")));

        System.out.println("Insert:");
        int[] nums = parseLine();
        for (int i = 0; i < nums.length; i++) {
            bst.put(nums[i]);
        }

        bst.print();
        StdOut.println();

       /* for (Integer s : bst.keys())
            StdOut.println(s + " " + bst.get(s));*/

        nums = parseLine();
        for (int i = 0; i < nums.length; i++) {
            bst.delete(nums[i]);
        }
        System.out.println("Delete: ");
        bst.print();
        System.out.println();

        nums = parseLine();
        for (int i = 0; i < nums.length; i++) {
            bst.printRightChild(nums[i]);
            System.out.print(" ");
        }
    }
}
