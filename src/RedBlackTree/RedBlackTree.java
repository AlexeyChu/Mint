package RedBlackTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by alex on 31.10.15.
 */

public class RedBlackTree {

    private static BufferedReader reader;

    private Node root;          // root of BST

    private class Node {
        private int key;              // sorted by key
        private Node left, right;     // left and right subtrees
        private Node parent;
        private boolean isRed;

        Node(int key) {
            this.key = key;
            this.isRed = true;
        }
    }

    public Node find(int key) {
        Node current = root;
        while(current.key != key) {
            if (key < current.key)
                current = current.left;
            else
                current = current.right;
            if (current == null)
                return null;
        }
        return current;
    }

    public void insert(int findKey) {
        Node z = new Node(findKey);
        Node y = null;
        Node x = root;
        while(x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else
                x = x.right;
        }
        z.parent = y;
        if (y == null)
            root = z;
        else if (z.key < y.key)
            y.left = z;
        else
            y.right = z;
        z.left = null;
        z.right = null;
        z.isRed = true;
        insertFixUp(z);
    }

    private void insertFixUp(Node x) {
        while (x != root && x.parent.isRed) {
        /* we have a violation */
            if (x.parent == x.parent.parent.left) {
                Node y = x.parent.parent.right;
                if (y.isRed) {

                /* uncle is RED */
                    x.parent.isRed = false;
                    y.isRed = false;
                    x.parent.parent.isRed = true;
                    x = x.parent.parent;
                } else {

                /* uncle is BLACK */
                    if (x == x.parent.right) {
                    /* make x a left child */
                        x = x.parent;
                        rotateLeft(x);
                    }

                /* recolor and rotate */
                    x.parent.isRed = false;
                    x.parent.parent.isRed = true;
                    rotateRight(x.parent.parent);
                }
            } else {

            /* mirror image of above code */
                Node y = x.parent.parent.left;
                //System.out.println(y.key);
                if (y.isRed) {

                /* uncle is RED */
                    x.parent.isRed = false;
                    y.isRed = false;
                    x.parent.parent.isRed = true;
                    x = x.parent.parent;
                } else {

                /* uncle is BLACK */
                    if (x == x.parent.left) {
                        x = x.parent;
                        rotateRight(x);
                    }
                    x.parent.isRed = false;
                    x.parent.parent.isRed = true;
                    rotateLeft(x.parent.parent);
                }
            }
        }
        root.isRed = false;
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

    public Integer getRightChild(int key) {
        Node q = find(key);
        Node right = q == null ? null : q.right;
        if (right == null)
            return null;
        else
            return right.key;
    }

    public static void main(String[] args) throws Exception {
        RedBlackTree bst = new RedBlackTree();

        reader = new BufferedReader(new FileReader(new File("bst.in")));
        PrintWriter writer = new PrintWriter(new File("bst.out"));

        int[] nums = parseLine();
        for (int i = 0; i < nums.length; i++) {
            bst.insert(nums[i]);
        }

        nums = parseLine();
        for (int i = 0; i < nums.length; i++) {
            Integer rightChild = bst.getRightChild(nums[i]);
            writer.print(rightChild + " ");
        }
        reader.close();
        writer.close();
    }
}

