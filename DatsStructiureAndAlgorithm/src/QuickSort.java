public class QuickSort {
    public static void main(String[] args) {
        String[] arr = {"Iphone", "Nokia", "Samsung", "Xiaomi", "Huawei", "Alcatel", "Motorola", "IPhone"};
        quickSort(arr, 0, arr.length - 1);
        printArray(arr); // Output: 1 2 3 5 6 7 9
    }

    // Main quicksort method: sorts arr from low to high
    public static <T extends Comparable<T>> void quickSort(T[] arr, int low, int high) {
        if (low < high) { // Only sort if there’s more than one element
            int pivotIndex = partition(arr, low, high); // Split and get pivot’s position
            quickSort(arr, low, pivotIndex - 1);       // Sort left side
            quickSort(arr, pivotIndex + 1, high);      // Sort right side
        }
    }

    // Partition: picks last element as pivot, puts it in right spot
    public static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
        T pivot = arr[high];    // Choose last element as pivot
        int i = low - 1;        // Index of smaller elements

        // Move all smaller elements to the left
        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) { // If current element <= pivot
                i++;                           // Move the boundary of smaller elements
                swap(arr, i, j);               // Swap to put it on the left side
            }
        }

        // Place pivot in its final position
        swap(arr, i + 1, high);
        return i + 1; // Return pivot’s final position
    }

    // Helper to swap two elements
    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Helper to print the array
    public static <T> void printArray(T[] arr) {
        for (T item : arr) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
