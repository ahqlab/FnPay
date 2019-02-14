package com.whyble.fn.pay;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.whyble.fn.pay.common.CommonModel;
import com.whyble.fn.pay.view.editInfo.EdtInfoActivity;
import com.whyble.fn.pay.view.exchange.ExchangeActivity;
import com.whyble.fn.pay.view.payment.PaymentActivity;
import com.whyble.fn.pay.view.receive.ReceiveActivity;
import com.whyble.fn.pay.view.send.SendActivity;

import butterknife.OnClick;

public class MainPresenter implements MainIn.Presenter {

    MainIn.View view;

    MainModel model;

    public MainPresenter(MainIn.View view) {
        this.view = view;
        this.model = new MainModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getCoinInfo(int i) {
        model.getCoinInfo(i, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.getCoinInfo(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }



}
