package DSA;

/**
 * Created by alex on 27.10.15.
 */
public class TreeApp {
    public static void main(String[] args) {
        Tree tree = new Tree(10);
        tree.insert(50, 1.5);
        tree.insert(25, 1.7);
        tree.insert(75, 1.9);
        tree.insert(65, 1.9);
        tree.insert(85, 1.9);
        tree.delete(25);

        if (tree.find(85) != null)
            System.out.println("Found the node with key 85");
        else
            System.out.println("Could not find node with key 85");
        System.out.println(tree);
    }
}
