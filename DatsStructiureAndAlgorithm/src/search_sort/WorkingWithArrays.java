package search_sort;

// Define a class named WorkingWithArrays
public class WorkingWithArrays {

    // Main method, the entry point of the program
    public static void main(String[] args) {
        // Declare and initialize an integer array with some values
        int[] arr = {1, 5, 7, 5, 8};

        // Call the printArray method and pass the array to it
        printArray(arr);
    }

    // Define a static method named printArray that takes an integer array as a parameter
    public static void printArray(int[] args) {
        // Print the opening bracket of the array
        System.out.print("[");

        // Loop through each element in the array
        for (int i = 0; i < args.length; i++) {
            // Get the current element from the array
            int item = args[i];

            // Print the current element
            System.out.print(item);

            // If the current element is not the last one, print a comma and a space
            if (i < args.length - 1) {
                System.out.print(", ");
            }
        }

        // Print the closing bracket of the array and move to the next line
        System.out.println("]");
    }
}
