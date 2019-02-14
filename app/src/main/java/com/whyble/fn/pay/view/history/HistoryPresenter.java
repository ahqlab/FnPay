package com.whyble.fn.pay.view.history;

import android.content.Context;

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
}
