package com.whyble.fn.pay.view.send.pincheck;

import android.content.Context;

import com.whyble.fn.pay.MainModel;
import com.whyble.fn.pay.common.CommonModel;

public class PinCheckPresenter implements PinCheckIn.Presenter {

    PinCheckIn.View view;

    PinCheckModel model;
    MainModel mainModel;

    public PinCheckPresenter(PinCheckIn.View view) {
        this.view = view;
        this.model = new PinCheckModel();

    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void verificationPinnumber(String text) {
        model.doVerificationPinnumber(text, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.doVerificationPinnumberResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

}
