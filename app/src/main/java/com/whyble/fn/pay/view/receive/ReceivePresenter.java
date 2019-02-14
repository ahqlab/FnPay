package com.whyble.fn.pay.view.receive;

import android.content.Context;

import com.whyble.fn.pay.MainModel;
import com.whyble.fn.pay.common.CommonModel;
import com.whyble.fn.pay.view.send.SendIn;
import com.whyble.fn.pay.view.send.SendModel;

public class ReceivePresenter implements ReceiveIn.Presenter {

    ReceiveIn.View view;

    ReceiveModel model;

    MainModel mainModel;

    public ReceivePresenter(ReceiveIn.View view) {
        this.view = view;
        this.model = new ReceiveModel();
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
