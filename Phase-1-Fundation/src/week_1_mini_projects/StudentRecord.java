package week_1_mini_projects;

import java.util.Scanner;

public class StudentRecord {
    static class Student {
        private String name;
        private int grade;

        Student(String name, int grade) {
            this.name = name;
            setGrade(grade);
        }

        public String getName() { return name; }
        public int getGrade() { return grade; }

        public void setName(String name) { this.name = name; }

        public void setGrade(int grade) {
           if (grade >= 0 && grade <= 100) {
               this.grade = grade;
           } else {
               System.out.println("Invalid grade! Setting to 0");
               this.grade = 0;
           }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student grade (0 - 100): ");
        int grade = scanner.nextInt();

        Student student = new Student(name, grade);
        System.out.println("Student: " + student.getName() + ", Grade: " + student.getGrade());

        // Update grade
        System.out.print("Enter new grade: ");
        int newGrade = scanner.nextInt();
        student.setGrade(newGrade);
        System.out.println("Updated grade: " + student.getGrade());

        scanner.close();
    }
}
