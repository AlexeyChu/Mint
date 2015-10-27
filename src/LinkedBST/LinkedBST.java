package LinkedBST;

/**
 * Created by alex on 27.10.15.
 */
public class LinkedBST {

    private Node root;

    private class Node {
        int key;
        long value;
        Node leftChild;
        Node rightChild;
    }

    public Node find (int key) {
        Node current = root;
        while (current.key != key) {
            if (key < current.key)
                current = current.leftChild;
            else
                current = current.rightChild;
            if (current == null)
                return null;
        }
        return current;
    }

    public void insert (int key) {
        Node node = new Node();
        node.key = key;
        if (root == null)
            root = node;
        else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                if (key < current.key) {
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = node;
                        return;
                    }
                } else {
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = node;
                        return;
                    }
                }
            }
        }
    }

    public boolean delete(int key) {

        return false;
    }
}
