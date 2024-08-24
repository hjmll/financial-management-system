package com.hs.settlement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HaltMapper {

    @Select("SELECT count(*) FROM request WHERE date = #{date} AND type = '申购' AND status = '待确认'")
    int countSub(String date);

    @Select("SELECT count(*) FROM request WHERE date = #{date} AND type = '赎回' AND status = '待确认'")
    int countRed(String date);

}
