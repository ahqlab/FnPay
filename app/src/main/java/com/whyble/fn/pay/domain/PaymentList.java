package com.whyble.fn.pay.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentList extends Domain{

    private String id;
    private String name;
    private String send_address;
    private String price;
    private String amount;
    private String fee;
    private String signdate1;
    private String signdate2;
    private String r_name;
}
