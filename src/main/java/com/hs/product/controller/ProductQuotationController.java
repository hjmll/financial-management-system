package com.hs.product.controller;


import com.hs.product.domain.ChartPoint;
import com.hs.product.domain.ProductQuotation;
import com.hs.product.service.ProductQuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product_quotations")
public class ProductQuotationController {

    private final ProductQuotationService productQuotationService;

    @Autowired
    public ProductQuotationController(ProductQuotationService productQuotationService) {
        this.productQuotationService = productQuotationService;
    }

    // 添加新的产品行情记录
    @PostMapping
    public ResponseEntity<String> addProductQuotation(@RequestBody ProductQuotation productQuotation) {
        boolean success = productQuotationService.addProductQuotation(productQuotation);
        if (success) {
            return ResponseEntity.ok("Product quotation added successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to add product quotation.");
        }
    }

    // 更新产品行情记录
    @PutMapping("/{productCode}")
    public ResponseEntity<String> updateProductQuotation(@RequestBody ProductQuotation productQuotation) {
        boolean success = productQuotationService.updateProductQuotation(productQuotation);
        if (success) {
            return ResponseEntity.ok("Product quotation updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update product quotation.");
        }
    }

    // 删除产品行情记录
    @DeleteMapping("/{productCode}/{tDate}")
    public ResponseEntity<String> deleteProductQuotation(@PathVariable String productCode, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date tDate) {
        boolean success = productQuotationService.deleteProductQuotation(productCode, tDate);
        if (success) {
            return ResponseEntity.ok("Product quotation deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete product quotation.");
        }
    }

    // 获取产品特定日期的行情记录
    @GetMapping("/{productCode}/{tDate}")
    public ResponseEntity<Map<String, Object>> getProductQuotation(@PathVariable String productCode, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date tDate) {
        ProductQuotation productQuotation = productQuotationService.getProductQuotation(productCode, tDate);
        if (productQuotation != null) {
            return ResponseEntity.ok(Map.of("productQuotation", productQuotation));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 获取产品行情数据并渲染成产品行情走势图
    @GetMapping("/{productCode}/chart")
    public ResponseEntity<Map<String, Object>> getProductQuotationChartPoints(@PathVariable String productCode) {
        List<ChartPoint> chartPoints = productQuotationService.getProductQuotationChartPoints(productCode);
        if (!chartPoints.isEmpty()) {
            return ResponseEntity.ok(Map.of("chartPoints", chartPoints));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}