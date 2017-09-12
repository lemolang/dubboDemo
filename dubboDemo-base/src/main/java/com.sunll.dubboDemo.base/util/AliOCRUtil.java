package com.jtd.ticketing.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunll on 2017/6/7.
 */
public class AliOCRUtil {

    /**
     * @param dataValue 图片压缩并转成Base64
     * @return 名片扫描后的信息
     */
    public static DataValue ocrBusinessCard(String dataValue) {
        //接口地址
        String host = "https://dm-57.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_business_card.json";
        String method = "POST";
        //接口appcode
        String appcode = "fab193b700cf461da2bc8d390c6a5f1f";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();

        String bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\"" + dataValue + "\"}}]}";

//      System.out.println(bodys);
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            // System.out.println(response.toString());
            //获取response的body
            String returnDate = EntityUtils.toString(response.getEntity());
            //解析返回的JSON字符串
            JSONObject returnDateJson = JSONObject.parseObject(returnDate);

            List<JSONObject> outputsList = (List<JSONObject>) returnDateJson.get("outputs");

            JSONObject outputs = outputsList.get(0);

            String outputValue = outputs.getString("outputValue");

            JSONObject returnOutputValue = (JSONObject) JSON.parse(outputValue);

            String returnDataValue = returnOutputValue.getString("dataValue");

            return JSON.parseObject(returnDataValue, DataValue.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("解析开始。。。。"+System.currentTimeMillis());
        String a = ImgReduceUtil.ImgBase64("C:\\Users\\Administrator\\Desktop\\1.jpg");
        DataValue dataValue = AliOCRUtil.ocrBusinessCard(a);
        System.out.println("解析结束。。。。"+System.currentTimeMillis());
    }
}
    class DataValue {

        private String[] addr;

        private String[] company;

        private String[] department;

        private String[] email;

        private String name;

        private String request_id;

        private String success;

        private String[] tel_cell;

        private String[] tel_work;

        private String[] title;

        public String[] getAddr() {
            return addr;
        }

        public void setAddr(String[] addr) {
            this.addr = addr;
        }

        public String[] getCompany() {
            return company;
        }

        public void setCompany(String[] company) {
            this.company = company;
        }

        public String[] getDepartment() {
            return department;
        }

        public void setDepartment(String[] department) {
            this.department = department;
        }

        public String[] getEmail() {
            return email;
        }

        public void setEmail(String[] email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String[] getTel_cell() {
            return tel_cell;
        }

        public void setTel_cell(String[] tel_cell) {
            this.tel_cell = tel_cell;
        }

        public String[] getTel_work() {
            return tel_work;
        }

        public void setTel_work(String[] tel_work) {
            this.tel_work = tel_work;
        }

        public String[] getTitle() {
            return title;
        }

        public void setTitle(String[] title) {
            this.title = title;
        }
    }
