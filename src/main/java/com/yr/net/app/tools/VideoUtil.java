package com.yr.net.app.tools;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.Frame;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoUtil {

    public static void main(String[] args) throws Exception {

        // randomGrabberFFmpegImage("http://101.132.110.90/group1/M00/00/05/rBN4LFq8p5SAJT0wA5k4vpHKf7Q325.mp4", "D:\\test", "test2");
        randomGrabberFFmpegImage("D:\\test2.mp4", "D:\\test", "test2");
        //randomGrabberFFmpegImage("C:/Users\\Administrator\\Desktop\\VID_20171229_162251.mp4", "G:\\test", "111");
    }

    public static void randomGrabberFFmpegImage(String filePath, String targerFilePath, String targetFileName)
            throws Exception {
        //创建视频帧抓取工具
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();
        //获取旋转角度信息（90度）
        String rotate =ff.getVideoMetadata("rotate");
        //帧
        Frame f;
        //i可以控制获取第几帧
        int i = 0;
        while (i <5) {
            //一帧一帧去抓取视频图片，ff.grabImage();每次抓取下一帧
            f =ff.grabImage();
            IplImage src = null;
            //旋转图像并输出第29帧
            if(i==4){
                //手机录的视频有旋转角度，需要旋转处理
                if(null !=rotate &&rotate.length() > 1) {
                    OpenCVFrameConverter.ToIplImage converter =new OpenCVFrameConverter.ToIplImage();
                    src =converter.convert(f);
                    f =converter.convert(rotate(src, Integer.valueOf(rotate)));
                }
                //输出第几帧图片
                doExecuteFrame(f,targerFilePath,targetFileName+i+i);
            }
            i++;
        }
        ff.stop();
    }

    /*
     * 旋转角度的
     */
    public static IplImage rotate(IplImage src, int angle) {
        IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }

    public static void doExecuteFrame(Frame f, String targerFilePath, String targetFileName) {

        if (null ==f ||null ==f.image) {
            return;
        }
        Java2DFrameConverter converter =new Java2DFrameConverter();
        String imageMat ="jpg";
        String FileName =targerFilePath + File.separator +targetFileName +"." +imageMat;
        BufferedImage bi =converter.getBufferedImage(f);
        System.out.println("width:" + bi.getWidth());
        System.out.println("height:" + bi.getHeight());
        File output =new File(FileName);
        try {
            ImageIO.write(bi,imageMat,output);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
