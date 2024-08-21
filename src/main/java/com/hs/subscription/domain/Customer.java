package com.hs.subscription.domain;

import lombok.Data;

import java.util.List;

@Data
public class Customer {
    /**
     * 客户名称，用于记录用户的姓名或名称。
     */
    private String name;

    /**
     * 客户类型，用于区分不同类型的客户，如个人或企业。
     */
    private String customerType;

    /**
     * 证件类型，例如身份证、护照等。
     */
    private String idType;

    /**
     * 证件号码，用于验证客户的身份。
     */
    private String idNumber;

    /**
     * 客户风险等级，用于评估客户的金融风险承受能力。
     */
    private String riskLevel;

    /**
     * 客户可以申购的产品号列表。
     */
    private List<String> productCodeList;

    /**
     * 余额
     */
    private double balance;

}
