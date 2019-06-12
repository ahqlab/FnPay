package com.whyble.fn.pay.view.change.password;

import android.content.Context;

public class ChangePasswordIn {

    interface View{

        void setChangeInfo(String s);

        //void setPinInfo(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void doChangePassword(String oldPass, String newPass);

        // void getPinInfo();
    }
}
