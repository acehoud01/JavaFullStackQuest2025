package stack_queue;

public class BuiltInStack {
    public static void main(String[] args) {
        Stack<Character> test = new Stack<>();
        System.out.println(test.isEmpty());
        test.push('A');
        System.out.println(test.isEmpty());
    }
}
