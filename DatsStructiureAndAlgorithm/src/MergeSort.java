import java.lang.reflect.Array;

public class MergeSort {
    public static void main(String[] args) {
        // Example with Integer array
        Integer[] intArr = {5, 2, 9, 1};
        Integer[] sortedInt = mergeSort(intArr, Integer.class); // Sort the array
        System.out.print("Integers: ");
        printArray(sortedInt); // Output: Integers: 1 2 5 9

        // Example with String array
        String[] stringArr = {"banana", "apple", "cherry", "date"};
        String[] sortedStrings = mergeSort(stringArr, String.class); // Sort the array
        System.out.print("Strings: ");
        printArray(sortedStrings); // Output: Strings: apple banana cherry date
    }

    /**
     * Recursively sorts an array using the MergeSort algorithm.
     *
     * @param arr   The array to be sorted.
     * @param clazz The class type of the array elements (used for creating new arrays).
     * @param <T>   The type of elements in the array, which must implement Comparable.
     * @return The sorted array.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T[] mergeSort(T[] arr, Class<T> clazz) {
        // Base case: If the array has 1 or 0 elements, it's already sorted
        if (arr.length <= 1) {
            return arr;
        }

        // Find the middle index to divide the array into two halves
        int mid = arr.length / 2;

        // Create left and right arrays with the correct type
        T[] left = (T[]) Array.newInstance(clazz, mid); // Left half of the array
        T[] right = (T[]) Array.newInstance(clazz, arr.length - mid); // Right half of the array

        // Fill the left array with the first half of the original array
        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }

        // Fill the right array with the second half of the original array
        for (int i = mid; i < arr.length; i++) {
            right[i - mid] = arr[i];
        }

        // Recursively sort the left and right halves
        left = mergeSort(left, clazz);
        right = mergeSort(right, clazz);

        // Merge the sorted left and right halves
        return merge(left, right, clazz);
    }

    /**
     * Merges two sorted arrays into a single sorted array.
     *
     * @param left  The left sorted array.
     * @param right The right sorted array.
     * @param clazz The class type of the array elements (used for creating new arrays).
     * @param <T>   The type of elements in the array, which must implement Comparable.
     * @return The merged and sorted array.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T[] merge(T[] left, T[] right, Class<T> clazz) {
        // Create a new array to store the merged result
        T[] result = (T[]) Array.newInstance(clazz, left.length + right.length);

        // Initialize pointers for left, right, and result arrays
        int i = 0; // Pointer for left array
        int j = 0; // Pointer for right array
        int k = 0; // Pointer for result array

        // Merge the two arrays by comparing elements
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) <= 0) {
                // If the current element in the left array is smaller or equal, add it to the result
                result[k++] = left[i++];
            } else {
                // Otherwise, add the current element from the right array to the result
                result[k++] = right[j++];
            }
        }

        // If there are remaining elements in the left array, add them to the result
        while (i < left.length) {
            result[k++] = left[i++];
        }

        // If there are remaining elements in the right array, add them to the result
        while (j < right.length) {
            result[k++] = right[j++];
        }

        // Return the merged and sorted array
        return result;
    }

    /**
     * Prints the elements of an array.
     *
     * @param arr The array to be printed.
     * @param <T> The type of elements in the array.
     */
    public static <T> void printArray(T[] arr) {
        for (T item : arr) {
            System.out.print(item + " "); // Print each element
        }
        System.out.println(); // Move to the next line after printing the array
    }
}