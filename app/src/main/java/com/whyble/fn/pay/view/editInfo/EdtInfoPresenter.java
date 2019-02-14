package com.whyble.fn.pay.view.editInfo;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;

public class EdtInfoPresenter implements EdtInfoIn.Presenter {

    EdtInfoIn.View view;

    EdtInfoModel model;

    public EdtInfoPresenter(EdtInfoIn.View view) {
        this.view = view;
        this.model = new EdtInfoModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void editInfo(String oldPassword, String newPassword) {
        model.editInfo(oldPassword,newPassword , new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.editInfoResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
