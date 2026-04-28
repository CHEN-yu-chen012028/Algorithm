// BinaryTree.java
class Node {
    int data;
    Node left, right;

    public Node(int item) {
        data = item;
        left = right = null;
    }
}

public class BinaryTree {
    Node root;

    public BinaryTree() {
        root = null;
    }

    // Insert method to maintain BST property
    public void insert(int data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data < root.data)
            root.left = insertRec(root.left, data);
        else if (data > root.data)
            root.right = insertRec(root.right, data);
        return root;
    }

    // 1. In-order Traversal (Left, Root, Right)
    public void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

    // 2. Pre-order Traversal (Root, Left, Right)
    public void preOrder(Node node) {
        if (node == null) return;
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    // 3. Post-order Traversal (Left, Right, Root)
    public void postOrder(Node node) {
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        /* Example Tree Structure:
              50
           /     \
          30      70
         /  \    /  \
        20  40  60  80
        */
        int[] nodes = {50, 30, 20, 40, 70, 60, 80};
        for (int value : nodes) tree.insert(value);

        System.out.println("Pre-order traversal:");
        tree.preOrder(tree.root);
        
        System.out.println("\nIn-order traversal (Sorted):");
        tree.inOrder(tree.root);
        
        System.out.println("\nPost-order traversal:");
        tree.postOrder(tree.root);
    }
}