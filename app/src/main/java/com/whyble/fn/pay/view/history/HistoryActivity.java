package com.whyble.fn.pay.view.history;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
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
import com.whyble.fn.pay.util.device.DeviceUtils;
import com.whyble.fn.pay.view.exchange.ExchangeActivity;
import com.whyble.fn.pay.view.receive.ReceiveActivity;
import com.whyble.fn.pay.view.send.SendActivity;
import com.whyble.fn.pay.view.send.pincheck.PinCheckActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity<HistoryActivity> implements HistoryIn.View {

    ActivityHistoryBinding binding;

    HistoryIn.Presenter presenter;

    AbsractCommonAdapter<SendList> sAdapter;

    @BindView(R.id.noHistory)
    LinearLayout noHistory;


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
        pageTitle.setText("Transaction");
        super.setToolbarColor();
        presenter.getTransaction();
    }

    @Override
    protected BaseActivity<HistoryActivity> getActivityClass() {
        return HistoryActivity.this;
    }


    @Override
    public void setSend(String s) {
        Gson gson = new Gson();
        Send send = gson.fromJson(s, Send.class);
        // List<SendList> list = send.getList();
     /*   for (SendList paymentList: list) {
            Log.e("HJLEE", paymentList.toString());
        }*/
        if(send.getList().size() > 0){
            binding.listview.setVisibility(View.VISIBLE);
            noHistory.setVisibility(View.GONE);
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
                    adapterBinding.copy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DeviceUtils.setClipBoardLink(HistoryActivity.this, sAdapter.data.get(position).getAddress());
                        }
                    });
                    adapterBinding.request.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), PinCheckActivity.class);
                            intent.putExtra("address", sAdapter.data.get(position).getAddress());
                            startActivity(intent);
                            finish();
                        }
                    });
                    return adapterBinding.getRoot();
                }
            };
            binding.listview.setAdapter(sAdapter);
        }else{
            binding.listview.setVisibility(View.GONE);
            noHistory.setVisibility(View.VISIBLE);
        }

    }
}
