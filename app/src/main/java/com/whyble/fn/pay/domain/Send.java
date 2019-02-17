package com.whyble.fn.pay.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Send extends Domain{

    List<SendList> list;
}
