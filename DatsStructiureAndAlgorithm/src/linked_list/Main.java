package linked_list;

public class Main {
    public static void main(String[] args) {
        SingleLinkedList nums = new SingleLinkedList();
        nums.addToStart(1);
        nums.addToStart(2);
        nums.addToStart(3);
        nums.printList();
        nums.insertAfter(1,16);
        nums.printList();
        nums.removeFirst();
        nums.printList();
        nums.removeLast();
        nums.printList();
        nums.addToStart(1);
        nums.addToStart(1);
        nums.removeByValue(1);
        nums.printList();
        nums.addToEnd(5);
        System.out.println(nums.find(5));
        System.out.println(nums.size());

    }
}
