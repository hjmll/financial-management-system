package com.hs.subscription.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("select product_code FROM account WHERE fund_account = #{fundAccount}")
    List<String> selectProductsByFundAccount(String fundAccount);

    @Select("select product_status FROM product WHERE product_code = #{productCode}")
    String findProductStatus(String productCode);

    @Select("SELECT risk_level FROM product WHERE product_code = #{productCode}")
    String selectRiskLevelByProductCode(String productCode);
}
