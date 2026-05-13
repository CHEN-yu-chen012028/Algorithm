import java.util.Scanner;

public class IntegralImage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. 讓使用者定義矩陣大小
        System.out.println("=== Integral Image 計算器 ===");
        System.out.print("請輸入矩陣的列數 (Rows): ");
        int rows = scanner.nextInt();
        System.out.print("請輸入矩陣的欄數 (Cols): ");
        int cols = scanner.nextInt();

        int[][] image = new int[rows][cols];

        // 2. 讓使用者輸入矩陣內容
        System.out.println("請輸入矩陣的數值 (由左至右，由上至下):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("位置 [%d][%d] 的值: ", i, j);
                image[i][j] = scanner.nextInt();
            }
        }

        // 3. 計算積分影像 (Integral Image)
        long startTime = System.nanoTime();
        int[][] integral = computeIntegral(image, rows, cols);
        long endTime = System.nanoTime();

        // 4. 顯示結果
        System.out.println("\n--- 原始矩陣 ---");
        printMatrix(image);

        System.out.println("\n--- 積分影像結果 ---");
        printMatrix(integral);

        System.out.println("-------------------------------");
        System.out.println("運算耗時: " + (endTime - startTime) + " 奈秒 (ns)");
        System.out.println("時間複雜度: O(W * H), 其中 W 為寬, H 為高");

        scanner.close();
    }

    /**
     * 計算積分影像核心邏輯
     */
    public static int[][] computeIntegral(int[][] image, int rows, int cols) {
        int[][] integral = new int[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                // 公式: I(x,y) = i(x,y) + I(x-1, y) + I(x, y-1) - I(x-1, y-1)
                int current = image[y][x];
                int left = (x > 0) ? integral[y][x - 1] : 0;
                int above = (y > 0) ? integral[y - 1][x] : 0;
                int diag = (x > 0 && y > 0) ? integral[y - 1][x - 1] : 0;

                integral[y][x] = current + left + above - diag;
            }
        }
        return integral;
    }

    /**
     * 輔助方法：印出矩陣
     */
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%4d ", val);
            }
            System.out.println();
        }
    }
}