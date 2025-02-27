// Define a class named LinearSearch
public class LinearSearch {

    // Define a generic method named searchMethod
    // It takes an array of type T (items) and a target element of type T (target)
    // Returns the index of the target if found, otherwise returns -1
    public static <T> int searchMethod(T[] items, T target) {
        // Iterate through each element in the array
        for (int i = 0; i < items.length; i++) {
            // Check if the current element equals the target
            if (items[i].equals(target)) {
                // If a match is found, return the current index
                return i;
            }
        }
        // If no match is found, return -1
        return -1;
    }

    // Main method, the entry point of the program
    public static void main(String[] args) {
        // Create a String array named students with some names
        String[] students = {"Felicia", "Vanilla", "Pedro", "Aunt", "Coco", "Cool"};

        // Call the searchMethod with the students array and the target "Coco"
        // Print the result of the search
        System.out.println(searchMethod(students, "Coco"));
    }
}
