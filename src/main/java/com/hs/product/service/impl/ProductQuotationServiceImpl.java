package com.hs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hs.product.domain.ChartPoint;
import com.hs.product.domain.ProductQuotation;
import com.hs.product.mapper.ProductQuotationMapper;
import com.hs.product.service.ProductQuotationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author hjm
* @description 针对表【product_quotation(产品行情表)】的数据库操作Service实现
* @createDate 2024-08-21 13:26:39
*/
@Service
public class ProductQuotationServiceImpl extends ServiceImpl<ProductQuotationMapper, ProductQuotation>
    implements ProductQuotationService{

    /**
     * 添加新的产品行情记录
     *
     * @param productQuotation 产品行情记录
     * @return 操作是否成功
     */
    @Override
    public boolean addProductQuotation(ProductQuotation productQuotation) {
        // 检查产品代码是否为空
        if (productQuotation.getProductCode() == null || productQuotation.getProductCode().trim().isEmpty()) {
            return false;
        }
        // 设置 tDate 为当前日期，如果它尚未被设置
        if (productQuotation.getTDate() == null) {
            productQuotation.setTDate(new Date());
        }
        return this.save(productQuotation);
    }

    /**
     * 更新产品行情记录
     *
     * @param productQuotation 产品行情记录
     * @return 操作是否成功
     */
    @Override
    public boolean updateProductQuotation(ProductQuotation productQuotation) {
        // 检查产品代码是否为空
        if (productQuotation.getProductCode() == null || productQuotation.getProductCode().trim().isEmpty()) {
            return false;
        }
        // 创建一个 QueryWrapper 对象来指定更新条件
        QueryWrapper<ProductQuotation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productCode", productQuotation.getProductCode());

        // 使用 MyBatis Plus 的 update 方法更新产品信息
        return this.update(productQuotation, queryWrapper);
    }

    /**
     * 删除产品行情记录
     *
     * @param productCode 产品代码
     * @param tDate       交易日期
     * @return 操作是否成功
     */
    @Override
    public boolean deleteProductQuotation(String productCode, Date tDate) {
        // 检查产品代码是否为空
        if (productCode == null || productCode.trim().isEmpty()) {
            return false;
        }
        QueryWrapper<ProductQuotation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productCode", productCode);
        queryWrapper.eq("tDate", tDate);
        return this.remove(queryWrapper);
    }

    /**
     * 获取产品特定日期的行情记录
     *
     * @param productCode 产品代码
     * @param tDate       交易日期
     * @return 产品行情记录
     */
    @Override
    public ProductQuotation getProductQuotation(String productCode, Date tDate) {
        // 检查产品代码是否为空
        if (productCode == null || productCode.trim().isEmpty()) {
            return null;
        }
        return this.getOne(new QueryWrapper<ProductQuotation>()
                .eq("productCode", productCode)
                .eq("tDate", tDate));
    }

    /**
     * 根据产品代码获取该产品的历史行情数据点
     * @param productCode 产品代码
     * @return 图标数据点
     */
    @Override
    public List<ChartPoint> getProductQuotationChartPoints(String productCode) {
        // 创建 QueryWrapper 对象
        QueryWrapper<ProductQuotation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productCode", productCode);

        // 查询产品行情表中的记录
        List<ProductQuotation> productQuotations = baseMapper.selectList(queryWrapper);

        // 将查询结果转换为 ChartPoint 对象列表
        List<ChartPoint> chartPoints = new ArrayList<>();
        for (ProductQuotation productQuotation : productQuotations) {
            chartPoints.add(new ChartPoint(productQuotation.getTDate(), productQuotation.getProductNetValue()));
        }
        return chartPoints;
    }
}




