package com.whyble.fn.pay.view.change.password;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;

public class ChangePasswordPresenter implements ChangePasswordIn.Presenter {

    ChangePasswordIn.View view;

    ChangePasswordModel model;

    public ChangePasswordPresenter(ChangePasswordIn.View view) {
        this.view = view;
        this.model = new ChangePasswordModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void doChangePassword(String oldPass, String newPass) {
        model.doChangePassword(oldPass, newPass , new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setChangeInfo(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    /*@Override
    public void getPinInfo() {
        model.getPinInfo(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setPinInfo(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }*/

   /* @Override
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
    }*/
}
