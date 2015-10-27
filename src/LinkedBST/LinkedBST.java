package LinkedBST;

/**
 * Created by alex on 27.10.15.
 */
public class LinkedBST {

    private Node root;

    private class Node {
        int iData;
        Node leftChild;
        Node rightChild;
    }

    public Node find (int key) {
        Node current = root;
        while (current.iData != key) {
            if (key < current.iData)
                current = current.leftChild;
            else
                current = current.rightChild;
            if (current == null)
                return null;
        }
        return current;
    }

    public void insert (int key) {

    }

    public boolean delete(int key) {

        return false;
    }
}
