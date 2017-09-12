package com.jtd.ticketing.base.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by sunll on 2017/6/6.
 */
public class ImgReduceUtil {

    /**
     * 图片压缩并转成Base64字符串
     * @param imgUrl
     * @return
     */
    public static String ImgBase64(String imgUrl){
        return Base64.GetImageStr(ImgReduceUtil.reduceImg(imgUrl,1200,1000,null));
    }

    /**
     * 图片压缩并转成Base64字符串
     * @param imgFile
     * @return
     */
    public static String ImgBase64(File imgFile){
        return Base64.GetImageStr(ImgReduceUtil.reduceImg(imgFile,1200,1000,null));
    }

    /**
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
     * @param srcFile 图片文件
     * @param widthDist 压缩后图片宽度（当rate==null时，必传）
     * @param heightDist 压缩后图片高度（当rate==null时，必传）
     * @param rate 压缩比例
     */
    public static InputStream reduceImg(File srcFile, int widthDist, int heightDist, Float rate) {
        try {
            // 检查文件是否存在
            if (!srcFile.exists()) {
                return null;
            }
            // 如果rate不为空说明是按比例压缩
            if (rate != null && rate > 0) {
                // 获取文件高度和宽度
                int[] results = getImgWidth(srcFile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return null;
                } else {
                    widthDist = (int) (results[0] * rate);
                    heightDist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = javax.imageio.ImageIO.read(srcFile);
            BufferedImage tag = new BufferedImage((int) widthDist,
                    (int) heightDist, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(
                    src.getScaledInstance(widthDist, heightDist,
                            Image.SCALE_SMOOTH), 0, 0, null);
            tag.flush();
            //转成输入流
            InputStream is = null;
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut;
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(tag, "jpg",imOut);
            is = new ByteArrayInputStream(bs.toByteArray());
            return is;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
     * @param imgSrc 源图片地址
     * @param widthDist 压缩后图片宽度（当rate==null时，必传）
     * @param heightDist 压缩后图片高度（当rate==null时，必传）
     * @param rate 压缩比例
     */
    public static InputStream reduceImg(String imgSrc,int widthDist, int heightDist, Float rate) {
        try {
            File srcfile = new File(imgSrc);
            // 检查文件是否存在
            if (!srcfile.exists()) {
                return null;
            }
            // 如果rate不为空说明是按比例压缩
            if (rate != null && rate > 0) {
                // 获取文件高度和宽度
                int[] results = getImgWidth(srcfile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return null;
                } else {
                    widthDist = (int) (results[0] * rate);
                    heightDist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = javax.imageio.ImageIO.read(srcfile);
            BufferedImage tag = new BufferedImage((int) widthDist,
                    (int) heightDist, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(
                    src.getScaledInstance(widthDist, heightDist,
                            Image.SCALE_SMOOTH), 0, 0, null);
            tag.flush();
            //转成输入流
            InputStream is = null;
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut;
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(tag, "jpg",imOut);
            is = new ByteArrayInputStream(bs.toByteArray());
            return is;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 获取图片宽度
     *
     * @param file
     *            图片文件
     * @return 宽度
     */
    public static int[] getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图宽
            result[1] = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
