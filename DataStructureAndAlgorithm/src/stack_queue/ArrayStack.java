package stack_queue;

public class ArrayStack<T> {
    private final T[] stack;   // Array to hold stack
    private int top;      // Index of top array (-1 means empty)
    private final int capacity; // Max size of array

    // Constructor: Set initial capacity
    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        this.capacity = capacity;     // Create generic array
        this.stack = (T[]) new Object[capacity]; // Empty stack starts at -1
    }

    // Push: Add to the top
    public void push(T data) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full!");
        }
        top++;             // Move to top
        stack[top] = data; // Add Item
    }

    // Pop: Remove and return the top item
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }
        T data = stack[top];
        stack[top] = null; // Get top item
        top--;             // Clear it
        return data;       // Move top down
    }

    // Peek: Look at the top item
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }
        return stack[top];
    }

    // isEmpty: Check if stack is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // isFull: Check if stack is full
    public boolean isFull() {
        return top == capacity - 1;
    }

    // Size: Number of items
    public int size() {
        return top + 1;
    }

    // Print the stack
    public void printStack() {
        System.out.print("Top: ");
        for (int i = top; i >= 0; i--) {
            System.out.print(stack[i] + " -> ");
        }
        System.out.println("Bottom");
    }
    public static void main(String[] args) {
        ArrayStack<Integer> intStack = new ArrayStack<>(2); // Start small
        intStack.push(5);
        intStack.push(2);
        intStack.push(9); // Triggers resize (2 -> 4)
        System.out.print("Integer Stack: ");
        intStack.printStack(); // Top: 9 -> 2 -> 5 -> Bottom
        System.out.println("Pop: " + intStack.pop()); // Pop: 9
        System.out.println("Size: " + intStack.size()); // Size: 2

        ArrayStack<String> stringStack = new ArrayStack<>(2);
        stringStack.push("Apple");
        stringStack.push("Banana");
        stringStack.push("Cherry"); // Triggers resize
        System.out.print("String Stack: ");
        stringStack.printStack(); // Top: Cherry -> Banana -> Apple -> Bottom
    }

}
