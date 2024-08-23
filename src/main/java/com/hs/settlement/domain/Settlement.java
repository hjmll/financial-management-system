package com.hs.settlement.domain;

import lombok.Data;

@Data
public class Settlement {
    private String date;
    private String productCode;
    private Double nav;
}
