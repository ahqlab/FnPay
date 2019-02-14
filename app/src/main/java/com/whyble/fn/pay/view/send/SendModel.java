package com.whyble.fn.pay.view.send;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;
import com.whyble.fn.pay.common.SharedPrefManager;
import com.whyble.fn.pay.common.Task.AbstractOldAsyncTask;
import com.whyble.fn.pay.util.TextManager.TextManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class SendModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void sendCoin(String addr, String sendCoin, String coinType, final DomainCallBackListner<String> domainCallBackListner) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("addr", addr));
        nameValuePairs.add(new BasicNameValuePair("send_coin", sendCoin));
        nameValuePairs.add(new BasicNameValuePair("coin_type", coinType));
        nameValuePairs.add(new BasicNameValuePair("valid_user", sharedPrefManager.getStringExtra(TextManager.VALID_USER)));

        new AbstractOldAsyncTask("send_coin.php"){

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
