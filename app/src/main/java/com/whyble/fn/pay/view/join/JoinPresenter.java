package com.whyble.fn.pay.view.join;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;

public class JoinPresenter implements JoinIn.Presenter {

    JoinIn.View view;

    JoinModel model;

    public JoinPresenter(JoinIn.View view) {
        this.view = view;
        this.model = new JoinModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void signup(String id, String password, String passwordCheck,String pinnumber, String name) {
        model.signup(id, password, passwordCheck, pinnumber, name, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.signupResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
