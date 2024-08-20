package com.hs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

@MapperScan("com.hs.product.mapper")
public class FinancialManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialManagementSystemApplication.class, args);
    }

}
