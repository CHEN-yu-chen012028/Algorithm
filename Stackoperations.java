class Stack {
    private int maxSize;
    private int[] stackArray;
    private int top;

    // 建構子
    public Stack(int size) {
        maxSize = size;
        stackArray = new int[maxSize];
        top = -1;
    }

    // push：放入元素
    public void push(int value) {
        if (top == maxSize - 1) {
            System.out.println("Stack Overflow");
        } else {
            stackArray[++top] = value;
            System.out.println("Push: " + value);
        }
    }

    // pop：取出元素
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow");
            return -1;
        } else {
            return stackArray[top--];
        }
    }

    // peek：查看最上面
    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return -1;
        } else {
            return stackArray[top];
        }
    }

    // 判斷是否為空
    public boolean isEmpty() {
        return (top == -1);
    }
}
public class Stackoperations {
    public static void main(String[] args) {
        Stack s = new Stack(5);

        s.push(10);
        s.push(20);
        s.push(30);

        System.out.println("Top element: " + s.peek());

        System.out.println("Pop: " + s.pop());
        System.out.println("Pop: " + s.pop());

        System.out.println("Is Empty: " + s.isEmpty());
    }
}