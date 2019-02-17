package com.whyble.fn.pay.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExchangeItem implements Serializable {


    private String title1;

    private String title2;

    private String ex_type;

    private String price1;

    private String price2;

    public ExchangeItem(String title1, String title2, String ex_type, String price1, String price2) {
        this.title1 = title1;
        this.title2 = title2;
        this.ex_type = ex_type;
        this.price1 = price1;
        this.price2 = price2;
    }
}
