import java.util.Scanner;

public class RedBlackTree {
    static class Node {
        int data;
        Node left, right, parent;
        int color; // 0 for Red, 1 for Black

        public Node(int data) {
            this.data = data;
            this.left = this.right = this.parent = null;
            this.color = 0; // 新節點預設為紅色
        }
    }

    private Node root;
    private final int RED = 0;
    private final int BLACK = 1;

    public void insert(int data) {
        Node node = new Node(data);
        if (root == null) {
            root = node;
            root.color = BLACK;
            return;
        }

        Node y = null;
        Node x = root;

        while (x != null) {
            y = x;
            if (node.data < x.data) x = x.left;
            else x = x.right;
        }

        node.parent = y;
        if (node.data < y.data) y.left = node;
        else y.right = node;

        fixInsert(node);
    }

    private void fixInsert(Node k) {
        while (k.parent != null && k.parent.color == RED) {
            if (k.parent == k.parent.parent.left) {
                Node u = k.parent.parent.right;
                if (u != null && u.color == RED) {
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            } else {
                Node u = k.parent.parent.left;
                if (u != null && u.color == RED) {
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            }
            if (k == root) break;
        }
        root.color = BLACK;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null) x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == null) root = x;
        else if (y == y.parent.left) y.parent.left = x;
        else y.parent.right = x;
        x.right = y;
        y.parent = x;
    }

    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + "(" + (node.color == RED ? "R" : "B") + ") ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Red-Black Tree 插入測試 ===");
        
        // 1. 先詢問總數
        System.out.print("請問您總共要插入幾個數字？ ");
        int total = scanner.nextInt();

        // 2. 使用 for 迴圈精確執行該次數
        for (int i = 1; i <= total; i++) {
            System.out.printf("請輸入第 %d/%d 個數字: ", i, total);
            int val = scanner.nextInt();
            
            long startTime = System.nanoTime();
            rbt.insert(val);
            long endTime = System.nanoTime();
            
            System.out.print("目前樹狀結構 (中序): ");
            rbt.inorder(rbt.root);
            System.out.println("\n插入耗時: " + (endTime - startTime) + " ns\n");
        }

        System.out.println("-------------------------------");
        System.out.println("所有數字插入完成！");
        System.out.println("時間複雜度證明: O(log n)");
        
        scanner.close();
    }
    }