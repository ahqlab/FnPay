package com.whyble.fn.pay.view.send.pincheck;

import android.content.Context;
import android.text.Editable;

public class PinCheckIn {

    interface View{

        void doVerificationPinnumberResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void verificationPinnumber(String text);
    }
}
