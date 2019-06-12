package com.whyble.fn.pay.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Profile extends Domain{

   private  String username;
}
