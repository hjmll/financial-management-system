package com.hs.product.service;

import com.hs.product.domain.ChartPoint;
import com.hs.product.domain.ProductQuotation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
* @author hjm
* @description 针对表【product_quotation(产品行情表)】的数据库操作Service
* @createDate 2024-08-21 13:26:39
*/
public interface ProductQuotationService extends IService<ProductQuotation> {
    /**
     * 添加新的产品行情记录
     *
     * @param productQuotation 产品行情记录
     * @return 操作是否成功
     */
    boolean addProductQuotation(ProductQuotation productQuotation);

    /**
     * 更新产品行情记录
     *
     * @param productQuotation 产品行情记录
     * @return 操作是否成功
     */
    boolean updateProductQuotation(ProductQuotation productQuotation);

    /**
     * 删除产品行情记录
     *
     * @param productCode 产品代码
     * @param tDate       交易日期
     * @return 操作是否成功
     */
    boolean deleteProductQuotation(String productCode, Date tDate);

    /**
     * 获取产品特定日期的行情记录
     *
     * @param productCode 产品代码
     * @param tDate       交易日期
     * @return 产品行情记录
     */
    ProductQuotation getProductQuotation(String productCode, Date tDate);
    /**
     * 获取产品行情数据并渲染成产品行情走势图。
     *
     * @param productCode 产品代码
     * @return 产品行情走势图的数据模型
     */
    List<ChartPoint> getProductQuotationChartPoints(String productCode);
}
