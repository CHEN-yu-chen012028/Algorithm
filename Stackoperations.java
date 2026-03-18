import java.util.Stack;

public class StackExample {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Stack<Integer> stack = new Stack<>();

        // Push 元素
        for (int i = 1; i <= 5; i++) {
            stack.push(i);
            System.out.println("Push: " + i);
        }

        // Pop 元素
        while (!stack.isEmpty()) {
            System.out.println("Pop: " + stack.pop());
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Execution Time (ns): " + duration);
    }
}
