import java.util.Arrays;

public class ContrastiveLoss {

    /**
     * 計算 L2 距離: ||D(x1) - D(x2)||_2 (對應講義 P.4)
     * 
     */
    public static double calculateL2(double[] x1, double[] x2) {
        double sum = 0;
        for (int i = 0; i < x1.length; i++) {
            sum += Math.pow(x1[i] - x2[i], 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * 計算負樣本的損失 (Loss for negative samples)
     * 公式: L = max(0, C - ||D(x1) - D(x2)||_2) (對應講義 P.5)
     * [cite: 43, 44]
     */
    public static double calculateNegativeLoss(double distance, double C) {
        return Math.max(0, C - distance);
    }

    public static void main(String[] args) {
        // 模擬從圖片提取的特徵向量 D(x)
        double[] img1 = {0.8, 0.9}; // 柯基犬 (圖1)
        double[] img2 = {0.1, 0.2}; // 橘貓 (圖2)
        double[] img3 = {0.2, 0.1}; // 黑貓 (圖3)

        // 講義中的關鍵參數
        double C = 1.0;     // Margin C 
        double lr = 0.05;   // 學習率
        int epochs = 100;   // 疊代次數

        System.out.println("=== 初始狀態 (Contrastive Learning) ===");
        double d12_init = calculateL2(img1, img2);
        double d23_init = calculateL2(img2, img3);
        
        System.out.printf("負樣本 (犬vs貓) 初始距離: %.4f, Loss L: %.4f%n", d12_init, calculateNegativeLoss(d12_init, C));
        System.out.printf("正樣本 (貓vs貓) 初始距離: %.4f%n", d23_init);

        /* * 對比學習核心概念：學習特徵不是看單一樣本，而是透過「比較」
         * 目標：縮小正樣本距離，並確保負樣本距離大於 C (對應講義 P.3)
         * 
         */
        for (int i = 0; i < epochs; i++) {
            double d12 = calculateL2(img1, img2);
            double d23 = calculateL2(img2, img3);

            // 1. 正樣本 (Positive Samples: 2跟3) -> 拉近距離 (Minimize Distance)
            for (int j = 0; j < img2.length; j++) {
                double grad = (img2[j] - img3[j]); 
                img2[j] -= lr * grad;
                img3[j] += lr * grad;
            }

            // 2. 負樣本 (Negative Samples: 1跟2) -> 優化 Loss (Minimize L)
            // 只有當距離小於 C 時才需要更新 (Loss > 0) 
            if (d12 < C) {
                for (int j = 0; j < img1.length; j++) {
                    // 根據 L = C - ||D(x1)-D(x2)|| 的負梯度方向
                    // 為了減少 Loss，我們必須增加距離 (Push away)
                    double grad = (img1[j] - img2[j]) / (d12 + 1e-6); 
                    img1[j] += lr * grad; 
                    img2[j] -= lr * grad;
                }
            }
        }

        System.out.println("\n=== 優化後 (Optimize distance equation) ===");
        double d12_final = calculateL2(img1, img2);
        double d23_final = calculateL2(img2, img3);

        System.out.printf("負樣本 (犬vs貓) 更新後距離: %.4f (目標: 接近或大於 C = %.1f)%n", d12_final, C);
        System.out.printf("正樣本 (貓vs貓) 更新後距離: %.4f (目標: 接近 0)%n", d23_final);
        
        System.out.println("\n結論：模型已學會將不同類別(犬與貓)推開，並將相同類別(貓)拉近。");
    }
}
