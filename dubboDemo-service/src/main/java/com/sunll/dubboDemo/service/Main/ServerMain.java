package com.sunll.dubboDemo.service.Main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Administrator
 * on 2017/9/8
 */
public class ServerMain {
    public static void main(String[] args) throws IOException {

       ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "/spring/spring-context.xml");
        context.start();

        System.in.read(); // 按任意键退出
    }
}
