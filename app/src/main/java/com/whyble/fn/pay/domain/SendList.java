package com.whyble.fn.pay.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SendList extends Domain {
    private String account;
    private String address;
    private String category;
    private String amount;
    private String fee;
    private String confirmations;
    private String time;
    private String otheraccount;
    private String txid;
    private String status;
}
