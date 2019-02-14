package com.whyble.fn.pay.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExchangeItem implements Serializable {

    private int icon1;

    private String coin1;

    private int icon2;

    private String coin2;

    public ExchangeItem(int icon1, String coin1, int icon2, String coin2) {
        this.icon1 = icon1;
        this.coin1 = coin1;
        this.icon2 = icon2;
        this.coin2 = coin2;
    }


}
