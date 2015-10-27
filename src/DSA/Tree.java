package DSA;

import java.util.Arrays;

/**
 * Created by alex on 27.10.15.
 */
public class Tree {

    private Node[] array;
    private int capacity;

    public Tree(int capacity) {
        this.capacity = capacity;
        array = new Node[capacity];
    }

    private class Node {

        int iData;
        double fData;
        boolean isDeleted;

    }

    public Node find(int key) {
        if (array[0] == null || array[0].isDeleted == true)
            return null;
        Node current = array[0];
        int i = 0;
        while(current.iData != key) {
            if (key < current.iData)
                current = array[2*i + 1];
            else
                current = array[2*i + 2];
            if (current == null || current.isDeleted == true)
                return null;
            i++;
        }

        return current;
    }

    public void insert(int id, double dd) {
        int i = 0, j = 0;
        Node newNode = new Node();
        newNode.iData = id;
        newNode.fData = dd;
        if (array[0] == null || array[0].isDeleted == true)
            array[0] = newNode;
        else {
            Node current = array[0];
            while (true) {
                if (id < current.iData) {
                    j = 2 * i + 1;
                    current = array[j];
                    if (current == null || array[0].isDeleted == true) {
                        array[j] = newNode;
                        return;
                    }
                } else {
                    j = 2 * i + 2;
                    if (array[j] == null || array[0].isDeleted == true) {
                        array[j] = newNode;
                        return;
                    }
                }
                i++;
            }
        }
    }

    public void delete(int id) {
        find(id).isDeleted = true;
    }

    public String toString() {
        return Arrays.toString(array);
    }
}
