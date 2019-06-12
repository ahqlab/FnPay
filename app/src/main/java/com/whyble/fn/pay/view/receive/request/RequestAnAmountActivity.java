package com.whyble.fn.pay.view.receive.request;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;

import butterknife.ButterKnife;

public class RequestAnAmountActivity extends BaseActivity<RequestAnAmountActivity> implements RequestAnAmountIn.View{

    RequestAnAmountIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_an_amount);
        ButterKnife.bind(this);
        presenter = new RequestAnAmountPresenter(RequestAnAmountActivity.this);
        presenter.loadData(RequestAnAmountActivity.this);
    }

    @Override
    protected BaseActivity<RequestAnAmountActivity> getActivityClass() {
        return RequestAnAmountActivity.this;
    }
}
