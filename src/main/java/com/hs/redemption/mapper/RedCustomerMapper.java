package com.hs.redemption.mapper;

import com.hs.redemption.domain.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RedCustomerMapper {
    @Select("SELECT customer_type, id_number, id_type, name, risk_level, fund_account FROM customer " +
            "WHERE (fund_account LIKE CONCAT('%', #{fundAccount}, '%') OR #{fundAccount} IS NULL)" +
            "AND (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL)")
    List<Customer> selectCustomerByFundAccountAndName(String fundAccount, String name);

    @Select("SELECT name FROM customer WHERE fund_account = #{fundAccount}")
    String selectCustomerNameByFundAccount(String fundAccount);

    @Select("SELECT fund_account FROM bankcard WHERE card_number = #{cardNumber}")
    String selectFundAccountByCardNumber(String cardNumber);
}
