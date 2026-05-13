import java.util.Scanner;

public class HammingDistance {
    public static int calculate(String s1, String s2) {
        // 根據定義，漢明距離僅適用於等長的字串
        if (s1.length() != s2.length()) {
            return -1; // 用 -1 代表錯誤（長度不符）
        }

        int distance = 0;
        // 遍歷字串，比較相同位置的字元
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Hamming Distance 計算器 ===");
        
        // 獲取使用者輸入
        System.out.print("請輸入第一個字串 (例如 1011101): ");
        String str1 = scanner.nextLine();
        
        System.out.print("請輸入第二個字串 (例如 1001001): ");
        String str2 = scanner.nextLine();

        // 開始計算並紀錄時間（符合講義提到 Printing time complexity 的精神）
        long startTime = System.nanoTime();
        int result = calculate(str1, str2);
        long endTime = System.nanoTime();

        // 輸出結果
        if (result == -1) {
            System.out.println("錯誤：兩個字串的長度必須相同！");
        } else {
            System.out.println("-------------------------------");
            System.out.println("字串 1: " + str1);
            System.out.println("字串 2: " + str2);
            System.out.println("漢明距離 (Hamming Distance) 為: " + result);
            System.out.println("運算耗時: " + (endTime - startTime) + " 奈秒 (ns)");
            System.out.println("時間複雜度: O(n), 其中 n 為字串長度");
        }

        scanner.close();
    }
}