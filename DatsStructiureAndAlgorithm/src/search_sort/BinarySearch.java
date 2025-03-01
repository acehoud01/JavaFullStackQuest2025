package search_sort;

public class BinarySearch {
    public static <T extends Comparable<T>> int binarySearchMethod(T[] items, T target) {
        int left = 0;
        int right = items.length - 1;
        int steps = 0;

        // O(log n)
        // Continue until left pointer exceeds right pointer
        while (left <= right) {
            steps++;
            int mid = (left + right) / 2; // Calculate middle index

            // Compare items[mid] with target
            int comparison = items[mid].compareTo(target);

            if (comparison == 0) { // items[mid] equals target
                System.out.println("Steps taken by binary : " + steps);
                return mid; // Return the index where target is found
            } else if (comparison < 0) { // items[mid] is less than target
                left = mid + 1; // Search right half
            } else { // items[mid] is greater than target
                right = mid - 1; // Search left half
            }
        }
        System.out.println("Steps taken by binary : " + steps);
        return -1; // Target not found
    }

    // Example usage
    public static void main(String[] args) {
        // Must be sorted
        Integer[] numbers = {
                1, 3, 5, 7, 9, 11, 13, 15, 17, 19,
                21, 23, 25, 27, 29, 31, 33, 35, 37, 39,
                41, 43, 45, 47, 49, 51, 53, 55, 57, 59,
                61, 63, 65, 67, 69, 71, 73, 75, 77, 79,
                81, 83, 85, 87, 89, 91, 93, 95, 97, 99
        };
        // String[] names = {"Zebra", "Anele", "Boy", "Candies", "Donkey", "Monkey", "Zulu"};
        int result = binarySearchMethod(numbers, 89);
        System.out.println("Index of 5: " + result); // Output: 2

        //result = binarySearchMethod(numbers, 4);
        //System.out.println("Index of 4: " + result); // Output: -1
        //System.out.println(binarySearchMethod(names, "Donkey"));
    }
}
