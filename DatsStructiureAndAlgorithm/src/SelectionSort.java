public class SelectionSort {
    public static void main(String[] args) {
        Integer[] numbers = {
                73, 5, 47, 99, 17, 31, 89, 23, 57, 3,
                67, 21, 39, 83, 11, 95, 45, 79, 1, 29,
                37, 53, 13, 9, 61, 25, 87, 33, 41, 49,
                93, 19, 75, 97, 43, 35, 55, 77, 27, 7,
                85, 15, 59, 81, 91, 63, 51, 69, 71, 65
        };
        selectionSort(numbers);
        printArray(numbers);
    }

    public static <T extends Comparable<T>> void selectionSort(T[] arr) {
        int n = arr.length;


        // Iterate through the array, placing the minimum element in position i
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i; // Store index of minimum element
            for (int j = i + 1; j < n; j++) {
                // Compare arr[j] with arr[minIndex], update minIndex if smaller
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            // Swap the found minimum   with the current position i
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }

    }

    public static <T> void printArray(T[] arr) {
        System.out.print("Sorted array: ");
        for (T item : arr) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}