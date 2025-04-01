package week_3_mini_projects;

import java.util.HashMap;
import java.util.Scanner;

public class RatingsApp {
    private final Scanner scanner;
    private double ratings;
    private HashMap<String, Double> companies;

    public RatingsApp() {
        this.scanner = new Scanner(System.in);
        this.companies = new HashMap<>();
        this.ratings = 0.0;
        setupCompanies();
    }

    private void setupCompanies() {
        HashMap<String, Double> comp = companies;
        comp.put("ShoeCity", 4.5);
        comp.put("MidBay", 4.0);
        comp.put("BoomHouse", 3.5);
    }


    // Method to display company names along with their ratings
    public void viewRatings() {
            System.out.println("Company Ratings:");
            int index = 0;
            for (String name : companies.keySet()) {
                // Display the company name and its corresponding rating
                System.out.println(++index + ". " + name + " - Rating: " + companies.get(name));
            }
    }

    public static void main(String[] args) {
        RatingsApp app = new RatingsApp();
        app.viewRatings();

    }
}
