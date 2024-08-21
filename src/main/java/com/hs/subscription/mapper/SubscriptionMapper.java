package com.hs.subscription.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface SubscriptionMapper {
    @Select("SELECT card_number FROM bankcard WHERE fund_account = #{fundAccount} AND trading_account = #{tradingAccount}")
    String findBankCardNumber(String fundAccount, String tradingAccount);

    @Select("SELECT balance FROM bankcard WHERE card_number = #{cardNumber}")
    double findBalance(String cardNumber);

    @Update("UPDATE bankcard SET balance = balance - #{amount} WHERE card_number = #{cardNumber}")
    void updateBalance(String cardNumber, double amount);

    @Update("UPDATE bankcard SET last_updated = #{date} WHERE card_number = #{cardNumber}")
    void updateBankcardLastUpdated(String cardNumber, String date);

    @Select("SELECT COUNT(*) FROM request WHERE date = #{date}")
    int findTodayRequestNumber(String date);

    @Insert("INSERT INTO transaction (id, card_number, date, description, amount, type) VALUES (#{id}, #{cardNumber}, #{date}, #{description}, #{amount}, #{type})")
    void insertTransaction(String id, String cardNumber, String date, String description, double amount, String type);

    @Insert("INSERT INTO request (id, product_code, card_number, type, amount, date, status) VALUES (#{id}, #{productCode}, #{cardNumber}, #{type}, #{amount}, #{date},#{status})")
    void insertRequest(String id, String productCode, String cardNumber, String type, double amount, String date, String status);

}
