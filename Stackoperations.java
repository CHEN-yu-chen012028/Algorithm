import java.util.Scanner;
import java.util.Stack;

public class StackInputExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Integer> stack = new Stack<>();

        System.out.print("請輸入數字個數: ");
        int n = scanner.nextInt();

        long startTime = System.nanoTime();

        // 使用者輸入並 push
        for (int i = 0; i < n; i++) {
            System.out.print("輸入數字: ");
            int num = scanner.nextInt();
            stack.push(num);
        }

        System.out.println("\nStack 輸出 (LIFO):");

        // pop 並輸出
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

        long endTime = System.nanoTime();

        System.out.println("Execution Time (ns): " + (endTime - startTime));

        scanner.close();
    }
}
