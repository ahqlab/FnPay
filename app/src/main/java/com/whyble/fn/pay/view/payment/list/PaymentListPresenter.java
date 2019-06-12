package com.whyble.fn.pay.view.payment.list;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;

public class PaymentListPresenter implements PaymentListIn.Presenter {

    PaymentListIn.View view;

    PaymentListModel model;

    public PaymentListPresenter(PaymentListIn.View view) {
        this.view = view;
        this.model = new PaymentListModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }



    @Override
    public void getTransaction() {
        model.getPayment( new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setPayment(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
