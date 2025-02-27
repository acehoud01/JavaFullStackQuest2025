public class BinarySearch {
    public static <T extends Comparable<T>> int binarySearchMethod(T[] items, T target) {
        int left = 0;
        int right = items.length - 1;

        // Continue until left pointer exceeds right pointer
        while (left <= right) {
            int mid = (left + right) / 2; // Calculate middle index

            // Compare items[mid] with target
            int comparison = items[mid].compareTo(target);

            if (comparison == 0) { // items[mid] equals target
                return mid; // Return the index where target is found
            } else if (comparison < 0) { // items[mid] is less than target
                left = mid + 1; // Search right half
            } else { // items[mid] is greater than target
                right = mid - 1; // Search left half
            }
        }
        return -1; // Target not found
    }

    // Example usage
    public static void main(String[] args) {
        Integer[] numbers = {1, 3, 5, 7, 9}; // Must be sorted
        int result = binarySearchMethod(numbers, 5);
        System.out.println("Index of 5: " + result); // Output: 2

        result = binarySearchMethod(numbers, 4);
        System.out.println("Index of 4: " + result); // Output: -1
    }
}
