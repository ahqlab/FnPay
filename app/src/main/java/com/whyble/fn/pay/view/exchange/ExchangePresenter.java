package com.whyble.fn.pay.view.exchange;

import android.content.Context;

import com.whyble.fn.pay.MainModel;
import com.whyble.fn.pay.common.CommonModel;

public class ExchangePresenter implements ExchangetIn.Presenter {

    ExchangetIn.View view;

    ExchangeModel model;
    MainModel mainModel;

    public ExchangePresenter(ExchangetIn.View view) {
        this.view = view;
        this.model = new ExchangeModel();
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
}
