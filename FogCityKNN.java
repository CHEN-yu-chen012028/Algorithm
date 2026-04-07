import java.util.*;

public class FogCityKNN {

    static class Node {
        String id;
        double aura; // 靈韻值 (0.0 ~ 1.0)
        boolean isAutomated; // 是否為演算法產生的自動化路徑

        Node(String id, double aura, boolean isAutomated) {
            this.id = id; this.aura = aura; this.isAutomated = isAutomated;
        }
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // 1. 初始化路口
        Node routeA = new Node("森林舊道", 0.95, false); // 高靈韻，非自動化
        Node routeB = new Node("平滑大道", 0.05, true);  // 低靈韻，高度自動化

        // 2. 挑戰場景：當鄰居都是錯的 (8人選B，只有1人選A)
        List<Node> neighbors = new ArrayList<>();
        for(int i=0; i<8; i++) neighbors.add(routeB); 
        neighbors.add(routeA); 
        
        Collections.shuffle(neighbors); // 洗牌，代表你隨機遇到這些鄰居

        System.out.println("--- 霧城模擬：挑戰多數決陷阱 ---");
        System.out.println("當前情況：大多數鄰居 (8/9) 都走向了「平滑大道」。");
        
        System.out.print("\n請輸入觀察範圍 K 值 (輸入 9 以觀察所有人): ");
        int k = reader.nextInt();
        if (k > neighbors.size()) k = neighbors.size();

        // 3. 執行三種不同層次的邏輯
        System.out.println("\n[層次一：傳統從眾 (Naive KNN)]");
        System.out.println(">>> 決定：" + naiveKNN(neighbors, k));

        System.out.println("\n[層次二：加權靈韻 (Weighted KNN)]");
        System.out.println(">>> 決定：" + weightedKNN(neighbors, k));

        System.out.println("\n[層次三：主體覺醒 (Subjective Decision)]");
        System.out.println(">>> 決定：" + subjectiveDecision(neighbors, k));
        
        reader.close();
    }

    // 1. 傳統 KNN：多數決 (在 8 vs 1 的情況下絕對會錯)
    public static String naiveKNN(List<Node> neighbors, int k) {
        Map<String, Integer> votes = new HashMap<>();
        for (int i = 0; i < k; i++) {
            String id = neighbors.get(i).id;
            votes.put(id, votes.putIfAbsent(id, 0) == null ? 1 : votes.get(id) + 1);
        }
        return getWinner(votes);
    }

    // 2. 加權 KNN：考慮靈韻 (利用加權對抗數量)
    public static String weightedKNN(List<Node> neighbors, int k) {
        Map<String, Double> scores = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Node n = neighbors.get(i);
            scores.put(n.id, scores.getOrDefault(n.id, 0.0) + n.aura);
        }
        return getWinnerDouble(scores);
    }

    // 3. 主體覺醒：黃老師的「為人之術」
    // 當偵測到「群體自動化」現象時，主動捨棄多數人的數據
    public static String subjectiveDecision(List<Node> neighbors, int k) {
        System.out.println("正在進行深度偵測...");
        for (int i = 0; i < k; i++) {
            Node n = neighbors.get(i);
            if (!n.isAutomated && n.aura > 0.8) {
                System.out.println("! 偵測到具有主體性靈光的少數選擇：" + n.id);
                return n.id + " (捨棄多數，聽從內心與少數靈光)";
            }
        }
        return "無法做決定，因為所有鄰居都已喪失主體性。";
    }

    private static String getWinner(Map<String, Integer> map) {
        return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static String getWinnerDouble(Map<String, Double> map) {
        return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}