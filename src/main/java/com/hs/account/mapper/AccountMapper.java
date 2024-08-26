package com.hs.account.mapper;

import com.hs.account.domain.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Select("SELECT COUNT(*) FROM customer")
    int getAccountNumber();

    @Insert("INSERT INTO customer (customer_type, id_type, id_number, name, gender, phone, risk_level, fund_account) VALUES (#{customerType}, #{idType}, #{idNumber}, #{name}, #{gender}, #{phone}, #{riskLevel}, #{fundAccount})")
    int createAccount(Account account);

    @Update("UPDATE customer SET gender = #{gender}, phone = #{phone}, risk_level = #{riskLevel} " +
            "WHERE fund_account = #{fundAccount}")
    int updateAccount(Account account);

    @Select("SELECT * FROM customer")
    @Results({
            @Result(property = "fundAccount", column = "fund_account"),
            @Result(property = "riskLevel", column = "risk_level"),
            @Result(property = "idType", column = "id_type"),
            @Result(property = "customerType", column = "customer_type"),
            @Result(property = "idNumber", column = "id_number")
    })
    List<Account> getAllAccounts();
}
