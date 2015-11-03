package Semen;

import java.io.*;
import java.util.Comparator;

class Node
{

    Node leftNode = null;
    Node rightNode = null;
    Integer value = 0;
    int height;
    Node(Integer value)
    {
        this.value = value;
        height = 1;
    }

}

class AVLTree<Key extends Comparable<Key>> extends BinarySearchTree
{


    int height(Node n)
    {
        return (n != null ? n.height : 0);
    }

    int balance_factor(Node n)
    {
        return (height(n.rightNode) - height(n.leftNode));
    }

    void fixheight(Node n)
    {
        int h_left = height(n.leftNode);
        int h_right = height(n.rightNode);
        n.height = (h_left > h_right ? h_left : h_right) + 1;
    }

    Node rotate_right(Node n)
    {
        Node q = n.leftNode;
        n.leftNode = q.rightNode;
        q.rightNode = n;
        fixheight(n);
        fixheight(q);
        return q;
    }

    Node rotate_left(Node n)
    {
        Node q = n.rightNode;
        n.rightNode = q.leftNode;
        q.leftNode = n;
        fixheight(n);
        fixheight(q);
        return q;
    }

    Node balance(Node n)
    {
        fixheight(n);
        if(balance_factor(n) == 2)
        {
            if( balance_factor(n.rightNode) < 0 )
                n.rightNode = rotate_right(n.rightNode);
            return rotate_left(n);
        }
        if( balance_factor(n) == -2 )
        {
            if( balance_factor(n.leftNode) > 0  )
                n.leftNode = rotate_left(n.leftNode);
            return rotate_right(n);
        }
        return n;
    }

    @Override
    Node insert(Node n, Integer k)
    {
        if (n == null) {
            return new Node(k);
        }
        else
        {
            if (n.value > k) { n.leftNode = insert(n.leftNode, k);}
            else { n.rightNode = insert(n.rightNode, k); }
        }
        return balance(n);
    }

    @Override
    Node delete(Node n, Integer k)
    {
        if (n == null) {return null;}
        if (k < n.value)
            n.leftNode = delete (n.leftNode, k);
        else
        if (k > n.value)
            n.rightNode = delete (n.rightNode, k);
        else
        {
            Node q = n.leftNode;
            Node r = n.rightNode;

            if (q == null) {return r;}

            Node min = findMinData(q);

//            printTree(q);
//            System.out.println();
            min.leftNode = removeMinData(q);
            min.rightNode = r;

            return balance(min);
        }
        return balance(n);
    }

    Node findMinData(Node node)
    {
        return ((node.rightNode != null ? findMinData(node.rightNode) : node));
    }

    Node removeMinData(Node n)
    {
        if( n.rightNode == null )
            return n.leftNode;
        n.rightNode = removeMinData(n.rightNode);
        return balance(n);
    }
}

class BinarySearchTree
{
    Node rootNode;

    BinarySearchTree()
    {
        rootNode = null;
    }

    void printTree(Node n)
    {
        if (n != null)
        {
            if (n.leftNode != null) {System.out.println(String.valueOf(n.value)+" => " + String.valueOf(n.leftNode.value));}
            if (n.rightNode != null) {System.out.println(String.valueOf(n.value)+" => " + String.valueOf(n.rightNode.value));}
            printTree(n.leftNode);
            printTree(n.rightNode);
        }
    }

    Node insert(Node n, Integer k)
    {
        if (n == null) {
            return new Node(k);
        }
        else
        {
            if (n.value > k) { n.leftNode = insert(n.leftNode, k);}
            else { n.rightNode = insert(n.rightNode, k); }
        }
        return n;
    }

    void insert(Integer k) {
        rootNode = insert(rootNode, k);
    }

    Node delete(Node n, Integer k)
    {
        if (n == null) {return null;}
        if (k < n.value)
            n.leftNode = delete (n.leftNode, k);
        else
        if (k > n.value)
            n.rightNode = delete (n.rightNode, k);
        else
        {
            if (n.leftNode == null) return n.rightNode;
            else
            if (n.rightNode == null) return n.leftNode;
            else
            {
                n.value = retrieveData(n.leftNode);
                n.leftNode =  delete(n.leftNode, n.value) ;
            }
        }
        return n;

    }

    Integer retrieveData(Node node)
    {
        while (node.rightNode != null) node = node.rightNode;
        return node.value;
    }


    public void delete(Integer k)
    {
        rootNode = delete(rootNode, k);
    }

    Node find(Node n, Integer k)
    {
        if (n == null) {return null;}
        else if (n.value.equals(k)) {return n;}
        else
        {
            if (n.value > k) {return find(n.leftNode, k);}
            else { return find(n.rightNode, k);}
        }
    }

    Node find(Integer k)
    {
        return find(rootNode, k);
    }
}

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String parameter = "avl";
        FileInputStream in = new FileInputStream(parameter+".in");
        FileOutputStream out = new FileOutputStream(parameter+".out");
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        // Initializing tree
        BinarySearchTree tree;
        if (parameter.equals("bst"))
        {
            tree = new BinarySearchTree();
        }
        else if (parameter.equals("avl"))
        {
            tree = new AVLTree();
        }
        else
        {
            tree = new BinarySearchTree();
        }

        String insertNodes = bf.readLine();
        for (String node: insertNodes.split(" "))
        {
            Integer intNode = Integer.valueOf(node);
            tree.insert(intNode);
        }
        tree.printTree(tree.rootNode);
        System.out.println();

        String deleteNodes = bf.readLine();
        if (deleteNodes.length() > 0)
        {
            for (String node : deleteNodes.split(" "))
            {
                Integer intNode = Integer.valueOf(node);
                tree.delete(intNode);
            }
        }
        tree.printTree(tree.rootNode);

        String findNodes = bf.readLine();
        for (String node: findNodes.split(" "))
        {
            Integer intNode = Integer.valueOf(node);
            Node findNode = tree.find(intNode);
            Node right = (findNode == null ? null : findNode.rightNode);
            if (right == null)
            {
                out.write("null".getBytes());
            }
            else
            {
                out.write(String.valueOf(right.value).getBytes());
            }
            out.write(" ".getBytes());
        }

        //out.flush();
        in.close();
        out.close();
    }
}