package com.whyble.fn.pay.view.join;

import android.os.Bundle;

import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;

public class JoinActivity extends BaseActivity<JoinActivity> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    @Override
    protected BaseActivity<JoinActivity> getActivityClass() {
        return JoinActivity.this;
    }
}
