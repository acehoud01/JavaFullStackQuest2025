package linked_list;
import java.util.LinkedList;

public class BuiltInLinkedList {
    public static void main(String[] args) {
        LinkedList<Integer> num = new LinkedList<>();
        num.add(5);
        num.add(9);
        num.addFirst(25);
        SingleLinkedList customList = new SingleLinkedList();

        for (Integer data : num) {
            customList.addToStart(data);
        }
        customList.addToStart(0);
        customList.printList();
    }
}
