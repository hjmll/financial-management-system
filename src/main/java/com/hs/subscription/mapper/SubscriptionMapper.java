package com.hs.subscription.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Date;

@Mapper
public interface SubscriptionMapper {
    @Select("SELECT card_number FROM account WHERE fund_account = #{fundAccount} AND product_code = #{productCode}")
    String findBankCardNumber(String fundAccount, String productCode);

    @Select("SELECT balance FROM bankcard WHERE card_number = #{cardNumber}")
    double findBalance(String cardNumber);

    @Update("UPDATE bankcard SET balance = balance - #{amount} WHERE card_number = #{cardNumber}")
    void updateBalance(String cardNumber, double amount);

    @Update("UPDATE bankcard SET last_updated = #{date} WHERE card_number = #{cardNumber}")
    void updateBankcardLastUpdated(String cardNumber, Date date);

    @Select("SELECT COUNT(*) FROM transaction WHERE date = CURDATE()")
    int findTodayTransactionNumber();

    @Insert("INSERT INTO transaction (id, card_number, date, description, amount, type) VALUES (#{id}, #{cardNumber}, #{date}, #{description}, #{amount}, #{type})")
    void insertTransaction(String id, String cardNumber, Date date, String description, double amount, String type);

    @Insert("INSERT INTO request (id, product_code, card_number, type, amount, date, status) VALUES (#{id}, #{productCode}, #{cardNumber}, #{type}, #{amount}, #{date},#{status})")
    void insertRequest(String id, String productCode, String cardNumber, String type, double amount, Date date, String status);

}
