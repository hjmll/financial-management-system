package com.hs.subscription.mapper;

import com.hs.subscription.domain.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerMapper {
    @Select("SELECT customer_type, id_number, id_type, name, risk_level FROM customer WHERE fund_account = #{fundAccount}")
    Customer selectCustomerByFundAccount(String fundAccount);
}
