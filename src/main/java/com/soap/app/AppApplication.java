package com.soap.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//开启注解式事务管理，等同于<tx:annotation-driven />
@SpringBootApplication
public class AppApplication {

    @Bean
    public Object testBean(PlatformTransactionManager manager){
        System.out.println(">>>>>>>"+manager.getClass().getName());
        return new Object();
    }
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
