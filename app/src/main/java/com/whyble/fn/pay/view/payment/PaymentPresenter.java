package com.whyble.fn.pay.view.payment;

import android.content.Context;

import com.whyble.fn.pay.MainModel;
import com.whyble.fn.pay.common.CommonModel;

public class PaymentPresenter implements PaymentIn.Presenter {

    PaymentIn.View view;

    PaymentModel model;
    MainModel mainModel;

    public PaymentPresenter(PaymentIn.View view) {
        this.view = view;
        this.model = new PaymentModel();
        this.mainModel = new MainModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
        mainModel.loadData(context);
    }

    @Override
    public void getCoinInfo(int i) {
        mainModel.getCoinInfo(i, new CommonModel.DomainCallBackListner<String>() {
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
    public void doPayment(String s, String s1, String s2, String s3) {
        model.doPayment(s, s1, s2, s3, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.doPaymentResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
