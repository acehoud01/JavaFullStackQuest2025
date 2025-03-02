package stack_queue;

public class Stack<T> {
    private static class Node<T> {
        T data;        // value
        Node<T> next;  // Points to the node below

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }


    private Node<T> head; // Top of the stack
    private int size;     // Track the number of items

    //  Constructor: Start with an empty stack
    public Stack() {
        head = null;
        size = 0;
    }

    // Push: Add to top
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head; // point to old top
        head = newNode;      // New top
        size++;
    }

    // Pop: remove and return the top item
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }
        T data = head.data; // Get top item
        head = head.next;   // Move top down
        size--;
        return data;
    }

    // Peek: Look at the top item
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }
        return head.data;
    }

    // isEmpty: Check if stack has items
    public boolean isEmpty() {
        return head == null; // or size == 0
    }

    public int size() { return size; }

    // Print stack
    public void printStack() {
        Node<T> current = head;
        System.out.print("Top: ");

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("Bottom");
    }

    // Test
    public static void main(String[] args) {
        // Test with integers
        Stack<Integer> intStack = new Stack<>();
        intStack.push(99);
        intStack.push(5);
        intStack.push(77);

        System.out.print("Integer Stack: ");
        intStack.printStack(); // Top: 77 -> 5 -> 99 -> Bottom
        System.out.println("Peek: " + intStack.peek()); // Peek: 77
        System.out.println("Pop: " + intStack.pop());   // Pop: 77
        System.out.print("After pop: ");
        intStack.printStack(); // Top: 5 -> 99 -> Bottom
        System.out.println("Size: " + intStack.size()); // Size

        // Test with Strings
        Stack<String> stringStack = new Stack<>();
        stringStack.push("Apple");
        stringStack.push("Banana");
        stringStack.push("Cherry");
        System.out.print("String Stack: ");
        stringStack.printStack(); // Top: Cherry -> Banana -> Apple -> Bottom
        System.out.println("Pop: " + stringStack.pop()); // Pop: Cherry
    }

}
