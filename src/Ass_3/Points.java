package Ass_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by alex on 03.11.15.
 */

public class Points {

    private static NodeBST<Point> bst = new NodeBST();
    private static NodeBST<Point> bstN = new NodeBST();
    private static BufferedReader reader;
    private static Point last;

    public static int[] parseLine() throws Exception {
        String[] lines = reader.readLine().split(" ");
        int[] nums = new int[lines.length];
        for (int i = 0; i < lines.length; i++) {
            nums[i] = Integer.parseInt(lines[i]);
        }
        return nums;
    }

    public static void putIntoTree(int[] a) {
        Point p0 = null, pN = null;
        last = new Point(a[a.length - 2], a[a.length - 1]);
        for (int i = 0; i < a.length; i+=2) {
            p0 = new Point(a[i], a[i+1]);
            //System.out.println("Distance: " + p.getDistance());
            bst.insert(p0);
           // bstN.insert(pN);
        }
    }

    public static void main(String[] args) {
        try {
           // NodeBST<Point> bst = new NodeBST();
            reader = new BufferedReader(new FileReader(new File("data.in")));
            PrintWriter writer = new PrintWriter(new File("data.out"));
            int[] points = parseLine();
            putIntoTree(points);

            Point min = bst.findMin();
            Point max = bst.findMax();

            Point closest = bst.closest(last);
            System.out.println(min.getDistance() + " : " + max.getDistance() + " : " + closest.getDistance());
            System.out.println(Arrays.toString(points));
            writer.print("" + min.getDistance() + " " + max.getDistance() + " " + closest.getDistance());

            reader.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class NodeBST<Key extends Comparable <Key>> {

    private Key closest;

    public Key getClosest() {
        return closest;
    }

    private static BufferedReader reader;

    private Node root;          // root of BST

    private class Node {
        private Node parent;
        private Key key;              // sorted by key
        private Node left, right;     // left and right subtrees

        Node(Key key) {
            this.key = key;
        }
    }

    public Key closest(Key key) {
        int comp = floor(key).compareTo(key);
        int comp2 = ceiling(key).compareTo(key);
        if (comp < comp2)
            return floor(key);
        else
            return ceiling(key);
    }

    public Key floor(Key key) {
        //if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) return null;
        else           return x.key;
    }

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else           return x;
    }

    public Key ceiling(Key key) {
        //if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) return null;
        else           return x.key;
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0)  return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t;
        else           return x;
    }

    public Key findMin() {
        System.out.println("Min: ");
        Node current = root;
        Node parent = current;
        int min = Integer.MAX_VALUE;
        while(current != null) {
            parent = current;
            System.out.print(current.key + " ");
            current = current.left;
        }
        System.out.println();
        return parent.key;
    }

    public Key findMax() {
        System.out.println("Max: ");
        Node current = root;
        Node parent = current;
        int min = Integer.MAX_VALUE;
        while(current != null) {
            parent = current;
            System.out.print(current.key + " ");
            current = current.right;
        }
        System.out.println();
        return parent.key;
    }

    public Node find(Key key) {
        Node current = root;
        while(current.key != key) {
            if (key.compareTo(current.key) < 0)
                current = current.left;
            else
                current = current.right;
            if (current == null)
                return null;

        }
        return current;
    }

    public void insert(Key findKey) {
        Node node = new Node(findKey);
        if (root == null)
            root = node;
        else {
            Node current = root;
            Node parent;
            while(true) {
                parent = current;
                if (findKey.compareTo(current.key) < 0) {
                    current = current.left;
                    if (current == null) {
                        parent.left = node;
                        return;
                    }
                }
                else {
                    current = current.right;
                    if(current == null) {
                        parent.right = node;
                        return;
                    }
                }
            }
        }
    }

    public boolean delete(Key findKey) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while(current.key != findKey) {
            parent = current;
            if (findKey.compareTo(current.key) < 0) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null)
                return false;
        }

        if (current.left == null && current.right == null) {
            if (current == root)
                root = null;
            else if (isLeftChild)
                parent.left = null;
            else
                parent.right = null;
        }
        else if (current.right == null) {
            if (current == root)
                root = current.left;
            else if (isLeftChild)
                parent.left = current.left;
            else
                parent.right = current.left;
        } else if(current.left == null) {
            if (current == root)
                root = current.right;
            else if (isLeftChild)
                parent.left = current.right;
            else
                parent.right = current.right;
        } else {
            Node predecessor = getPredecessor(current);
            if (current == root)
                root = predecessor;
            else if (isLeftChild)
                parent.left = predecessor;
            else
                parent.right = predecessor;
            predecessor.left = current.left;
        }
        return true;
    }

    private Node getPredecessor(Node delNode) {
        Node predecessorParent = delNode;
        Node predecessor = delNode;
        Node current = delNode.left;
        while(current != null) {
            predecessorParent = predecessor;
            predecessor = current;
            current = current.right;
        }

        if (predecessor != delNode.left) {
            predecessorParent.right = predecessor.left;
            predecessor.left = delNode.left;
            predecessor.right = delNode.right;
        }
        return predecessor;
    }

    public static int[] parseLine() throws Exception {
        String[] lines = reader.readLine().split(" ");
        int[] nums = new int[lines.length];
        for (int i = 0; i < lines.length; i++) {
            nums[i] = Integer.parseInt(lines[i]);
        }
        return nums;
    }

    public Key getRightChild(Key key) {
        Node q = find(key);
        Node right = q == null ? null : q.right;
        if (right == null)
            return null;
        else
            return right.key;
    }
}

class Point implements Comparable<Point> {

    private int x;
    private int y;
    private int distance;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        distance = getDistance();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return (int) (Math.sqrt(Math.pow((this.x), 2) + Math.pow((this.y), 2)));
    }

    @Override
    public int compareTo(Point p) {
        return (this.distance - p.distance);
    }
}
