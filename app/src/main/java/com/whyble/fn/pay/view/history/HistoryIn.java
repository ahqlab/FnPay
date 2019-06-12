package com.whyble.fn.pay.view.history;

import android.content.Context;

public class HistoryIn {

    interface View{

        void setSend(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getTransaction();
    }
}
