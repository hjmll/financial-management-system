package com.hs.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hs.product.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author hjm
* @description 针对表【product(产品表)】的数据库操作Service
* @createDate 2024-08-19 23:21:31
*/

public interface ProductService extends IService<Product> {
    //分页查询所有产品信息，并返回分页数据列表
    List<Product> findProductsByPage(int pageNum, int pageSize);

    //分页查询所有产品信息，并返回包含分页信息的对象
    PageInfo<Product> findPagedProducts(int pageNum, int pageSize);

   /* //分页展示产品列表
    Page<Product> listProducts(int page, int pageSize);*/

    // 添加新产品
    boolean addProduct(Product product);

    // 编辑产品信息
    boolean editProduct(Product product);

    // 删除产品
    boolean deleteProduct(String productCode);

    // 查看产品详情
    Product viewProductDetail(String productCode);

}
