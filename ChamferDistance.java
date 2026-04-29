import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ChamferDistance {
    private static final int INF = 9999;

    public static void main(String[] args) {
        String path = "C:\\Users\\user\\Pictures\\2.jpg";
        
        try {
            BufferedImage ori = ImageIO.read(new File(path));
            int w = ori.getWidth();
            int h = ori.getHeight();

            // 1. 初始化與二值化 (提高對比度)
            int[][] matrixX = new int[h][w];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    Color c = new Color(ori.getRGB(x, y));
                    // 使用加權灰階公式讓輪廓更清晰
                    int gray = (int)(c.getRed()*0.299 + c.getGreen()*0.587 + c.getBlue()*0.114);
                    // 門檻設為 120，讓建築輪廓更明顯
                    matrixX[y][x] = (gray < 120) ? 0 : INF;
                }
            }

            // 2. X 軸掃描 (1D Chamfer)
            for (int y = 0; y < h; y++) {
                for (int x = 1; x < w; x++) matrixX[y][x] = Math.min(matrixX[y][x], matrixX[y][x - 1] + 1);
                for (int x = w - 2; x >= 0; x--) matrixX[y][x] = Math.min(matrixX[y][x], matrixX[y][x + 1] + 1);
            }
            BufferedImage imgX = visualize(matrixX, w, h, 20); // 週期設為 20

            // 3. Y 軸掃描 (2D Chamfer)
            int[][] matrixY = new int[h][w];
            for(int i=0; i<h; i++) System.arraycopy(matrixX[i], 0, matrixY[i], 0, w);
            
            for (int x = 0; x < w; x++) {
                for (int y = 1; y < h; y++) matrixY[y][x] = Math.min(matrixY[y][x], matrixY[y - 1][x] + 1);
                for (int y = h - 2; y >= 0; y--) matrixY[y][x] = Math.min(matrixY[y][x], matrixY[y + 1][x] + 1);
            }
            BufferedImage imgY = visualize(matrixY, w, h, 20);

            showResult(ori, imgX, imgY);

        } catch (Exception e) {
            System.out.println("請確認圖片路徑是否正確：" + path);
        }
    }

    // 強化視覺化函數
    private static BufferedImage visualize(int[][] m, int w, int h, int cycle) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int d = m[y][x];
                if (d == 0) {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                } else if (d >= INF) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    // 使用 Modulo 產生清晰條紋
                    int brightness = (d % cycle) * (255 / cycle);
                    img.setRGB(x, y, new Color(brightness, brightness, brightness).getRGB());
                }
            }
        }
        return img;
    }

    private static void showResult(BufferedImage i1, BufferedImage i2, BufferedImage i3) {
        JFrame frame = new JFrame("Assignment 1 - Clear Visualization");
        frame.setLayout(new GridLayout(1, 3, 10, 10)); // 設定間距讓圖分開一點
        frame.add(new JLabel(new ImageIcon(i1)));
        frame.add(new JLabel(new ImageIcon(i2)));
        frame.add(new JLabel(new ImageIcon(i3)));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}