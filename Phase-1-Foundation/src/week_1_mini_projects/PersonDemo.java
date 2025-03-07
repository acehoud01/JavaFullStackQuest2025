package week_1_mini_projects;

/**
 * intro to OOPS
 */

public class PersonDemo {
    static class Person {
        String name;       // Reference type (points to object)
        int age;           // Primitive type (bits = value)
        boolean isLearning; // Primitive type (true/false)

        Person(String name, int age) {
            this.name = name;
            this.age = age;
            this.isLearning = true; // Default to true
        }

        void greet() {
            System.out.println("Hi, I’m " + name + ", " + age + " years old!");
        }

        void checkLearning() {
            if (isLearning) {
                System.out.println(name + " is learning Java!");
            } else {
                System.out.println(name + " is not learning right now.");
            }
        }
    }

    public static void main(String[] args) {
        Person p1 = new Person("Anele", 70);
        Person p2 = new Person("Alex", 25);

        p1.greet();
        p1.checkLearning();
        p2.greet();
        p2.checkLearning();
    }
}
