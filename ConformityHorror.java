import java.util.*;

public class ConformityHorror {

    static class Node {
        String id;
        double aura; // 靈韻值
        String description;

        Node(String id, double aura, String description) {
            this.id = id; this.aura = aura; this.description = description;
        }
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // 1. 初始化三條路徑 (保持不變)
        Node routeA = new Node("森林舊道", 0.95, "充滿雜草與摩擦力，但能聽見自己的腳步聲。");
        Node routeB = new Node("平滑大道", 0.01, "極致圓潤、無阻力，所有人都在這按讚。");
        Node routeC = new Node("數據小徑", 0.20, "演算法推薦的次佳選擇，看似多元其實平庸。");

        // 2. 建立鄰居列表 (保持不變)
        List<Node> neighbors = new ArrayList<>();
        for(int i=0; i<9; i++) neighbors.add(routeB); 
        for(int i=0; i<4; i++) neighbors.add(routeC); 
        for(int i=0; i<2; i++) neighbors.add(routeA); 

        Collections.shuffle(neighbors);

        System.out.println("--- 霧城模擬：雙獨立 K 值對抗 ---");
        System.out.println("面前有三條路，這座城市的人們正盲目地移動著...");
        
        // --- 修改處：讓 K 值分開 ---
        System.out.print("\n[策略 A] 請輸入你想觀察的人數 (K_A): ");
        int kA = reader.nextInt();
        if (kA > neighbors.size()) kA = neighbors.size();

        System.out.print("[策略 B] 請輸入你想觀察的人數 (K_B): ");
        int kB = reader.nextInt();
        if (kB > neighbors.size()) kB = neighbors.size();
        // -------------------------

        // 3. 執行策略 A (帶入 kA)
        System.out.println("\n[策略 A: 傳統 KNN (多數決陷阱)] - 觀察範圍: " + kA);
        String decisionA = naiveKNN(neighbors, kA);
        System.out.println(">>> 最終決定：" + decisionA);

        // 3. 執行策略 B (帶入 kB)
        System.out.println("\n[策略 B: 靈韻修正 KNN (生存決策)] - 觀察範圍: " + kB);
        String resultB = weightedKNN(neighbors, kB);
        System.out.println(">>> 最終決定：" + resultB);
        
        // 判斷生死 (以具備生存策略的 B 為準)
        if(resultB.equals("森林舊道")) {
            System.out.println("\n[結局]: 你穿過了迷霧，你逃出來了！");
        } else {
            System.out.println("\n[結局]: 你走入了自動化的循環，成為了監視器畫面中下一個失蹤的點。");
        }
        
        reader.close();
    }

    // 策略 A 
    public static String naiveKNN(List<Node> neighbors, int k) {
        Map<String, Integer> votes = new HashMap<>();
        for (int i = 0; i < k; i++) {
            String id = neighbors.get(i).id;
            votes.put(id, votes.getOrDefault(id, 0) + 1);
        }
        if (votes.isEmpty()) return "無人可參考";
        return Collections.max(votes.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    // 策略 B 
    public static String weightedKNN(List<Node> neighbors, int k) {
        Map<String, Double> scores = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Node n = neighbors.get(i);
            System.out.println("看見一人走向 [" + n.id + "] (靈韻: " + n.aura + ")");
            scores.put(n.id, scores.getOrDefault(n.id, 0.0) + n.aura);
        }
        if (scores.isEmpty()) return "無人可參考";
        return Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
