import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Queue<Integer> queue = new LinkedList<>();

        // Enqueue
        for (int i = 1; i <= 5; i++) {
            queue.offer(i);
            System.out.println("Enqueue: " + i);
        }

        // Dequeue
        while (!queue.isEmpty()) {
            System.out.println("Dequeue: " + queue.poll());
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Execution Time (ns): " + duration);
    }
}