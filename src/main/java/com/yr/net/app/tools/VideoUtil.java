package com.yr.net.app.tools;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacpp.avutil;
import  org.bytedeco.javacpp.helper.opencv_core.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@Slf4j
public class VideoUtil {

    public static void main(String[] args) throws Exception {

        randomGrabberFFmpegImage("/Users/dengbp/Movies/1568023588910627.mp4", "/Users/dengbp/Downloads", Math.random()+"");
    }


    /**
     * Description 截取视频封面
     * @param sourceFilePath 源视频绝对路径
 * @param targetFilePath 封面路径
 * @param targetFileName 封面文件名不含后缀
     * @return void
     * @Author dengbp
     * @Date 1:11 PM 3/22/21
     **/

    public static void randomGrabberFFmpegImage(String sourceFilePath, String targetFilePath, String targetFileName)
            throws Exception {
        //创建视频帧抓取工具
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(sourceFilePath);
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
                doExecuteFrame(f,targetFilePath,targetFileName+i+i);
            }
            i++;
        }
        ff.stop();
    }

    /*
     * 旋转角度的
     */
    private static IplImage rotate(IplImage src, int angle) {
        IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }

    private static void doExecuteFrame(Frame f, String targerFilePath, String targetFileName) {

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


    /**
     * 获取视频时长，单位为秒
     * @param file
     * @return 时长（s）
     */
    public static Long getVideoTime(File file){
        Long times = 0L;
        try {
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
            ff.start();
            times = ff.getLengthInTime()/(1000*1000);
            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

}
