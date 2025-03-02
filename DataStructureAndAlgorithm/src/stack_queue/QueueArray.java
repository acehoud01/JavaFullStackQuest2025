package stack_queue;

public class QueueArray<T> {
    private final T[] queue;
    private int front; // Index of the front element
    private int rear;  // Index where the next element will be added
    private int size;  // Current number of elements
    private final int capacity; // Maximum size of the queue

    // Constructor
    @SuppressWarnings("unchecked")
    public QueueArray(int capacity) {
        this.capacity = capacity;
        queue = (T[]) new Object[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    // Add an element to the queue (enqueue)
    public void enqueue(T value) {
        if (isFull()) {
            System.out.println("Queue is full! Cannot enqueue " + value);
            return;
        }
        rear = (rear + 1) % capacity; // Circular increment
        queue[rear] = value;
        size++;
    }

    // Remove an element from the queue (dequeue)
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty! Cannot dequeue");
            return null;
        }
        T value = queue[front];
        front = (front + 1) % capacity; // Circular increment
        size--;
        return value;
    }

    // Get the front element
    public T front() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }
        return queue[front];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() {return size == capacity; }
    public int size() { return size; }

    public static void main(String[] args) {
            QueueArray<Integer> q = new QueueArray<>(5);

            q.enqueue(10);
            q.enqueue(20);
            q.enqueue(30);
            System.out.println("Front element: " + q.front()); // 10
            System.out.println("Dequeued: " + q.dequeue());    // 10
            System.out.println("Front element: " + q.front()); // 20
            q.enqueue(40);
            q.enqueue(50);
            q.enqueue(60); // Queue is full!
            System.out.println("Queue size: " + q.size());    // 4
        }

}
