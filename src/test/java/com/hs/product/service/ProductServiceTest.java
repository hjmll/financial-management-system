package com.hs.product.service;

import com.hs.product.domain.Product;
import com.hs.product.mapper.ProductMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;
    @Test
    public void testAddProduct() {
        // 创建一个产品实例
        Product product = new Product();
        product.setProductCode("PRODUCT126");
        product.setProductName("Test Fund");
        product.setProductNetValue(new BigDecimal("100.00"));
        product.setNetValueDate(new Date());
        product.setRiskLevel("Low");
        product.setProductType("Equity");
        product.setDescription("A test fund for unit testing.");
        product.setProductStatus("A");
        // 执行添加产品操作
        boolean result = productService.addProduct(product);
        System.out.println(product.getProductCode());
        Assertions.assertTrue(result);

    }
    @Test
    public void testEditProduct() {
        // 创建一个产品实例
        Product product = new Product();
        product.setProductCode("PRODUCT123");
        product.setProductName("Updated Test Fund");
        product.setProductNetValue(new BigDecimal("105.00"));
        product.setNetValueDate(new Date());
        product.setRiskLevel("Medium");
        product.setProductType("Bond");
        product.setDescription("An updated test fund for unit testing.");
        product.setProductStatus("Inactive");

        // 执行编辑产品操作
        boolean result = productService.editProduct(product);

        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteProduct() {
        // 测试产品代码
        String productCode = "PRODUCT123";



        // 执行删除产品操作
        boolean result = productService.deleteProduct(productCode);

        // 验证结果
        System.out.println(result);
        Assertions.assertTrue(result);
    }

    @Test
    public void testViewProductDetail() {

        // 测试产品代码
        String productCode = "PRODUCT123";



        // 执行查看产品详情操作
        Product result = productService.viewProductDetail(productCode);
        System.out.println(result);

    }

}
