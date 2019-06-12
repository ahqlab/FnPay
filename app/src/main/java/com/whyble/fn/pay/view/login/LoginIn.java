package com.whyble.fn.pay.view.login;

import android.content.Context;
import android.widget.EditText;

public class LoginIn {

    interface View{

        void setLoginResult(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void login(String inputId, String inputPass);
    }
}
