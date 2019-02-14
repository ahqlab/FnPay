package com.whyble.fn.pay.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CoinInfo extends Domain{

    private String address;
    private String balance;
    private String coin_title;
    private String qr;
    private String coin_price;
}
