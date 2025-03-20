package week_3_mini_projects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class StudentGrades {
    private String fullName;
    private String studentID;
    private ArrayList<Double> grades;
    private HashMap<String, Double> subjectGrades;

    public StudentGrades(String fullName, String studentID) {
        this.fullName = fullName;
        this.studentID = studentID;
        this.grades = new ArrayList<>();
        this.subjectGrades = new HashMap<>();
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public ArrayList<Double> getGrades() {
        return grades;
    }
    public void setGrades(ArrayList<Double> grades) {
        this.grades = grades;
    }
    public HashMap<String, Double> getSubjectGrades() {
        return subjectGrades;
    }
    public void setSubjectGrades(HashMap<String, Double> subjectGrades) {
        this.subjectGrades = subjectGrades;
    }

    // Add overall grade
    void addGrade(double grade) {
        grades.add(grade);
        System.out.println("Grade " + grade + " added to overall list!");
    }

    // Remove overall grade
    void removeGrade(double grade) {
        if (grades.remove(Double.valueOf(grade))) { // Convert to Double object
            System.out.println("Grade " + grade + " removed from overall list!");
        } else {
            System.out.println("Grade " + grade + " not found in overall list!");
        }
    }

    void addSubjectGrade(String subject, double grade){
        subjectGrades.put(subject, grade);
        System.out.println("Grade " + grade + " added for " + subject + "!");
    }

    // Get grade for a specific subject
    void getGrade(String subject) {
        if (subjectGrades.containsKey(subject)) {
            System.out.println(subject + ": " + subjectGrades.get(subject));
        } else {
            System.out.println("No grade found for " + subject + "!");
        }
    }

    // View all grades
    void viewGrades() {
        System.out.println("\nStudent: " + fullName + " (ID: " + studentID + ")");

        // Overall grades
        System.out.println("Overall Grades:");
        if (grades.isEmpty()) {
            System.out.println("No overall grades yet!");
        } else {
            for (int i = 0; i < grades.size(); i++) {
                System.out.println((i + 1) + ". " + grades.get(i));
            }
        }

        // Subject grades
        System.out.println("Subject Grades:");
        if (subjectGrades.isEmpty()) {
            System.out.println("No subject grades yet!");
        } else {
            for (String subject : subjectGrades.keySet()) {
                System.out.println(subject + ": " + subjectGrades.get(subject));
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentGrades student = new StudentGrades("Dollar Bill", "STU001");

        while (true) {
            System.out.println("\nStudent Grades Menu:");
            System.out.println("1. Add Overall Grade");
            System.out.println("2. Remove Overall Grade");
            System.out.println("3. Add Subject Grade");
            System.out.println("4. Get Subject Grade");
            System.out.println("5. View All Grades");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter grade: ");
                    double grade = scanner.nextDouble();
                    student.addGrade(grade);
                    break;
                case 2:
                    System.out.print("Enter grade to remove: ");
                    double removeGrade = scanner.nextDouble();
                    student.removeGrade(removeGrade);
                    break;
                case 3:
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter grade: ");
                    double subjGrade = scanner.nextDouble();
                    student.addSubjectGrade(subject, subjGrade);
                    break;
                case 4:
                    System.out.print("Enter subject: ");
                    String lookup = scanner.nextLine();
                    student.getGrade(lookup);
                    break;
                case 5:
                    student.viewGrades();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

}
