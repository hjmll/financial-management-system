package com.hs.account.domain;

import lombok.Data;

@Data
public class Account {
    private String customerType;
    private String idType;
    private String idNumber;
    private String name;
    private String gender;
    private String phone;
    private String riskLevel;
    private String fundAccount = "";
}
