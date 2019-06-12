package com.whyble.fn.pay.view.send;

import android.content.Context;

import com.whyble.fn.pay.MainModel;
import com.whyble.fn.pay.common.CommonModel;

public class SendPresenter implements SendIn.Presenter {

    SendIn.View view;

    SendModel model;
    MainModel mainModel;

    public SendPresenter(SendIn.View view) {
        this.view = view;
        this.model = new SendModel();
        this.mainModel = new MainModel();

    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
        mainModel.loadData(context);
    }

    @Override
    public void getCoinInfo() {
        mainModel.getCoinInfo(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setCoinInfo(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void sendCoin(String addr, String sendCoin) {
        model.sendCoin(addr, sendCoin, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.sendCoinResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
