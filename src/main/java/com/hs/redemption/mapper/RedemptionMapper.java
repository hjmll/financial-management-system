package com.hs.redemption.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RedemptionMapper {

    @Insert("INSERT INTO request (id, product_code, card_number, type, shares, date, status) " +
            "VALUES (#{id}, #{productCode}, #{cardNumber}, #{type}, #{shares}, #{date},#{status})")
    void insertRequest(String id, String productCode, String cardNumber, String type, double shares, String date, String status);

    @Update("UPDATE holding SET shares = shares - #{shares} , update_date = #{date} WHERE card_number = #{cardNumber}" +
            "AND product_code = #{productCode}")
    void updateHolding(String cardNumber, double shares, String productCode,String date);

}
