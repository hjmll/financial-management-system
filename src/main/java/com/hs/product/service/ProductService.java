package com.hs.product.service;

import com.hs.product.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author hjm
* @description 针对表【product(产品表)】的数据库操作Service
* @createDate 2024-08-19 23:21:31
*/
public interface ProductService extends IService<Product> {

    // 添加新产品
    boolean addProduct(Product product);

    // 编辑产品信息
    boolean editProduct(Product product);

    // 删除产品
    boolean deleteProduct(String productCode);

    // 查看产品详情
    Product viewProductDetail(String productCode);

}
