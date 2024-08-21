package com.hs.redemption.mapper;

import com.hs.redemption.domain.Holding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RedHoldingMapper{
    @Select("SELECT product_code, card_number, shares FROM holding WHERE card_number = #{cardNumber}" )
    public List<Holding> selectHoldingByCardNumber(String cardNumber);

    @Select("SELECT card_number FROM bankcard WHERE fund_account = #{fundAccount}")
    public List<String> selectCardNumberByFundAccount(String fundAccount);
}
