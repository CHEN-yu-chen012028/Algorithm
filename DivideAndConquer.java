// DivideAndConquer.java
import java.util.Arrays;

public class DivideAndConquer {

    // --- 1. Merge Sort (合併排序) ---
    // Recurrence: T(n) = 2T(n/2) + n
    public void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Divide: 遞迴拆解左半部與右半部
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            // Conquer: 合併兩個已排序的子陣列
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        int[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i] <= rightArr[j]) arr[k++] = leftArr[i++];
            else arr[k++] = rightArr[j++];
        }
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];
    }

    // --- 2. Binary Search (二元搜尋) ---
    // Recurrence: T(n) = T(n/2) + 1
    public int binarySearch(int[] arr, int left, int right, int target) {
        if (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) return mid; // 找到目標

            // Divide: 根據大小關係決定搜尋左半或右半
            if (arr[mid] > target)
                return binarySearch(arr, left, mid - 1, target);
            else
                return binarySearch(arr, mid + 1, right, target);
        }
        return -1; // 未找到
    }

    public static void main(String[] args) {
        DivideAndConquer demo = new DivideAndConquer();
        
        // 測試數據
        int[] data = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("原始陣列: " + Arrays.toString(data));

        // 執行 Merge Sort
        demo.mergeSort(data, 0, data.length - 1);
        System.out.println("排序後陣列 (Merge Sort): " + Arrays.toString(data));

        // 執行 Binary Search
        int target = 9;
        int result = demo.binarySearch(data, 0, data.length - 1, target);
        if (result != -1)
            System.out.println("二元搜尋: 元素 " + target + " 位在索引 " + result);
        else
            System.out.println("二元搜尋: 找不到元素 " + target);
    }
}