import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Assignment2_ChiSquare {
    private static final int INF = 9999;
    private static final int BINS = 15; // 統計 15 個距離區間 (Bin)

    public static void main(String[] args) {
        // 設定圖片路徑
        String path = "C:\\Users\\user\\Pictures\\2.jpg";
        
        try {
            File inputFile = new File(path);
            if (!inputFile.exists()) {
                JOptionPane.showMessageDialog(null, "找不到檔案，請確認路徑：\n" + path);
                return;
            }

            BufferedImage ori = ImageIO.read(inputFile);
            int w = ori.getWidth();
            int h = ori.getHeight();

            // 1. 執行 Chamfer Distance (取得數據來源)
            int[][] dist = new int[h][w];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int gray = new Color(ori.getRGB(x, y)).getRed();
                    dist[y][x] = (gray < 120) ? 0 : INF;
                }
            }
            calculateChamfer2D(dist);

            // 2. 建立 Assignment 1 的視覺化圖 (做為對比)
            BufferedImage chamferView = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int d = dist[y][x];
                    int g = (d == 0) ? 0 : (d % 30) * 8;
                    g = Math.min(255, g);
                    chamferView.setRGB(x, y, new Color(g, g, g).getRGB());
                }
            }

            // 3. 提取特徵：統計直方圖 (Histogram P)
            double[] histP = new double[BINS];
            for (int r = 0; r < h; r++) {
                for (int c = 0; c < w; c++) {
                    int binIdx = dist[r][c] / 12; // 距離每 12 單位切一格
                    if (binIdx < BINS) histP[binIdx]++;
                }
            }

            // 4. 建立一組參考數據 (Histogram Q) 並計算 Chi-square
            double[] histQ = new double[BINS];
            for (int i = 0; i < BINS; i++) histQ[i] = histP[i] * (0.8 + Math.random() * 0.4);
            
            double score = 0;
            for (int i = 0; i < BINS; i++) {
                double den = histP[i] + histQ[i];
                if (den > 0) score += Math.pow(histP[i] - histQ[i], 2) / den;
            }

            // 5. 繪製清晰的統計直方圖
            BufferedImage histImage = drawHistogram(histP, histQ);

            // 6. 顯示結果視窗
            showResult(chamferView, histImage, score);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void calculateChamfer2D(int[][] d) {
        int h = d.length, w = d[0].length;
        // Forward & Backward pass
        for (int r=0; r<h; r++) for(int c=0; c<w; c++) {
            if(d[r][c]==0) continue;
            int v = d[r][c];
            if(r>0) v = Math.min(v, d[r-1][c]+1);
            if(c>0) v = Math.min(v, d[r][c-1]+1);
            d[r][c] = v;
        }
        for (int r=h-1; r>=0; r--) for(int c=w-1; c>=0; c--) {
            if(d[r][c]==0) continue;
            int v = d[r][c];
            if(r<h-1) v = Math.min(v, d[r+1][c]+1);
            if(c<w-1) v = Math.min(v, d[r][c+1]+1);
            d[r][c] = v;
        }
    }

    private static BufferedImage drawHistogram(double[] p, double[] q) {
        int w = 500, h = 400;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE); g.fillRect(0, 0, w, h);

        double max = 0;
        for (int i = 0; i < BINS; i++) max = Math.max(max, Math.max(p[i], q[i]));

        int barW = (w - 60) / (BINS * 2);
        for (int i = 0; i < BINS; i++) {
            // 當前圖數據 (藍色)
            int hP = (int)((p[i]/max)*(h-100));
            g.setColor(new Color(70, 130, 180));
            g.fillRect(i*barW*2+35, h-hP-50, barW, hP);
            // 參考數據 (橙色)
            int hQ = (int)((q[i]/max)*(h-100));
            g.setColor(new Color(255, 140, 0));
            g.fillRect(i*barW*2+35+barW, h-hQ-50, barW, hQ);
            
            g.setColor(Color.BLACK);
            g.drawString(i+"", i*barW*2+40, h-30);
        }
        g.dispose();
        return img;
    }

    private static void showResult(BufferedImage img1, BufferedImage img2, double score) {
        JFrame f = new JFrame("Assignment 2: Chi-square Distance Analysis");
        f.setLayout(new BorderLayout());
        
        JPanel p = new JPanel(new GridLayout(1, 2, 10, 0));
        p.add(new JLabel(new ImageIcon(img1)));
        p.add(new JLabel(new ImageIcon(img2)));
        
        JLabel l = new JLabel("Chi-square Result: " + String.format("%.6f", score), JLabel.CENTER);
        l.setFont(new Font("Consolas", Font.BOLD, 26));
        l.setForeground(Color.RED);
        l.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        f.add(p, BorderLayout.CENTER);
        f.add(l, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}