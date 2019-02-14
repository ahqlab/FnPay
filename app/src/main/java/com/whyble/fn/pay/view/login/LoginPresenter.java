package com.whyble.fn.pay.view.login;

import android.content.Context;
import android.util.Log;

import com.whyble.fn.pay.common.CommonModel;


public class LoginPresenter implements LoginIn.Presenter {

    LoginIn.View view;

    LoginModel model;

    public LoginPresenter(LoginIn.View view) {
        this.view = view;
        this.model = new LoginModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void login(String inputId, String inputPass) {
        model.login(inputId, inputPass, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setLoginResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
