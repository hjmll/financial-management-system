package com.hs.settlement.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RedemptionMapper {

    @Select("SELECT COUNT(*) FROM transaction WHERE date = #{date}")
    int countTodayTransaction(String date);
}
