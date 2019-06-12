package com.whyble.fn.pay.common.base;

import android.content.Context;

public class BaseIn {

    interface View{

        void orderResult(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void order(String order);
    }
}
