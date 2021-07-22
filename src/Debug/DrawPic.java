package Debug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DrawPic {

    public static void main(String[] args) throws IOException {

        int width = 80; // 图片宽
        int height = 80;// 图片高

        BufferedImage bi;

        for (int i = 1; i <= 30; i++) {
            String actStr = "防御";
            String drawStr = actStr + i;
            // 得到图片缓冲区
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// INT精确度达到一定,RGB三原色，高度70,宽度150
            // 得到它的绘制环境(这张图片的笔)
            Graphics2D g2 = (Graphics2D) bi.getGraphics();
            g2.setColor(Color.WHITE); // 设置背景颜色
            g2.fillRect(0, 0, width, height);// 填充整张图片(其实就是设置背景颜色)
            g2.setColor(Color.black);// 设置字体颜色
            // 设置字体,字号,大小
            Font titleFont = new Font("黑体", Font.BOLD, 20);
            g2.setFont(titleFont);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 抗锯齿
        /*
        // 计算文字长度,计算居中的X点坐标
        FontMetrics fm = g2.getFontMetrics(titleFont);
        int titleWidth = fm.stringWidth(markNameStr);
        int titleWidthX = (width - titleWidth) / 2 - 35;// 感觉不居中,向左移动35个单位
         */
            g2.drawString(drawStr, 3, 50);

            g2.dispose(); // 释放对象
            ImageIO.write(bi, "JPEG", new FileOutputStream(drawStr + ".jpg"));// 保存图片 JPEG表示保存格式
        }
    }
}