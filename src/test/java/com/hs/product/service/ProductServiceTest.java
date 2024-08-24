package com.hs.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hs.product.domain.Product;
import com.hs.product.mapper.ProductMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试 ProductServiceTest 的功能
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;


    /*@Before
    public void setUp() {
        // 创建一个 ProductServiceImpl 的实例
        // 这里假设 ProductServiceImpl 已经正确实现了 listProducts 方法
        productService = new ProductServiceImpl();
    }*/

   /* *//**
     * 测试分页查询产品列表的方法
     *//*
    @Test
    public void testListProducts() {
        // 准备测试数据
        int currentPage = 1;
        int pageSize = 3;
        Product product1 = new Product();
        product1.setProductCode("PRODUCT135");
        product1.setProductName("Product 1");
        product1.setProductNetValue(new BigDecimal(134.00));

        Product product2 = new Product();
        product2.setProductCode("PRODUCT136");
        product2.setProductName("Product 2");
        product2.setProductNetValue(new BigDecimal(125.00));

        List<Product> productList = Arrays.asList(product1, product2);

        // 创建分页对象
        Page<Product> expectedPage = new Page<>(currentPage, pageSize);
        expectedPage.setRecords(productList);

        // 模拟分页查询
        Page<Product> resultPage = productService.listProducts(currentPage, pageSize);

        // 验证结果
        assertEquals(expectedPage.getRecords(), resultPage.getRecords());
    }*/


    @Test
    public void testAddProduct() {
        // 创建一个产品实例
        Product product = new Product();
        product.setProductCode("PRODUCT124");
        product.setProductName("Test Fund");
        product.setProductNetValue(new BigDecimal("100.00"));
        // 设置一个指定的净值日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date netValueDate = sdf.parse("2024-08-20"); // 例如，设置为今天的日期
            product.setNetValueDate(netValueDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse date", e);
        }
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
        product.setProductNetValue(new BigDecimal("1000.00"));
        // 设置一个指定的净值日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date netValueDate = sdf.parse("2024-08-20"); // 例如，设置为今天的日期
            product.setNetValueDate(netValueDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse date", e);
        }
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
