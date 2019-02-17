package com.whyble.fn.pay.view.history;

import android.content.Context;

public class HistoryIn {

    interface View{

        void setPayment(String s);

        void setSend(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getPayment(int coinType);

        void getSend(int coinType);
    }
}
