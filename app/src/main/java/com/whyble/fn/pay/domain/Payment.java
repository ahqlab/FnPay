package com.whyble.fn.pay.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Payment extends Domain{

    List<PaymentList> list;
}
