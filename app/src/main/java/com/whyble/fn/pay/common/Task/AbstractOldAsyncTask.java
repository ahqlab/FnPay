package com.whyble.fn.pay.common.Task;

import android.os.AsyncTask;

import com.whyble.fn.pay.util.TextManager.TextManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public abstract class AbstractOldAsyncTask extends AsyncTask<List<NameValuePair>, Void, String> {

    protected abstract void doPostExecute(String d);

    protected abstract void doPreExecute();

    public String url;

    public AbstractOldAsyncTask(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(List<NameValuePair>... lists) {
        Https https = new Https();
        HttpClient client = https.getHttpClient();
        HttpParams param = client.getParams();
        HttpConnectionParams.setConnectionTimeout(param, 5000);
        HttpConnectionParams.setSoTimeout(param, 5000);
        String responseBody = null;

        try {
            List<NameValuePair> nameValuePairs = lists[0];

            HttpPost httppost = new HttpPost(TextManager.SERVER_ROOT + url);

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entity);
            HttpResponse response = client.execute(httppost);
            responseBody = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody.trim();
    }

    @Override
    protected void onPreExecute() {
        doPreExecute();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String str) {
        doPostExecute(str);
        super.onPostExecute(str);
    }
}
