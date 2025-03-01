package search_sort;

public class BubbleSort {
    public static <T extends Comparable<T>> void bubbleSorting(T[] arr) {
        int n = arr.length;
        int steps = 0;

        // Outer loop for number of passes
        for (int i = 0; i < n - 1; i++) {
            steps++;
           boolean swapped = false;
            // Inner loop for comparisons in each pass
            for (int j = 0; j < n - i - 1; j++) {
                // Compare adjacent elements using compareTo
                if (arr[j].compareTo(arr[j + 1]) > 0) { // arr[j] > arr[j + 1]
                    // Swap elements
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            } if (!swapped) {
                break;
            }
        }

    }

    public static <T> void printArray(T[] arr) {
        System.out.print("Sorted array: ");
        for (T item : arr) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    // Example usage
    public static void main(String[] args) {
        Integer[] numbers = {
                73, 5, 47, 99, 17, 31, 89, 23, 57, 3,
                67, 21, 39, 83, 11, 95, 45, 79, 1, 29,
                37, 53, 13, 9, 61, 25, 87, 33, 41, 49,
                93, 19, 75, 97, 43, 35, 55, 77, 27, 7,
                85, 15, 59, 81, 91, 63, 51, 69, 71, 65
        };
        bubbleSorting(numbers);
        printArray(numbers);
    }
}
