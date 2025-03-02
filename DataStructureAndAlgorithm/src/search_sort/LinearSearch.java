package search_sort;

// Define a class named LinearSearch
public class LinearSearch {

    // Define a generic method named searchMethod
    // It takes an array of type T (items) and a target element of type T (target)
    // Returns the index of the target if found, otherwise returns -1
    public static <T> int searchMethod(T[] items, T target) {
        int steps = 0;
        // Iterate through each element in the array
        // O(n)
        for (int i = 0; i < items.length; i++) {
            steps++;
            // Check if the current element equals the target
            if (items[i].equals(target)) {
                // If a match is found, return the current index
                System.out.println("Steps taken by linear : " + steps);
                return i;
            }
        }
        // If no match is found, return -1
        return -1;
    }

    // Main method, the entry point of the program
    public static void main(String[] args) {
        // Create a String array named students with some names
        //[] students = {"Felicia", "Vanilla", "Pedro", "Aunt", "Coco", "Cool"};
        Integer[] numbers = {
                1, 3, 5, 7, 9, 11, 13, 15, 17, 19,
                21, 23, 25, 27, 29, 31, 33, 35, 37, 39,
                41, 43, 45, 47, 49, 51, 53, 55, 57, 59,
                61, 63, 65, 67, 69, 71, 73, 75, 77, 79,
                81, 83, 85, 87, 89, 91, 93, 95, 97, 99
        };
        int result = searchMethod(numbers, 89);

        // Call the searchMethod with the students array and the target "Coco"
        // Print the result of the search
        if (result != -1) {
            System.out.println("Element found at Index : " + result);
        } else {
            System.out.println("Element not found");
        }
    }
}
