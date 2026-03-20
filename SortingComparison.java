import java.util.Random;

public class SortingComparison {

    // ===== Insertion Sort =====
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ===== Bubble Sort =====
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }

    // ===== Quick Sort =====
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                // swap
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // ===== 複製陣列 =====
    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return copy;
    }

    // ===== 計算平均時間 =====
    public static double measureTime(int[] original, int runs, String type) {
        double total = 0;

        for (int i = 0; i < runs; i++) {
            int[] temp = copyArray(original);

            long start = System.nanoTime();

            if (type.equals("insertion")) {
                insertionSort(temp);
            } else if (type.equals("bubble")) {
                bubbleSort(temp);
            } else if (type.equals("quick")) {
                quickSort(temp, 0, temp.length - 1);
            }

            long end = System.nanoTime();
            total += (end - start);
        }

        return (total / runs) / 1e6; // 轉成 ms
    }

    // ===== 主程式 =====
    public static void main(String[] args) {
        int[] sizes = {1000, 2000, 5000, 10000};
        int runs = 10; // 跑10次取平均
        Random rand = new Random();

        for (int size : sizes) {
            int[] original = new int[size];

            // 產生隨機資料
            for (int i = 0; i < size; i++) {
                original[i] = rand.nextInt(10000);
            }

            System.out.println("Input size: " + size);

            double insertionTime = measureTime(original, runs, "insertion");
            double bubbleTime = measureTime(original, runs, "bubble");
            double quickTime = measureTime(original, runs, "quick");

            System.out.println("Insertion Sort: " + insertionTime + " ms");
            System.out.println("Bubble Sort: " + bubbleTime + " ms");
            System.out.println("Quick Sort: " + quickTime + " ms");

            System.out.println("---------------------------");
        }
    }
}