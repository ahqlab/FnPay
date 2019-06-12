package com.whyble.fn.pay.view.history;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;

public class HistoryPresenter implements HistoryIn.Presenter {

    HistoryIn.View view;

    HistoryModel model;

    public HistoryPresenter(HistoryIn.View view) {
        this.view = view;
        this.model = new HistoryModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }



    @Override
    public void getTransaction() {
        model.getSend(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setSend(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

}
