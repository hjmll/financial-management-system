package com.hs.redemption.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RedProductMapper {
    @Select("select product_name FROM product WHERE product_code = #{productCode}")
    String selectProductNameByCode(String productCode);

}
