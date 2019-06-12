package com.whyble.fn.pay.view.payment;

import android.content.Context;

public class PaymentIn {

    interface View{

        void setCoinInfo(String s);

        void doPaymentResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getCoinInfo();

        void doPayment(String s, String s1, String s2);
    }
}
