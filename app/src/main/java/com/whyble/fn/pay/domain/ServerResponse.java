package com.whyble.fn.pay.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServerResponse extends Domain{

    private String msg;

    private String result;
}
