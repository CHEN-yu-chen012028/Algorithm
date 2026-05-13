import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class MultiThresholdBFS {

    static BufferedImage original, grayscale, segmented;
    static JLabel origLabel = new JLabel(), grayLabel = new JLabel(), segLabel = new JLabel(), histLabel = new JLabel();
    static JSlider sliderT1 = new JSlider(0, 255, 175), sliderT2 = new JSlider(0, 255, 228);
    static JLabel  lblT1 = new JLabel("T1 = 175"), lblT2 = new JLabel("T2 = 228");

    public static void main(String[] args) throws Exception {
        // 加載影像 (請確保路徑正確)
        File f = new File("1.jpeg");
        if(!f.exists()) {
            System.out.println("找不到 1.jpeg");
            return;
        }
        original = ImageIO.read(f);
        grayscale = toGrayscale(original);
        
        JFrame frame = new JFrame("Extreme Fast BFS — Assignment 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(15, 15, 15));
        frame.setLayout(new BorderLayout(10, 10));

        JPanel ctrl = new JPanel(new GridLayout(2, 3, 12, 6));
        ctrl.setBackground(new Color(20, 20, 20));
        ctrl.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel hdr1 = makeLabel("LOW THRESHOLD T1", new Color(0, 180, 255));
        JLabel hdr2 = makeLabel("HIGH THRESHOLD T2", new Color(180, 240, 60));
        JLabel hdr3 = makeLabel("ALGORITHM: OPTIMIZED BFS", Color.WHITE);

        JPanel p1 = new JPanel(new BorderLayout(6,0));
        p1.setBackground(new Color(20,20,20));
        sliderT1.setBackground(new Color(20,20,20));
        lblT1.setForeground(new Color(0,180,255));
        lblT1.setFont(new Font("Courier New", Font.BOLD, 14));
        p1.add(sliderT1, BorderLayout.CENTER); p1.add(lblT1, BorderLayout.EAST);

        JPanel p2 = new JPanel(new BorderLayout(6,0));
        p2.setBackground(new Color(20,20,20));
        sliderT2.setBackground(new Color(20,20,20));
        lblT2.setForeground(new Color(180,240,60));
        lblT2.setFont(new Font("Courier New", Font.BOLD, 14));
        p2.add(sliderT2, BorderLayout.CENTER); p2.add(lblT2, BorderLayout.EAST);

        ctrl.add(hdr1); ctrl.add(hdr2); ctrl.add(hdr3);
        ctrl.add(p1);   ctrl.add(p2);   
        frame.add(ctrl, BorderLayout.NORTH);

        JPanel imgs = new JPanel(new GridLayout(1, 4, 8, 0));
        imgs.setBackground(new Color(10, 10, 10));
        imgs.add(panel("ORIGINAL",  origLabel));
        imgs.add(panel("GRAYSCALE", grayLabel));
        imgs.add(panel("SEGMENTED (BFS)", segLabel));
        imgs.add(panel("HISTOGRAM", histLabel));
        frame.add(imgs, BorderLayout.CENTER);

        ChangeListener cl = e -> {
            if (sliderT1.getValue() >= sliderT2.getValue()) {
                if (e.getSource() == sliderT1) sliderT1.setValue(sliderT2.getValue() - 1);
                else sliderT2.setValue(sliderT1.getValue() + 1);
            }
            lblT1.setText("T1 = " + sliderT1.getValue());
            lblT2.setText("T2 = " + sliderT2.getValue());
            refresh();
        };
        sliderT1.addChangeListener(cl);
        sliderT2.addChangeListener(cl);

        refresh();
        frame.setSize(1300, 620);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * 極限優化版 BFS
     * 1. 移除除法與取餘數運算
     * 2. 使用一維索引位移
     * 3. 減少記憶體存取
     */
    static BufferedImage segment(BufferedImage gray, int t1, int t2) {
        int w = gray.getWidth(), h = gray.getHeight();
        int n = w * h;
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        
        int[] src = gray.getRaster().getPixels(0, 0, w, h, (int[]) null);
        int[] result = new int[n];
        boolean[] visited = new boolean[n];
        int[] queue = new int[n]; 

        // 預計算位移：左, 右, 上, 下
        int[] offsets = {-1, 1, -w, w};

        long startTime = System.nanoTime();

        for (int i = 0; i < n; i++) {
            if (!visited[i] && src[i] >= t1 && src[i] <= t2) {
                int head = 0, tail = 0;
                queue[tail++] = i;
                visited[i] = true;

                while (head < tail) {
                    int curr = queue[head++];
                    result[curr] = 255;

                    // 僅在必要時計算一次 X 座標用於邊界檢查
                    int cx = curr % w;

                    for (int d = 0; d < 4; d++) {
                        int next = curr + offsets[d];

                        // 快速邊界判定
                        if (next >= 0 && next < n) {
                            // 防止左右溢出 (Wrap-around)
                            if (d == 0 && cx == 0) continue; 
                            if (d == 1 && cx == w - 1) continue;

                            if (!visited[next] && src[next] >= t1 && src[next] <= t2) {
                                visited[next] = true;
                                queue[tail++] = next;
                            }
                        }
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        double ms = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("\n--- Extreme BFS Performance Analysis ---");
        System.out.println("Execution Time  : " + String.format("%.3f", ms) + " ms");
        System.out.println("Process Rate    : " + String.format("%.2f", (n / ((endTime - startTime) / 1e9)) / 1e6) + " MP/s");

        out.getRaster().setPixels(0, 0, w, h, result);
        return out;
    }

    static BufferedImage toGrayscale(BufferedImage src) {
        int w = src.getWidth(), h = src.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = out.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return out;
    }

    static void refresh() {
        segmented = segment(grayscale, sliderT1.getValue(), sliderT2.getValue());
        origLabel.setIcon(scaled(original));
        grayLabel.setIcon(scaled(grayscale));
        segLabel .setIcon(scaled(segmented));
        histLabel.setIcon(new ImageIcon(buildHistogram(grayscale, sliderT1.getValue(), sliderT2.getValue())));
    }

    static BufferedImage buildHistogram(BufferedImage gray, int t1, int t2) {
        int[] hist = new int[256];
        int w = gray.getWidth(), h = gray.getHeight();
        int[] pixels = gray.getRaster().getPixels(0, 0, w, h, (int[]) null);
        for (int p : pixels) hist[p]++;
        int maxV = 1;
        for (int v : hist) if (v > maxV) maxV = v;
        BufferedImage img = new BufferedImage(512, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(new Color(18, 18, 18)); g2.fillRect(0, 0, 512, 200);
        for (int i = 0; i < 256; i++) {
            int barH = (int)((double) hist[i] / maxV * 180);
            g2.setColor(i < t1 ? new Color(0, 80, 130) : (i <= t2 ? new Color(80, 130, 20) : new Color(50, 50, 50)));
            g2.fillRect(i * 2, 200 - barH, 2, barH);
        }
        g2.dispose(); return img;
    }

    static ImageIcon scaled(BufferedImage img) {
        int maxW = 280, maxH = 340;
        double ratio = Math.min((double) maxW / img.getWidth(), (double) maxH / img.getHeight());
        return new ImageIcon(img.getScaledInstance((int)(img.getWidth()*ratio), (int)(img.getHeight()*ratio), Image.SCALE_SMOOTH));
    }

    static JPanel panel(String title, JLabel content) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setBackground(new Color(18, 18, 18));
        p.setBorder(BorderFactory.createLineBorder(new Color(35, 35, 35)));
        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setFont(new Font("Courier New", Font.BOLD, 10));
        lbl.setForeground(new Color(100, 100, 100));
        p.add(lbl, BorderLayout.NORTH); p.add(content, BorderLayout.CENTER);
        return p;
    }

    static JLabel makeLabel(String text, Color color) {
        JLabel l = new JLabel(text);
        l.setForeground(color);
        l.setFont(new Font("Courier New", Font.PLAIN, 11));
        return l;
    }
}