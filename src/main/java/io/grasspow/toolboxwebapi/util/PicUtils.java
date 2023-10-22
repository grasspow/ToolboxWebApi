package io.grasspow.toolboxwebapi.util;

import jakarta.annotation.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class PicUtils {
    private static final String BA_FONT_PATH = "./data/font/bafont.ttf";
    private static final String PNG_1_PATH = "./data/assets/halo.png";
    private static final String PNG_2_PATH = "./data/assets/cross.png";

    /**
     * generate BA style logo
     * code by ChatGPT,edited by grasspow
     * @param left
     * @param right
     * @param down
     * @return
     */
    public static Optional<String> genBALogo(String left, String right, @Nullable String down) {
//        int width = 900; // 图片宽度
        int width = 60 * (left.length() + right.length() + 4); // 图片宽度
        int height = 250; // 图片高度
        // 创建一个新的BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取Graphics2D对象，用于绘制图像
        Graphics2D g2d = image.createGraphics();
        // 让字体变平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置背景颜色为白色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        // 设置字体样式和大小
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(BA_FONT_PATH));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        font = font.deriveFont(Font.ITALIC, 80);
        AffineTransform transform = new AffineTransform();
        transform.shear(-0.2, 0);
        font = font.deriveFont(transform);
        Font font1 = font.deriveFont(Font.ITALIC);
        Font font2 = font.deriveFont(Font.ITALIC);
        // 设置文本颜色
        g2d.setColor(new Color(0x128afa));
        // 绘制str1
        int str1Width = g2d.getFontMetrics(font1).stringWidth(left);
        int strWidth = g2d.getFontMetrics(font1).stringWidth(left+right);
        int x1 = width/2 - strWidth/2 - 5; // 文本1左上角的x坐标
        int x2 = x1 + str1Width; // 文本2左上角的x坐标
        int x3 = x2 + 55;
        int y = 173; // 文本1右上角的y坐标
        g2d.setFont(font1);
        // 添加白色边框
        g2d.setStroke(new BasicStroke(10f));
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = font1.createGlyphVector(frc, left);
        Shape outline = gv.getOutline(x1, y);
        g2d.setColor(Color.WHITE);
        g2d.draw(outline);
        g2d.setColor(new Color(0x128afa));
        g2d.fill(outline);
        // 绘制缩小后的png1
        try {
            drawBASubPic(x3, height, g2d, PNG_1_PATH);
        } catch (Exception e) {
            return Optional.empty();
        }
        // 设置文本颜色
        g2d.setColor(Color.BLACK);
        // 绘制str2
        g2d.setFont(font2);
        // 添加白色边框
        g2d.setStroke(new BasicStroke(10f));
        gv = font2.createGlyphVector(frc, right);
        outline = gv.getOutline(x2, y);
        g2d.setColor(Color.WHITE);
        g2d.draw(outline);
        g2d.setColor(Color.BLACK);
        g2d.fill(outline);
        // 绘制缩小后的png2
        try {
            drawBASubPic(x3, height, g2d, PNG_2_PATH);
        } catch (Exception e) {
            return Optional.empty();
        }
        if (down != null) {
            // 设置文本颜色
            g2d.setColor(Color.BLACK);
            // 绘制str2
            x2 = x2 + 20; // 文本2左上角的x坐标
            int y2 = 205; // 文本2左上角的y坐标
            Font font3 = font.deriveFont(Font.ITALIC, 20);
            transform.shear(-0.4, 0);
            font3 = font3.deriveFont(transform);
            g2d.setFont(font3);
            // 添加白色边框
            g2d.setStroke(new BasicStroke(0));
            gv = font3.createGlyphVector(frc, down);
            outline = gv.getOutline(x2, y2);
            g2d.setColor(Color.WHITE);
            g2d.draw(outline);
            g2d.setColor(Color.BLACK);
            g2d.fill(outline);
        }
        // 释放资源
        g2d.dispose();
        // 将图像保存到文件
        try {
            File output = new File("./data/tmp/ba" + System.currentTimeMillis() + ".png");
            if (!output.exists()){
                output.createNewFile();
            }
            ImageIO.write(image, "jpg", output);
            return Optional.ofNullable(convertImageToBase64Str(output));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static void drawBASubPic(int width, int height, Graphics2D g2d, String png2Path) throws IOException {
        BufferedImage originalPng2 = ImageIO.read(new File(png2Path));
        int newPng2Width = originalPng2.getWidth() / 2;
        int newPng2Height = originalPng2.getHeight() / 2;
        Image scaledPng2 = originalPng2.getScaledInstance(newPng2Width, newPng2Height, Image.SCALE_SMOOTH);
        BufferedImage png2 = new BufferedImage(newPng2Width, newPng2Height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D png2G2d = png2.createGraphics();
        png2G2d.drawImage(scaledPng2, 0, 0, null);
        png2G2d.dispose();
        int png2X = width - newPng2Width/ 2; // png2中心的x坐标
        int png2Y = (height - newPng2Height) / 2; // png2中心的y坐标
        g2d.drawImage(png2, png2X, png2Y, null);
    }

    public static String convertImageToBase64Str(File file) {
        ByteArrayOutputStream baos = null;
        try {
            //获取图片类型
            String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            //构建文件
            //通过ImageIO把文件读取成BufferedImage对象
            BufferedImage bufferedImage = ImageIO.read(file);
            //构建字节数组输出流
            baos = new ByteArrayOutputStream();
            //写入流
            ImageIO.write(bufferedImage, suffix, baos);
            //通过字节数组流获取字节数组
            byte[] bytes = baos.toByteArray();
            //获取JDK8里的编码器Base64.Encoder转为base64字符
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception ignore) {
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
