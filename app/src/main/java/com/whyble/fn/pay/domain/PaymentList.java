package com.whyble.fn.pay.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentList extends Domain{

    private String send_address;
    private String amount;
    private String signdate;
    private String signdate1;
    private String signdate2;
    private String r_name;
}
