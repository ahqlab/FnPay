package com.whyble.fn.pay.view.receive;

import android.content.Context;

public class ReceiveIn {

    interface View{

        void setCoinInfo(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getCoinInfo();

    }
}
