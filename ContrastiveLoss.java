import java.util.Arrays;

public class ContrastiveLoss {

    // 計算 L2 距離: ||D(x1) - D(x2)||2
    public static double calculateL2(double[] x1, double[] x2) {
        double sum = 0;
        for (int i = 0; i < x1.length; i++) {
            sum += Math.pow(x1[i] - x2[i], 2);
        }
        return Math.sqrt(sum);
    }

    // 計算負樣本 Loss L = max(0, C - distance)
    public static double calculateNegativeLoss(double distance, double C) {
        return Math.max(0, C - distance);
    }

    public static void main(String[] args) {
        // 模擬從圖片 1, 2, 3 提取的原始向量 (例如 2 維特徵)
        double[] img1 = {0.8, 0.9}; // 柴犬
        double[] img2 = {0.1, 0.2}; // 灰貓
        double[] img3 = {0.2, 0.1}; // 豹貓

        double C = 1.0; // 講義中的 Margin C
        double lr = 0.1; // 學習率 (更新步長)

        System.out.println("=== 初始狀態 ===");
        double d12_init = calculateL2(img1, img2);
        double d23_init = calculateL2(img2, img3);
        
        System.out.println("柴犬(1) vs 灰貓(2) 距離: " + d12_init + ", Loss L: " + calculateNegativeLoss(d12_init, C));
        System.out.println("灰貓(2) vs 豹貓(3) 距離: " + d23_init + ", Loss L: " + calculateNegativeLoss(d23_init, C));

        // 執行優化 (微分更新)
        // 目標：讓 2 和 3 更像 (拉近)，讓 1 和 2 保持距離 (推開)
        for (int i = 0; i < 50; i++) {
            double d23 = calculateL2(img2, img3);
            double d12 = calculateL2(img1, img2);

            // 1. 處理相似樣本 (2跟3)：拉近
            for (int j = 0; j < img2.length; j++) {
                double grad = (img2[j] - img3[j]); // 距離微分
                img2[j] -= lr * grad;
                img3[j] += lr * grad;
            }

            // 2. 處理負樣本 (1跟2)：根據講義公式推開 (若距離 < C)
            if (d12 < C) {
                for (int j = 0; j < img1.length; j++) {
                    // L = C - d 的微分方向為 -(x1-x2)/d
                    double grad = (img1[j] - img2[j]) / (d12 + 1e-6); 
                    img1[j] += lr * grad; // 加上梯度代表推開
                    img2[j] -= lr * grad;
                }
            }
        }

        System.out.println("\n=== 優化後 (L 的差別) ===");
        double d12_final = calculateL2(img1, img2);
        double d23_final = calculateL2(img2, img3);

        System.out.println("柴犬(1) vs 灰貓(2) 更新後距離: " + d12_final + " (距離變大，L 減少)");
        System.out.println("灰貓(2) vs 豹貓(3) 更新後距離: " + d23_final + " (距離縮小，相似度增加)");
        
        System.out.println("\n結論：透過微分更新，圖片 2 (灰貓) 與 3 (豹貓) 的特徵向量已成功靠攏。");
    }
}