package com.whyble.fn.pay.domain;

import java.util.List;

import lombok.Data;

@Data
public class Exchange extends Domain{

    List<ExchangeItem> coin_list;

}
