public class SinglyLinkedList {
    // 定義節點結構
    private class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head = null;

    // 1. 在開頭插入 (Time: O(1))
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    // 2. 在結尾插入 (Time: O(n))
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    // 3. 刪除特定數值的節點 (Time: O(n))
    public void deleteNode(int key) {
        Node temp = head, prev = null;

        // 如果頭節點就是要刪除的
        if (temp != null && temp.data == key) {
            head = temp.next;
            return;
        }

        // 搜尋要刪除的節點並記錄前一個節點
        while (temp != null && temp.data != key) {
            prev = temp;
            temp = temp.next;
        }

        // 沒找到
        if (temp == null) return;

        // 從鏈結中移除
        prev.next = temp.next;
    }

    // 4. 列印串列
    public void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        list.insertAtEnd(10);
        list.insertAtBeginning(5);
        list.insertAtEnd(20);
        list.display(); // 輸出: 5 -> 10 -> 20 -> null
    }
}