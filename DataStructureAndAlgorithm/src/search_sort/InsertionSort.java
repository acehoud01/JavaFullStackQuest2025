package search_sort;

public class InsertionSort {
    public static <T extends Comparable<T>> void insertionSort(T[] arr) {
        // Check for null or empty array
        if (arr == null || arr.length <= 1) {
            return; // Nothing to sort
        }

        int n = arr.length;

        // Start from the second element
        for (int i = 1; i < n; i++) {
            T currentValue = arr[i]; // Element to insert
            int j = i - 1;           // Last index of sorted portion

            // Shift elements greater than currentValue to the right
            while (j >= 0 && arr[j].compareTo(currentValue) > 0) {
                arr[j + 1] = arr[j]; // Shift element right
                j--;                 // Move to the previous element
            }
            arr[j + 1] = currentValue; // Place currentValue in its correct spot
        }
    }

    // Helper method to print the array
    public static <T> void printArray(T[] arr) {
        if (arr == null) {
            System.out.println("Array is null");
            return;
        }
        System.out.print("Sorted array: ");
        for (T item : arr) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    // Test the implementation
    public static void main(String[] args) {
        Integer[] numbers = {
                73, 5, 47, 99, 17, 31, 89, 23, 57, 3,
                67, 21, 39, 83, 11, 95, 45, 79, 1, 29,
                37, 53, 13, 9, 61, 25, 87, 33, 41, 49,
                93, 19, 75, 97, 43, 35, 55, 77, 27, 7,
                85, 15, 59, 81, 91, 63, 51, 69, 71, 65
        };
        insertionSort(numbers);
        printArray(numbers);

        // Test edge cases
        Integer[] empty = {};
        insertionSort(empty);
        printArray(empty); // Output: Sorted array:

        Integer[] single = {1};
        insertionSort(single);
        printArray(single); // Output: Sorted array: 1
    }
}
