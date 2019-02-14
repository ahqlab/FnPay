package com.whyble.fn.pay.view.login;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;
import com.whyble.fn.pay.common.SharedPrefManager;
import com.whyble.fn.pay.common.Task.AbstractOldAsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void login(String inputId, String inputPass, final DomainCallBackListner<String> domainCallBackListner) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", inputId));
        nameValuePairs.add(new BasicNameValuePair("pass", inputPass));

        new AbstractOldAsyncTask("login.php"){

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
