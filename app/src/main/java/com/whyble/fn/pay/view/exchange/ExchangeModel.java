package com.whyble.fn.pay.view.exchange;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;
import com.whyble.fn.pay.common.SharedPrefManager;

public class ExchangeModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

}
