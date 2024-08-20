package com.hs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hs.product.domain.Product;
import com.hs.product.service.ProductService;
import com.hs.product.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author hjm
* @description 针对表【product(产品表)】的数据库操作Service实现
* @createDate 2024-08-19 23:21:31
*/
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{
    /**
     * 添加新产品
     *
     * @param product
     * @return
     */
    @Override
    public boolean addProduct(Product product) {
        // 检查产品代码是否为空
        if (product.getProductCode() == null || product.getProductCode().trim().isEmpty()) {
            return false;
        }
        return this.save(product);

    }

    /**
     * 编辑产品信息
     * @param product
     * @return
     */
    @Override
    public boolean editProduct(Product product) {
        // 检查产品代码是否为空
        if (product.getProductCode() == null || product.getProductCode().trim().isEmpty()) {
            return false;
        }
        // 创建一个 QueryWrapper 对象来指定更新条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productCode", product.getProductCode());

        // 使用 MyBatis Plus 的 update 方法更新产品信息
        return this.update(product, queryWrapper);

    }

    /**
     * 删除产品
     * @param productCode
     * @return
     */
    @Override
    public boolean deleteProduct(String productCode) {
        // 检查产品代码是否为空
        if (productCode == null || productCode.trim().isEmpty()) {
            return false;
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productCode", productCode);
        return this.remove(queryWrapper);
    }

    /**
     * 查看产品详情
     * @param productCode
     * @return
     */
    @Override
    public Product viewProductDetail(String productCode) {
        // 检查产品代码是否为空
        if (productCode == null || productCode.trim().isEmpty()) {
            return null;
        }
        return this.getOne(new QueryWrapper<Product>().eq("productCode", productCode), false);
    }
}




