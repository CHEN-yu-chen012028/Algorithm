import java.util.Scanner;
import java.util.Stack;

public class StackInputExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Integer> stack = new Stack<>();

        System.out.print("請輸入數字個數 (n): ");
        int n = scanner.nextInt();

        // 🎯 這裡開始計時
        long startTime = System.nanoTime();

        // 1. Push 操作 (每一步都是 O(1))
        for (int i = 0; i < n; i++) {
            System.out.print("輸入第 " + (i + 1) + " 個數字: ");
            int num = scanner.nextInt();
            stack.push(num);
        }

        System.out.println("\n--- Stack 輸出 (LIFO 順序) ---");

        // 2. Pop 操作 (每一步都是 O(1))
        while (!stack.isEmpty()) {
            System.out.println("彈出: " + stack.pop());
        }

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // --- 🎯 新增：時間複雜度分析輸出 ---
       System.out.println("\n----------------------------------------");
        System.out.println("效能指標 (Performance Metrics)");
        System.out.println("資料規模 (Input Size n): " + n);
        System.out.println("操作複雜度 (Step Complexity): O(1)");
        System.out.println("總體複雜度 (Total Complexity): O(n)");
        System.out.println("實測執行時間 (Runtime): " + totalTime + " ns");
        System.out.println("----------------------------------------");
    }
}