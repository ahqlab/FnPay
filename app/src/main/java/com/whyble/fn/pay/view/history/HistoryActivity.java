package com.whyble.fn.pay.view.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.adapter.AbsractCommonAdapter;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.databinding.ActivityHistoryBinding;
import com.whyble.fn.pay.databinding.PaymentHistoryListviewItemBinding;
import com.whyble.fn.pay.databinding.SendHistoryListviewItemBinding;
import com.whyble.fn.pay.databinding.SpinnerItemBinding;
import com.whyble.fn.pay.domain.Exchange;
import com.whyble.fn.pay.domain.ExchangeItem;
import com.whyble.fn.pay.domain.Payment;
import com.whyble.fn.pay.domain.PaymentList;
import com.whyble.fn.pay.domain.Send;
import com.whyble.fn.pay.domain.SendList;
import com.whyble.fn.pay.view.exchange.ExchangeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity<HistoryActivity> implements HistoryIn.View {

    ActivityHistoryBinding binding;

    HistoryIn.Presenter presenter;

    AbsractCommonAdapter<PaymentList> pAdapter;

    AbsractCommonAdapter<SendList> sAdapter;

    Spinner spinner;

    ArrayAdapter<CharSequence> adspin;

    @BindView(R.id.tab1)
    Button tab1;
    @BindView(R.id.tab2)
    Button tab2;

    @BindView(R.id.send_menu)
    LinearLayout menuSend;

    @BindView(R.id.payment_menu)
    LinearLayout paymentMenu;

    int coinType = 0;

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        binding.setActivity(HistoryActivity.this);
        ButterKnife.bind(this);
        presenter = new HistoryPresenter(this);
        presenter.loadData(this);
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("HISTORY");
        super.setToolbarColor();
        spinner = (Spinner) findViewById(R.id.spinner);
        setSpinner();
        tab1.setBackgroundColor(getResources().getColor(R.color.white));
        tab1.setTextColor(getResources().getColor(R.color.blue_text));
        presenter.getPayment(coinType);
        paymentMenu.setVisibility(View.VISIBLE);
        menuSend.setVisibility(View.GONE);
        flag = 0;
    }

    @OnClick({R.id.tab1, R.id.tab2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab1:
                tab1.setBackgroundColor(getResources().getColor(R.color.white));
                tab1.setTextColor(getResources().getColor(R.color.blue_text));
                tab2.setBackgroundColor(getResources().getColor(R.color.history_trans_bg));
                tab2.setTextColor(getResources().getColor(R.color.white));
                presenter.getPayment(coinType);
                paymentMenu.setVisibility(View.VISIBLE);
                menuSend.setVisibility(View.GONE);
                flag = 0;
                break;
            case R.id.tab2:
                tab1.setBackgroundColor(getResources().getColor(R.color.history_trans_bg));
                tab1.setTextColor(getResources().getColor(R.color.white));
                tab2.setBackgroundColor(getResources().getColor(R.color.white));
                tab2.setTextColor(getResources().getColor(R.color.blue_text));
                presenter.getSend(coinType);
                paymentMenu.setVisibility(View.GONE);
                menuSend.setVisibility(View.VISIBLE);
                flag = 1;
                break;
        }
    }

    public void setPaymentListview() {
    }

    public void setSendListview() {
    }

    @Override
    protected BaseActivity<HistoryActivity> getActivityClass() {
        return HistoryActivity.this;
    }

    private void setSpinner() {
        adspin = ArrayAdapter.createFromResource(this, R.array.coins, android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                coinType = i;
                if(flag == 0){
                    presenter.getPayment(coinType);
                }else{
                    presenter.getSend(coinType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void setPayment(String s) {
        Gson gson = new Gson();
        Payment payment = gson.fromJson(s, Payment.class);
        if (payment.getList() != null) {
            pAdapter = new AbsractCommonAdapter<PaymentList>(HistoryActivity.this, payment.getList()) {
                PaymentHistoryListviewItemBinding adapterBinding;

                @Override
                protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = pAdapter.inflater.inflate(R.layout.payment_history_listview_item, null);
                        adapterBinding = DataBindingUtil.bind(convertView);
                        adapterBinding.setDomain(pAdapter.data.get(position));
                        convertView.setTag(adapterBinding);
                    } else {
                        adapterBinding = (PaymentHistoryListviewItemBinding) convertView.getTag();
                        adapterBinding.setDomain(pAdapter.data.get(position));
                    }
                    return adapterBinding.getRoot();
                }
            };
            binding.listview.setAdapter(pAdapter);
        }else{
            pAdapter.notifyDataSetChanged();
            pAdapter.data.clear();
        }
    }

    @Override
    public void setSend(String s) {
        Gson gson = new Gson();
        Send send = gson.fromJson(s, Send.class);
        if (send.getList() != null) {
            sAdapter = new AbsractCommonAdapter<SendList>(HistoryActivity.this, send.getList()) {
                SendHistoryListviewItemBinding adapterBinding;

                @Override
                protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = sAdapter.inflater.inflate(R.layout.send_history_listview_item, null);
                        adapterBinding = DataBindingUtil.bind(convertView);
                        adapterBinding.setDomain(sAdapter.data.get(position));
                        convertView.setTag(adapterBinding);
                    } else {
                        adapterBinding = (SendHistoryListviewItemBinding) convertView.getTag();
                        adapterBinding.setDomain(sAdapter.data.get(position));
                    }
                    return adapterBinding.getRoot();
                }
            };
            binding.listview.setAdapter(sAdapter);
        }else{
            sAdapter.notifyDataSetChanged();
            sAdapter.data.clear();
        }
    }
}
