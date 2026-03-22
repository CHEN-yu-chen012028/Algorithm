import java.util.*;

// 1. 定義節點類別 (Node Class)
class HuffmanNode {
    int data; // 頻率
    char c;   // 字元
    HuffmanNode left;
    HuffmanNode right;
}

// 實作比較器，讓 PriorityQueue 依據頻率由小到大排序
class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.data - y.data;
    }
}

public class HuffmanCoding {

    // 2. 遞迴列印編碼並存儲結果
    public static void printCode(HuffmanNode root, String s) {
        // 如果是葉子節點，代表找到字元的編碼了
        if (root.left == null && root.right == null && Character.isLetter(root.c)) {
            System.out.println(root.c + ":" + s);
            return;
        }

        // 往左走加 "0"，往右走加 "1"
        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    public static void main(String[] args) {
        int n = 6;
        char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f' };
        int[] charfreq = { 5, 9, 12, 13, 16, 45 };

        // 建立優先隊列 (Min-Heap)
        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new MyComparator());

        for (int i = 0; i < n; i++) {
            HuffmanNode hn = new HuffmanNode();
            hn.c = charArray[i];
            hn.data = charfreq[i];
            hn.left = null;
            hn.right = null;
            q.add(hn);
        }

        HuffmanNode root = null;

        // 3. 構建 Huffman Tree 的核心邏輯
        while (q.size() > 1) {
            // 取出頻率最小的兩個節點
            HuffmanNode x = q.peek();
            q.poll();
            HuffmanNode y = q.peek();
            q.poll();

            // 建立一個新節點作為父節點
            HuffmanNode f = new HuffmanNode();
            f.data = x.data + y.data;
            f.c = '-'; // 非葉子節點用特殊符號代替
            f.left = x;
            f.right = y;
            root = f;

            // 將新節點放回隊列
            q.add(f);
        }

        // 4. 輸出結果與時間複雜度
        System.out.println("--- Huffman Codes ---");
        printCode(root, "");
        
        printComplexity();
    }

    // 5. Printing Time Complexity (作業要求的詳細說明)
    public static void printComplexity() {
        System.out.println("\n--- Time Complexity Analysis ---");
        System.out.println("1. Building Frequency Map: O(n)");
        System.out.println("2. Building Huffman Tree: O(n log n)");
        System.out.println("   - Each insertion/extraction in PriorityQueue takes O(log n).");
        System.out.println("   - We perform this n-1 times.");
        System.out.println("3. Generating Codes: O(n)");
        System.out.println("Total Time Complexity: O(n log n)");
        System.out.println("Space Complexity: O(n) to store the tree nodes.");
    }
}