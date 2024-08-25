package com.hs.settlement.mapper;


import com.hs.settlement.domain.Request;
import com.hs.settlement.domain.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RedemptionMapper2 {

    @Select("SELECT COUNT(*) FROM transaction WHERE date = #{date}")
    int countTodayTransaction(String date);

    @Select("SELECT * FROM request WHERE date = #{date} AND status = '待确认' AND type = '赎回'")
    @Results({
            @Result(property = "productCode", column = "product_code"),
            @Result(property = "cardNumber", column = "card_number")
    })
    List<Request> getRedRequestsByDate(String date);

    @Update("UPDATE request SET amount = #{amount}, status = #{status} WHERE id = #{id}")
    int updateRequest(Request request);

    @Insert("INSERT INTO transaction (id, card_number, date, description, amount, type) VALUES (#{id}, #{cardNumber}, #{date}, #{description}, #{amount}, #{type})")
    void insertTransaction(Transaction transaction);

    @Select("SELECT * FROM transaction WHERE date = #{date}")
    @Results({
            @Result(property = "cardNumber", column = "card_number")
    })
    List<Transaction> getTodayTransaction(String date);
}
