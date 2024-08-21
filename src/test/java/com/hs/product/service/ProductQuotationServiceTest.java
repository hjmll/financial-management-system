package com.hs.product.service;


import com.hs.product.domain.ChartPoint;
import com.hs.product.domain.ProductQuotation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 测试 ProductQuotationService 的功能
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductQuotationServiceTest {

    @Autowired
    private ProductQuotationService productQuotationService;

    private ProductQuotation productQuotation;

    @Before
    public void setUp() {
        productQuotation = new ProductQuotation();
        productQuotation.setProductCode("PRODUCT130");
        productQuotation.setProductNetValue(new BigDecimal("300.00"));
        productQuotation.setTDate(new Date());
    }

    @Test
    public void testAddProductQuotation() {
        // 测试
        boolean result = productQuotationService.addProductQuotation(productQuotation);

        // 验证
        assertTrue(result);
    }

    @Test
    public void testUpdateProductQuotation() {
        // 测试
        boolean result = productQuotationService.updateProductQuotation(productQuotation);

        // 验证
        assertTrue(result);
    }

    @Test
    public void testDeleteProductQuotation() {
        // 准备测试数据
        String productCode = "PRODUCT130";
        Date tDate = new Date();

        // 测试
        boolean result = productQuotationService.deleteProductQuotation(productCode, tDate);

        // 验证
        assertTrue(result);
    }

    @Test
    public void testGetProductQuotation() {
        // 准备测试数据
        String productCode = "PRODUCT128";
        Date tDate = new Date();

        // 测试
        ProductQuotation result = productQuotationService.getProductQuotation(productCode, tDate);

        // 验证
        assertNotNull(result);
        assertEquals(productCode, result.getProductCode());
        assertEquals(tDate, result.getTDate());

    }

    @Test
    public void testGetProductQuotationChartPoints() {
        // 准备测试数据
        String productCode = "PRODUCT127";

        // 调用方法
        List<ChartPoint> chartPoints = productQuotationService.getProductQuotationChartPoints(productCode);

        // 验证结果
        assertEquals(3, chartPoints.size()); // 假设数据库中有3条记录
        chartPoints.forEach(chartPoint -> {
            System.out.println(chartPoint);
        });
        /*assertEquals(new Date(2024 - 1900, 7, 21), chartPoints.get(0).getDate());
        assertEquals(100.00, chartPoints.get(0).getValue());
        assertEquals(new Date(2024 - 1900, 7, 24), chartPoints.get(1).getDate());
        assertEquals(105.00, chartPoints.get(1).getValue());*/
    }
}