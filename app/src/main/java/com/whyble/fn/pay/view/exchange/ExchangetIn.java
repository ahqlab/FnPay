package com.whyble.fn.pay.view.exchange;

import android.content.Context;

public class ExchangetIn {

    interface View{

        void setCoinInfo(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getCoinInfo(int i);
    }
}
