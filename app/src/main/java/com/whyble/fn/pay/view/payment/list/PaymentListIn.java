package com.whyble.fn.pay.view.payment.list;

import android.content.Context;

public class PaymentListIn {

    interface View{

        void setPayment(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getTransaction();
    }
}
