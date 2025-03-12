package week_2_mini_projects;

import week_1_mini_projects.StudentRecord;

import java.awt.desktop.ScreenSleepEvent;
import java.util.Scanner;
import java.util.Set;

public class NameFormatter {
    public static String formatName(String name) {
        name = name.trim();
        if (name.isEmpty()) return "No name!";

        String firstLetter = name.substring(0, 1).toUpperCase();
        String rest = name.substring(1).toLowerCase();

        return firstLetter + rest;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        String[] parts = fullName.split(" ");
        String formatted = "";
        for (String part : parts) {
            formatted += formatName(part) + " ";
        }


        System.out.println("Formatted: " + formatted.trim());
        scanner.close();
    }
}
