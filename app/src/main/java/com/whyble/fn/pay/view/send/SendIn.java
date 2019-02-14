package com.whyble.fn.pay.view.send;

import android.content.Context;

public class SendIn {

    interface View{

        void setCoinInfo(String s);

        void sendCoinResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getCoinInfo(int i);

        void sendCoin(String addr, String sendCoin, String coinType);
    }
}
