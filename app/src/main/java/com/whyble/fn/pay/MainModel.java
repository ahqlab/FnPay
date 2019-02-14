package com.whyble.fn.pay;

import android.content.Context;
import android.util.Log;

import com.whyble.fn.pay.common.CommonModel;
import com.whyble.fn.pay.common.SharedPrefManager;
import com.whyble.fn.pay.common.Task.AbstractOldAsyncTask;
import com.whyble.fn.pay.util.TextManager.TextManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getCoinInfo(int i, final DomainCallBackListner<String> domainCallBackListner) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        new AbstractOldAsyncTask("coin_info.php?valid_user=" + sharedPrefManager.getStringExtra(TextManager.VALID_USER) + "&coin_type=" + String.valueOf(i)){

            @Override
            protected void doPostExecute(String d) {
                domainCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(nameValuePairs);
    }
}
