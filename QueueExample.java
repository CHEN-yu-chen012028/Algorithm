import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class QueueInputExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue<Integer> queue = new LinkedList<>();

        System.out.print("請輸入數字個數: ");
        int n = scanner.nextInt();

        long startTime = System.nanoTime();

        // 使用者輸入並 enqueue
        for (int i = 0; i < n; i++) {
            System.out.print("輸入數字: ");
            int num = scanner.nextInt();
            queue.offer(num);
        }

        System.out.println("\nQueue 輸出 (FIFO):");

        // dequeue 並輸出
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }

        long endTime = System.nanoTime();

        System.out.println("Execution Time (ns): " + (endTime - startTime));

        scanner.close();
    }
}
