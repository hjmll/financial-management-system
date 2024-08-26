package com.hs.account.mapper;

import com.hs.account.domain.Account;
import com.hs.account.domain.Bankcard;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BankMapper {
    @Select("SELECT COUNT(*) FROM bankcard WHERE fund_account = #{account}")
    int getCardNumber(String account);

    @Insert("INSERT INTO bankcard (card_number, bank_name, balance, last_updated, fund_account, trading_account) VALUES (#{cardNumber}, #{bankName}, #{balance}, #{lastUpdated}, #{fundAccount}, #{tradingAccount})")
    int addCard(Bankcard bankcard);

    @Select("SELECT COUNT(*) FROM bankcard WHERE card_number = #{card_number}")
    int findCard(String card_number);

    @Update("UPDATE bankcard SET balance = balance + #{amount} " +
            "WHERE card_number = #{card_number}")
    void charge(double amount, String card_number);
}
