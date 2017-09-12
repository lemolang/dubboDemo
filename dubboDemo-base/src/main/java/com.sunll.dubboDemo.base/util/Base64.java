package com.jtd.ticketing.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.List;

/**
 * Created by sunll on 2017/6/5.
 */
public class Base64 {

    /**
     * 图片转化成base64字符串
     *
     * @param inputStream 文件输入流
     */
    public static String GetImageStr(InputStream inputStream) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        //读取图片字节数组
        try {
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * 图片转化成base64字符串
     *
     * @param fileUrl 文件路径
     */
    public static String GetImageStr(String fileUrl) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = fileUrl;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * base64字符串转化成图片
     *
     * @param imgStr      base64字符串
     * @param imgFilePath 生成图片的路径
     */
    public static boolean GenerateImage(String imgStr, String imgFilePath) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String data = "{\"outputs\": [{\n" +
                "            \"outputLabel\": \"ocr_business_card\",\n" +
                "            \"outputMulti\": {},\n" +
                "            \"outputValue\": {\n" +
                "                \"dataType\": 50,\n" +
                "                \"dataValue\": \"{\\\"addr\\\":[\\\"北京市朝阳区红车营东路8号鸿懋商务大厦4楼2405室\\\"],\\\"company\\\":[\\\"北京微赢互动科技有限公司\\\"],\\\"department\\\":[],\\\"email\\\":[],\\\"name\\\":\\\"扦轭\\\",\\\"request_id\\\":\\\"20170606154917_95e0f905198faa6d2b128d68ce9a176a\\\",\\\"success\\\":true,\\\"tel_cell\\\":[],\\\"tel_work\\\":[\\\"1158101628\\\"],\\\"title\\\":[\\\"商务部高级渠道经理\\\"]}\"}}]}\n" +
                "\n";
        JSONObject json = JSONObject.parseObject(data);
        List<JSONObject> outputsList = (List<JSONObject>) json.get("outputs");

        JSONObject outputs = outputsList.get(0);

        String o = outputs.getString("outputValue");

        JSONObject data1 = (JSONObject) JSON.parse(o);

        String dataValue = data1.getString("dataValue");

        DataValue outputs1 = JSON.parseObject(dataValue,DataValue.class);

        System.out.println(dataValue);

    }

}
