package com.hs.settlement.mapper;

import com.hs.settlement.domain.Settlement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InitialMapper {

    @Update("UPDATE product SET net_value_date = #{date}, product_net_value = #{nav} WHERE product_code = #{productCode}")
    int initialNav(Settlement settlement);

    @Select("SELECT net_value_date, product_code, product_net_value FROM product WHERE net_value_date = #{date}")
    @Results({
            @Result(property = "productCode", column = "product_code"),
            @Result(property = "date", column = "net_value_date"),
            @Result(property = "nav", column = "product_net_value")

    })
    List<Settlement> getSettlementsByDate(String date);

    @Select("SELECT * FROM product_quotation WHERE net_value_date = (SELECT MAX(net_value_date) FROM product_quotation)")
    @Results({
            @Result(property = "productCode", column = "product_code"),
            @Result(property = "date", column = "net_value_date"),
            @Result(property = "nav", column = "product_net_value")
    })
    List<Settlement> getSettlements();
}
