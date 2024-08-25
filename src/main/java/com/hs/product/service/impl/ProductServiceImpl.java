package com.hs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hs.product.domain.Product;
import com.hs.product.domain.ProductQuotation;
import com.hs.product.service.ProductQuotationService;
import com.hs.product.service.ProductService;
import com.hs.product.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author hjm
* @description 针对表【product(产品表)】的数据库操作Service实现
* @createDate 2024-08-19 23:21:31
*/
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{

    private ProductMapper productMapper;
    private ProductQuotationService productQuotationService;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductQuotationService productQuotationService) {
        this.productMapper = productMapper;
        this.productQuotationService = productQuotationService;
    }
    /**
     * 分页查询所有产品信息，并返回分页数据列表。
     *
     * @param pageNum  页码编号（通常从1开始）
     * @param pageSize 每页显示的产品数量
     * @return 包含分页产品信息的列表
     */
    @Override
    public List<Product> findProductsByPage(int pageNum, int pageSize) {
        // 使用 PageHelper 进行分页设置
        PageHelper.startPage(pageNum, pageSize);

        // 调用 Mapper 接口查询所有产品信息
        List<Product> lists = productMapper.queryProductInfo();

        return lists;
    }

    /**
     * 分页查询所有产品信息，并返回包含分页信息的对象。
     *
     * @param pageNum  页码编号（通常从1开始）
     * @param pageSize 每页显示的产品数量
     * @return 包含分页信息和产品数据的 PageInfo 对象
     */
    @Override
    public PageInfo<Product> findPagedProducts(int pageNum, int pageSize) {
        // 使用 PageHelper 进行分页设置
        PageHelper.startPage(pageNum, pageSize);

        // 调用 Mapper 接口查询所有产品信息
        List<Product> lists = productMapper.queryProductInfo();

        // 将查询结果转换为 PageInfo 对象，自动计算分页信息
        PageInfo<Product> pageInfo = new PageInfo<>(lists);

        return pageInfo;
    }

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
        queryWrapper.eq("product_code", product.getProductCode());

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
        queryWrapper.eq("product_code", productCode);
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
        return this.getOne(new QueryWrapper<Product>().eq("product_code", productCode), false);
    }
}




