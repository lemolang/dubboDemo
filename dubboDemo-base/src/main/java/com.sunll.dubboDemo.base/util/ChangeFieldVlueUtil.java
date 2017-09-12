package com.jtd.ticketing.base.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by sunll on 2017/3/14.
 */
public class ChangeFieldVlueUtil {
    /**
     * 获取静态变量中中文转英文的方法
     * @param splitString 按规则切分的字符创
     * @param text
     * @return
     * @throws Exception
     */
    public  static String ChangeFieldValueEn(Class clazz,String splitString,String text) throws Exception{
        //获取所有变量的值
        Field[] fields = clazz.getFields();
        for( Field field : fields ){
            //   System.out.println(field.getName());
            //如果传过来的值和类中的静态变量值一致
            if (StringUtils.isNotBlank(text)){
                if (field.get(clazz).toString().equals(text)){
                    String fieldName = field.getName();
                    //按规则找到英文字段
                    fieldName = fieldName.split(splitString)[0];
                    Field field1 = clazz.getField(fieldName);
                    return field1.get(fieldName).toString();
                }
            }

        }
        return "";
    }

    /**
     * 获取静态变量中英文转中文的方法
     * @param splitString 按规则切分的字符创
     * @param text
     * @return
     * @throws Exception
     */
    public static String ChangeFieldValueCh(Class clazz,String splitString,String text) throws Exception{
        //获取所有变量的值
        Field[] fields = clazz.getFields();
        for( Field field : fields ){
            //如果传过来的值和类中的静态变量值一致
            if (StringUtils.isNotBlank(text)){
                if (field.get(clazz).toString().equals(text)){
                    String fieldName = field.getName();
                    //按规则找到英文字段
                    fieldName = fieldName+splitString;
                    Field field1 = clazz.getField(fieldName);
                    return field1.get(fieldName).toString();
                }
            }
        }
        return "";
    }
}
