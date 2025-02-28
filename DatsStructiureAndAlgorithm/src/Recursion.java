public class Recursion {
    public static void main(String[] args) {
        f1(10);
        System.out.println(factorial(5));
    }

    public static void f1(int i) {
        System.out.println(i);
        if( i > 0) f1(i - 1);
    }

    public static int factorial(int n) {
        if (n != 0) return n * factorial(n - 1);
        return 1;
    }

}
