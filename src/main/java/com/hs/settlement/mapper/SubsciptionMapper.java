package com.hs.settlement.mapper;

import com.hs.settlement.domain.Request;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubsciptionMapper {
    @Select("SELECT * FROM request WHERE date = #{date} AND status = '待确认'")
    @Results({
            @Result(property = "productCode", column = "product_code")
    })
    List<Request> getRequestsByDate(String date);

    @Select("SELECT product_net_value FROM product WHERE product_code = #{productCode}")
    double getProductNav(@Param("productCode") String productCode);

    @Update("UPDATE request SET shares = #{shares}, status = #{status} WHERE id = #{id}")
    int updateRequest(Request request);

}
