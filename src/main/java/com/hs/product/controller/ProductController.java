package com.hs.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hs.product.domain.Product;
import com.hs.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    /**
     * 获取分页的产品信息。
     *
     * @return 包含分页信息和产品数据的 PageInfo 对象
     */
    @GetMapping("/getPagedProducts")
    public PageInfo<Product> getPagedProducts(){
        PageInfo<Product> queryResult = productService.findPagedProducts(1, 5);
        return queryResult;
    }
    /**
     * 获取分页的产品列表。
     *
     * @return 分页后的产品列表
     */
    @GetMapping("/getPagedProductList")
    public List<Product> getPagedProductList(){
        List<Product> queryResult = productService.findProductsByPage(1, 5);
        return queryResult;
    }


    /**
     * 添加新产品
     *
     * @param product 新产品对象
     * @return ResponseEntity 返回添加成功或失败的信息
     */
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        boolean added = productService.addProduct(product);
        if (added) {
            return ResponseEntity.ok("Product added successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to add the product.");
        }
    }

    /**
     * 编辑产品信息
     *
     * @param product 更新后的产品对象
     * @return ResponseEntity 返回编辑成功或失败的信息
     */
    @PutMapping("/{productCode}")
    public ResponseEntity<String> editProduct(@PathVariable String productCode, @RequestBody Product product) {
        if (!productCode.equals(product.getProductCode())) {
            return ResponseEntity.badRequest().body("Product code in path does not match the product object.");
        }
        boolean edited = productService.editProduct(product);
        if (edited) {
            return ResponseEntity.ok("Product updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update the product.");
        }
    }

    /**
     * 删除产品
     *
     * @param productCode 产品代码
     * @return ResponseEntity 返回删除成功或失败的信息
     */
    @DeleteMapping("/{productCode}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productCode) {
        boolean deleted = productService.deleteProduct(productCode);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete the product.");
        }
    }

    /**
     * 查看产品详情
     *
     * @param productCode 产品代码
     * @return ResponseEntity 返回产品详情或错误信息
     */
    @GetMapping("/{productCode}")
    public ResponseEntity<Product> viewProductDetail(@PathVariable String productCode) {
        Product product = productService.viewProductDetail(productCode);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
