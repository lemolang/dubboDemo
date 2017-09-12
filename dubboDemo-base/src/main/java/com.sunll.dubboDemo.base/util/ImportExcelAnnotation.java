package com.jtd.ticketing.base.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sunll on 2017/3/9.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportExcelAnnotation {
    //插入字段的列数
    int fieldNum();
    //字段需要设置的值
    Class fieldValue();
}
