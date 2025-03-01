package linked_list;

public class SingleLinkedList extends LinkedListOperations{

    @Override
    public void addToEnd(int data) {
        Node newNode = new Node(data);
        Node current = head;
        if (head == null) {
            head = newNode;
            return;
        } else {
            current.next = newNode;
        }

        while (current != null) {
            current = current.next;
        }

    }

    @Override
    public void addToStart(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    @Override
    public void insertAfter(int afterValue, int data) {
        Node current = head;
        while (current != null && current.data != afterValue) {
            current = current.next;
        }
        if (current != null) {
            Node newNode = new Node(data);
            newNode.next = current.next;
            current.next = newNode;
        } else {
            System.out.println(afterValue + " not found.");
        }
    }

    @Override
    public void removeFirst() {
        if (head != null) {
            head = head.next;
        }
    }

    @Override
    public void removeLast() {
        if (head == null) return;
        if (head.next == null) {
            head = null;
            return;
        }
        Node current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        current.next = null;
    }

    @Override
    public void removeByValue(int value) {
        if (head == null) return;
        if (head.data == value) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null && current.next.data != value) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
        } else {
            System.out.println(value + " not found.");
        }
    }

    @Override
    public int find(int value) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }
}
