package com.hs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"com.hs.product.mapper","com.hs.redemption.mapper","com.hs.settlement.mapper","com.hs.subscription.mapper"})
public class FinancialManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialManagementSystemApplication.class, args);
    }

}
