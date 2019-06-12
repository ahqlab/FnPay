package com.whyble.fn.pay.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressBook extends Domain{

    private String a_no;
    private String a_id;
    private String a_sendaddr;
    private String a_sendname;
    private String a_sendid;
    private String a_signdate;
    private String name;
}
