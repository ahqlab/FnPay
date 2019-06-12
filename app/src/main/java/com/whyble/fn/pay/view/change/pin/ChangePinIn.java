package com.whyble.fn.pay.view.change.pin;

import android.content.Context;

public class ChangePinIn {

    interface View{


        void setChangeResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void setChangePin(String oldPinnumber, String newPinnumber);
    }
}
