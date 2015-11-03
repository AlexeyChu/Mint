package Contest_Ass3_1.Problem_C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by alex on 31.10.15.
 */

public class RedBlackTree<Key extends Comparable<Key>> {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private static BufferedReader reader;

    private Node root;          // root of BST

    private class Node {
        private Key key;              // sorted by key
        private Node left, right;     // left and right subtrees
        private Node parent;
        private boolean color;

        Node(Key key) {
            this.key = key;
            this.color = BLACK;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
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
        Node z = new Node(findKey);
        Node y = null;
        Node x = root;
        while(x != null) {
            y = x;
            if (z.key.compareTo(x.key) < 0) {
                x = x.left;
            } else
                x = x.right;
        }
        z.parent = y;
        if (y == null)
            root = z;
        else if (z.key.compareTo(y.key) < 0)
            y.left = z;
        else
            y.right = z;
        z.left = null;
        z.right = null;
        z.color = RED;
        insertFixUp(z);
    }

    private void insertFixUp(Node z) {
        while (z != root && isRed(z.parent)) {
        /* we have a violation */
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (isRed(y)) {

                /* uncle is RED */
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {

                /* uncle is BLACK */
                    if (z == z.parent.right) {
                    /* make x a left child */
                        z = z.parent;
                        rotateLeft(z);
                    }

                /* recolor and rotate */
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateRight(z.parent.parent);
                }
            } else {

            /* mirror image of above code */
                Node y = z.parent.parent.left;
                //System.out.println(y.key);
                if (isRed(y)) {

                /* uncle is RED */
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {

                /* uncle is BLACK */
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(Node x) {

        /**************************
         *  rotate node x to left *
         **************************/

        Node y = x.right;

    /* establish x->right link */
        x.right = y.left;
        if (y.left != null) y.left.parent = x;

    /* establish y->parent link */
        /*if (y != null)*/ y.parent = x.parent;
        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;

    /* link x and y */
        y.left = x;
        /*if (x != null)*/ x.parent = y;
    }

    private void rotateRight(Node x) {

        /****************************
         *  rotate node x to right  *
         ****************************/

        Node y = x.left;

    /* establish x->left link */
        x.left = y.right;
        if (y.right != null) y.right.parent = x;

    /* establish y->parent link */
        /*if (y != null)*/ y.parent = x.parent;
        if (x.parent == null)
            root = y;
        else if (x == x.parent.right)
                x.parent.right = y;
        else
                x.parent.left = y;

    /* link x and y */
        y.right = x;
        /*if (x != null)*/ x.parent = y;
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

    public static void main(String[] args) {
        try {
            RedBlackTree<Integer> rbt = new RedBlackTree();

            reader = new BufferedReader(new FileReader(new File("rbt.in")));
            PrintWriter writer = new PrintWriter(new File("rbt.out"));

            int[] nums = parseLine();
            for (int i = 0; i < nums.length; i++) {
                rbt.insert(nums[i]);
            }

            nums = parseLine();
            for (int i = 0; i < nums.length; i++) {
                Integer rightChild = rbt.getRightChild(nums[i]);
                writer.print(rightChild + " ");
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

