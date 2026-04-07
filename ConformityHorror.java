import java.util.*;

public class ConformityHorror {

    static class Node {
        String id;
        double x, y;
        double aura; // 靈韻值 (0.0 ~ 1.0)
        
        Node(String id, double x, double y, double aura) {
            this.id = id; this.x = x; this.y = y; this.aura = aura;
        }
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // 1. 初始化路口
        Node routeA = new Node("森林舊道", 10, 10, 0.9); // 高靈韻：充滿不確定性與主體性
        Node routeB = new Node("平滑大道", 50, 50, 0.1); // 低靈韻：極致滑順的演算法陷阱

        // 2. 建立鄰居列表 (手動加入不同比例的鄰居)
        List<Node> neighbors = new ArrayList<>();
        
        // 模擬大多數人 (8位) 走向平滑大道
        for(int i=0; i<8; i++) neighbors.add(routeB); 
        
        // 模擬少數「倖存者」(3位) 走向森林舊道
        for(int i=0; i<3; i++) neighbors.add(routeA);

        // 打亂鄰居順序，模擬隨機遇到的情況
        Collections.shuffle(neighbors);

        System.out.println("--- 霧城模擬：KNN 靈韻實驗 ---");
        System.out.println("當前路口總人數：" + neighbors.size());
        System.out.println("其中 8 人趨向「平滑大道」，3 人趨向「森林舊道」。");
        
        // 3. 手動輸入 K 值
        System.out.print("\n請輸入你想觀察的鄰居數量 (K 值，建議輸入 1~11): ");
        int k = reader.nextInt();
        if (k > neighbors.size()) k = neighbors.size();

        // 4. 執行兩種策略
        System.out.println("\n[策略 A: 傳統 KNN (純粹從眾)]");
        String resultA = naiveKNN(neighbors, k);
        System.out.println(">>> 最終決定：" + resultA);

        System.out.println("\n---------------------------\n");

        System.out.println("[策略 B: 靈韻修正 KNN (為人之術)]");
        String resultB = survivalKNN(neighbors, k);
        System.out.println(">>> 最終決定：" + resultB);
        
        reader.close();
    }

    // 傳統 KNN：只看數量，多數決
    public static String naiveKNN(List<Node> neighbors, int k) {
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < k; i++) {
            String id = neighbors.get(i).id;
            counts.put(id, counts.getOrDefault(id, 0) + 1);
        }
        return getBest(counts);
    }

    // 生存策略 KNN：考慮靈韻權重
    public static String survivalKNN(List<Node> neighbors, int k) {
        Map<String, Double> weightedScores = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Node n = neighbors.get(i);
            // 權重公式：這不只是計算，而是對「人之本質」的加權
            double weight = n.aura; 
            weightedScores.put(n.id, weightedScores.getOrDefault(n.id, 0.0) + weight);
            
            System.out.println("觀察到一位鄰居選了 [" + n.id + "]，其靈韻權重為: " + weight);
        }
        return getBestWeighted(weightedScores);
    }

    // 輔助工具：找出最高分
    private static String getBest(Map<String, Integer> map) {
        return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static String getBestWeighted(Map<String, Double> map) {
        return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}