package com.whyble.fn.pay.view.join;

import android.content.Context;

public class JoinIn {

    interface View{

        void signupResult(String result);
    }
    interface Presenter{
        void loadData(Context context);

        void signup(String id, String password, String passwordCheck, String pinnumber,  String name);
    }
}
