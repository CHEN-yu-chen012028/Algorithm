// TreeTraversal.java
class Node {
    int data;
    Node left, right;

    public Node(int item) {
        data = item;
        left = right = null;
    }
}

public class TreeTraversal {
    Node root;

    public TreeTraversal() {
        root = null;
    }

    // 1. 前序走訪 Preorder Traversal (Root -> Left -> Right)
    public void preorder(Node node) {
        if (node == null) return;
        
        System.out.print(node.data + " "); // 訪問根節點
        preorder(node.left);             // 遞迴左子樹
        preorder(node.right);            // 遞迴右子樹
    }

    // 2. 中序走訪 Inorder Traversal (Left -> Root -> Right)
    public void inorder(Node node) {
        if (node == null) return;
        
        inorder(node.left);              // 遞迴左子樹
        System.out.print(node.data + " "); // 訪問根節點
        inorder(node.right);             // 遞迴右子樹
    }

    // 3. 後序走訪 Postorder Traversal (Left -> Right -> Root)
    public void postorder(Node node) {
        if (node == null) return;
        
        postorder(node.left);            // 遞迴左子樹
        postorder(node.right);           // 遞迴右子樹
        System.out.print(node.data + " "); // 訪問根節點
    }

    public static void main(String[] args) {
        TreeTraversal tree = new TreeTraversal();
        
        /* 建立測試樹結構:
              1
            /   \
           2     3
          / \
         4   5
        */
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        System.out.println("Preorder traversal:");
        tree.preorder(tree.root);

        System.out.println("\nInorder traversal:");
        tree.inorder(tree.root);

        System.out.println("\nPostorder traversal:");
        tree.postorder(tree.root);
    }
}