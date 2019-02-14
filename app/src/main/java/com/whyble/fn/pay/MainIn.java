package com.whyble.fn.pay;

import android.content.Context;

public class MainIn {

    interface View{

        void getCoinInfo(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getCoinInfo(int i);
    }
}
