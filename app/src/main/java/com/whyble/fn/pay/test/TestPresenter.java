package com.whyble.fn.pay.test;

import android.content.Context;

public class TestPresenter implements TestIn.Presenter {

    TestIn.View view;

    TestModel model;

    public TestPresenter(TestIn.View view) {
        this.view = view;
        this.model = new TestModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }
}
