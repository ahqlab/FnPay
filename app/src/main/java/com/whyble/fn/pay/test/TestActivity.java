package com.whyble.fn.pay.test;

import android.os.Bundle;

import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;

public class TestActivity extends BaseActivity<TestActivity> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    protected BaseActivity<TestActivity> getActivityClass() {
        return null;
    }
}
