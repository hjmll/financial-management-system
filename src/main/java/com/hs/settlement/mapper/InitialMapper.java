package com.hs.settlement.mapper;

import com.hs.settlement.domain.Settlement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InitialMapper {

    @Update("UPDATE product SET date = #{date}, nav = #{nav} WHERE product_code = #{productCode}")
    int initialNav(Settlement settlement);

    @Select("SELECT date, product_code, nav FROM product WHERE date = #{date}")
    @Results({
            @Result(property = "productCode", column = "product_code")

    })
    List<Settlement> getSettlementsByDate(String date);

    @Select("SELECT * FROM product_quotation WHERE date = (SELECT MAX(date) FROM product_quotation)")
    @Results({
            @Result(property = "productCode", column = "product_code")
    })
    List<Settlement> getSettlements();
}
