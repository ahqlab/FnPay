package com.whyble.fn.pay.view.exchange;

import android.content.Context;

public class ExchangetIn {

    interface View{

        void setCoinInfo(String s);

        void setExchangeSpinner(String s);

        void setExchangeResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getCoinInfo(int i);

        void getExchangeSpinner(int i);

        void doExchange(String coinType, String exType, String exCoin, String sendCoin);
    }
}
