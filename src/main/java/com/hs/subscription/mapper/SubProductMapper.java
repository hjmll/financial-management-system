package com.hs.subscription.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubProductMapper {
    @Select("select product_code FROM product WHERE product_status = '激活'")
    List<String> selectProduct();

//    @Select("select product_status FROM product WHERE product_code = #{productCode}")
//    String findProductStatus(String productCode);

    @Select("SELECT risk_level FROM product WHERE product_code = #{productCode}")
    String selectRiskLevelByProductCode(String productCode);
}
