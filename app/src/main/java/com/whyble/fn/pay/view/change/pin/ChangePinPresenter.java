package com.whyble.fn.pay.view.change.pin;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;

public class ChangePinPresenter implements ChangePinIn.Presenter {

    ChangePinIn.View view;

    ChangePinModel model;

    public ChangePinPresenter(ChangePinIn.View view) {
        this.view = view;
        this.model = new ChangePinModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }


    @Override
    public void setChangePin(String oldPinnumber, String newPinnumber) {
        model.doChangePin(oldPinnumber,newPinnumber , new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setChangeResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

}
