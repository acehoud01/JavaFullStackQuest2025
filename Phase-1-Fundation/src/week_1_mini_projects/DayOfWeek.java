package week_1_mini_projects;

import java.util.Scanner;

public class DayOfWeek {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int dayInNumeric = sc.nextInt(); // Primitive - bits represent the value directly

        // Basic switch for day name
        switch (dayInNumeric) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("Invalid day!");
        }

        // Enhanced switch for day type
        String dayType = switch (dayInNumeric) {
            case 1, 2, 3, 4, 5 -> "Weekday";
            case 6, 7 -> "Weekend";
            default -> "Invalid day";
        };
        System.out.println("Day type: " + dayType);

        // Add weekday check with if-else
        if (dayInNumeric >= 1 && dayInNumeric <= 5) {
            System.out.println("It’s a weekday!");
        } else if (dayInNumeric == 6 || dayInNumeric == 7) {
            System.out.println("It’s a weekend!");
        }
    }
}
