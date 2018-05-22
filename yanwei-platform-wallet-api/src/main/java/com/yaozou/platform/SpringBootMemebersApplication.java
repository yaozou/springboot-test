package com.yanwei.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//java -Xmx512m -jar /opt/apps/platform-member/platform-member.jar
//http://blog.csdn.net/javahighness/article/details/52515226

@ServletComponentScan
@SpringBootApplication
public class SpringBootMemebersApplication  {

    public static void main(String[] args) {
        try {

            SpringApplication.run(SpringBootMemebersApplication.class, args);
            
            System.out.println("***************************************");
            System.out.println("***************************************");
            System.out.println("*******Platform  Memeber 启动成功**********");
            System.out.println("***************************************");
            System.out.println("***************************************");

        } catch (Exception ex) {

            System.out.println("***************************************");
            System.out.println(ex.getMessage());
            System.out.println("***************************************");
            System.out.println("***************************************");
            System.out.println("******Platform  Memeber 启动失败**********");
            System.out.println("***************************************");
            System.out.println("***************************************");

        }
    }

}
