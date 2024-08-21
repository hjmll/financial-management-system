package com.hs.subscription.mapper;

import com.hs.subscription.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubAccountMapper {
    @Select("SELECT fund_account, trading_account FROM bankcard WHERE trading_account LIKE CONCAT('%', #{account}, '%')")
    List<Account> selectAccount(String account);

//    @Select("SELECT trading_account FROM account WHERE fund_account = #{fundAccount} AND product_code = #{productCode}")
//    String findTradingAccount(String fundAccount, String productCode);
}
