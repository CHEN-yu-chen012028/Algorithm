import java.util.Random;

public class TimeComplexityTest {

    // =========================
    // 1. Merge Sort O(n log n)
    // =========================
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] < arr[j])
                temp[k++] = arr[i++];
            else
                temp[k++] = arr[j++];
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        for (int t = 0; t < temp.length; t++) {
            arr[left + t] = temp[t];
        }
    }

    // =========================
    // 2. Insertion Sort O(n^2)
    // =========================
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

    // =========================
    // 3. Linear Scan O(n)
    // =========================
    public static int arraySum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    // =========================
    // 產生隨機陣列
    // =========================
    public static int[] generateArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1000);
        }
        return arr;
    }

    // =========================
    // 主程式（測時間）
    // =========================
    public static void main(String[] args) {

        int[] sizes = {1000, 2000, 4000, 8000};

        for (int n : sizes) {
            System.out.println("Input size: " + n);

            int[] arr1 = generateArray(n);
            int[] arr2 = arr1.clone();
            int[] arr3 = arr1.clone();

            // Merge Sort
            long start = System.nanoTime();
            mergeSort(arr1, 0, arr1.length - 1);
            long end = System.nanoTime();
            System.out.println("Merge Sort time: " + (end - start) / 1e6 + " ms");

            // Insertion Sort
            start = System.nanoTime();
            insertionSort(arr2);
            end = System.nanoTime();
            System.out.println("Insertion Sort time: " + (end - start) / 1e6 + " ms");

            // Linear Scan
            start = System.nanoTime();
            arraySum(arr3);
            end = System.nanoTime();
            System.out.println("Linear Scan time: " + (end - start) / 1e6 + " ms");

            System.out.println("------------------------");
        }
    }
}