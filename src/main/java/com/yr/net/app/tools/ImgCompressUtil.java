package com.yr.net.app.tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author dengbp
 * @ClassName ImgCompressUtil
 * @Description 图片压缩处理
 * @date 1/25/21 10:32 PM
 */
public class ImgCompressUtil {


    /**
     * 强制压缩/放大图片到固定的大小
     * @param fileName 文件路径
     * @param compressPercent 压缩比例(压缩为原来一半传0.5)
     * @return
     */
    public static BufferedImage resize(String fileName,double compressPercent){
        BufferedImage img = null;//原图
        BufferedImage compressImg = null;//压缩后图
        int width,height;
        try {
            File file = new File(fileName);
            img = ImageIO.read(file);
            width = (int)(img.getWidth()*compressPercent);
            height = (int)(img.getHeight()*compressPercent);
            compressImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
            compressImg.getGraphics().drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressImg;
    }

    /**
     *  保存缩略图
     * @param file 文件全路径
     * @param img 缩略图
     */
    public static void saveThumbnail(String file,BufferedImage img){
        try {
            File destFile = new File(file);
            FileOutputStream out = new FileOutputStream(destFile);
            ImageIO.write(img, "png", out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String fileName = "/Users/dengbp/Downloads/PAAS功能规划.jpg";


       /* BufferedImage bufferedImage = ImgCompressUtil.resize(fileName, 0.5);
        ImgCompressUtil.writeToFile(fileName+"_middle.png", bufferedImage);*/

        BufferedImage img = ImgCompressUtil.resize(fileName, 0.25);
        ImgCompressUtil.saveThumbnail(fileName+"_small.png", img);
    }
}
