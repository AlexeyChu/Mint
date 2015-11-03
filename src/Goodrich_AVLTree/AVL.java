package Goodrich_AVLTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by alex on 03.11.15.
 */
public class AVL {

    private static BufferedReader reader;

    private Node root;

    private class Node // структура для представления узлов дерева
    {
        private int key;
        private int height;
        private Node left;
        private Node right;
        private Node (int k) {
            if (root == null)
                root = this;
            key = k; height = 1; }
    }

    public void setKey(Node x, int k) {
        if (x != null)
            x.key = k;
    }

    public Node getRight(Node x) {
        if (x == null)
            return null;
        return x.right;
    }

    public void setRight(Node parent, Node child) {
        if (parent != null)
            parent.right = child;

    }

    public Node getLeft(Node x) {
        if (x == null)
            return null;
        return x.left;
    }

    private int getKey(Node x) {
        if (x == null)
            return 0;
        return x.key;
    }

    int height(Node p)
    {
        return p != null ? p.height : 0;
    }

    int bfactor(Node p)
    {
        return height(p.right)-height(p.left);
    }

    void fixheight(Node p)
    {
        int hl = height(p.left);
        int hr = height(p.right);
        p.height = (hl>hr?hl:hr)+1;
    }

    Node rotateright(Node p) // правый поворот вокруг p
    {
        Node q = p.left;
        p.left = q.right;
        q.right = p;
        fixheight(p);
        fixheight(q);
        return q;
    }

    Node rotateleft(Node q) // левый поворот вокруг q
    {
        Node p = q.right;
        q.right = p.left;
        p.left = q;
        fixheight(q);
        fixheight(p);
        return p;
    }

    Node balance(Node p) // балансировка узла p
    {
        fixheight(p);
        if( bfactor(p)==2 )
        {
            if( bfactor(p.right) < 0 )
                p.right = rotateright(p.right);
            return rotateleft(p);
        }
        if( bfactor(p)==-2 )
        {
            if( bfactor(p.left) > 0  )
                p.left = rotateleft(p.left);
            return rotateright(p);
        }
        return p; // балансировка не нужна
    }

    public void insert (int k) {
        root = insert(root, k);
    }

    private Node insert(Node p, int k) // вставка ключа k в дерево с корнем p
    {
        //Node p = new Node(k);
        setKey(p, k);
        if (p == null)
            return p;
        if( k < getKey(p))
            p.left = insert(p.left,k);
        else
            p.right = insert(p.right,k);
        return balance(p);
    }

    Node findmin(Node p) // поиск узла с минимальным ключом в дереве p
    {
        return p.left != null ? findmin(p.left):p;
    }

    Node removemin(Node p) // удаление узла с минимальным ключом из дерева p
    {
        if( p.left == null )
            return p.right;
        p.left = removemin(p.left);
        return balance(p);
    }

    Node remove(Node p, int k) // удаление ключа k из дерева p
    {
        if( p == null ) return null;
        if( k < getKey(p) )
            p.left = remove(p.left,k);
        else if( k > getKey(p) )
            p.right = remove(p.right,k);
        else //  k == p->key
        {
            Node q = p.left;
            Node r = p.right;
           // delete p;
            if(r == null) return q;
            Node min = findmin(r);
            min.right = removemin(r);
            min.left = q;
            return balance(min);
        }
        return balance(p);
    }

    public Node find(int key) {
        Node current = root;
        while (current.key != key) {
            if (key - (current.key) < 0)
                current = current.left;
            else
                current = current.right;
            if (current == null)
                return null;
        }
        return current;
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

    public static void main(String[] args) {
        try {
            AVL rbt = new AVL();

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
