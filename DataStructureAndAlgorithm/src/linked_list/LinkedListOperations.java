package linked_list;

public abstract class LinkedListOperations {
    Node head; // Start of the list

    // Abstract methods
    public abstract void addToEnd(int data);
    public abstract void addToStart(int data);
    public abstract void insertAfter(int afterValue, int data);
    public abstract void removeFirst();
    public abstract void removeLast();
    public abstract void removeByValue(int value);
    public abstract int find(int value);
    public abstract int size();

    // print method
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}
