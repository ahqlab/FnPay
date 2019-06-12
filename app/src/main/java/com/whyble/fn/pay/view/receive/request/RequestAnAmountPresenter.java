package com.whyble.fn.pay.view.receive.request;

import android.content.Context;

import com.whyble.fn.pay.MainModel;

public class RequestAnAmountPresenter implements RequestAnAmountIn.Presenter {

    RequestAnAmountIn.View view;

    RequestAnAmountModel model;
    MainModel mainModel;

    public RequestAnAmountPresenter(RequestAnAmountIn.View view) {
        this.view = view;
        this.model = new RequestAnAmountModel();

    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }


}
