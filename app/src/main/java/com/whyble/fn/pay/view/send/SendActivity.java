package com.whyble.fn.pay.view.send;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;

public class SendActivity extends BaseActivity<SendActivity> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
    }

    @Override
    protected BaseActivity<SendActivity> getActivityClass() {
        return SendActivity.this;
    }
}
