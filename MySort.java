import java.util.Arrays;
import java.util.Scanner;

public class MySort {

    public static void sort(int[] arr) {
        System.out.println("\n>>> 開始排序過程 <<<");
        System.out.println("原始數組: " + Arrays.toString(arr));
        System.out.println("--------------------------------------------------");

        for (int i = 1; i < arr.length; i++) {
            int target = arr[i];
            
            // 1. 利用二分查找找出 target 應該插入的位置
            int insertPos = findPosition(arr, target, 0, i - 1);
            
            System.out.printf("步驟 %d: 提取數值 [%d], 搜尋插入位置...\n", i, target);
            System.out.printf("   [二分查找結果]: 應插入索引 %d\n", insertPos);

            // 2. 將 insertPos 之後的元素向後位移
            for (int j = i; j > insertPos; j--) {
                arr[j] = arr[j - 1];
            }
            
            // 3. 放入目標值
            arr[insertPos] = target;
            
            System.out.println("   [當前數組狀態]: " + Arrays.toString(arr));
        }
    }

    private static int findPosition(int[] arr, int target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (target < arr[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入要排序的數字個數: ");
        int n = scanner.nextInt();
        
        int[] data = new int[n];
        System.out.println("請輸入 " + n + " 個數字（用空格隔開）:");
        for (int i = 0; i < n; i++) {
            data[i] = scanner.nextInt();
        }

        sort(data);

        System.out.println("--------------------------------------------------");
        System.out.println("最終排序完成: " + Arrays.toString(data));
        
        scanner.close(); // 關閉資源
    }
}