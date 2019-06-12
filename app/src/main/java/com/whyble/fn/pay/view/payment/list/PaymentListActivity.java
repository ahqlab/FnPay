package com.whyble.fn.pay.view.payment.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.adapter.AbsractCommonAdapter;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.databinding.ActivityHistoryBinding;
import com.whyble.fn.pay.databinding.ActivityPaymentListBinding;
import com.whyble.fn.pay.databinding.PaymentHistoryListviewItemBinding;
import com.whyble.fn.pay.domain.Payment;
import com.whyble.fn.pay.domain.PaymentList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentListActivity extends BaseActivity<PaymentListActivity> implements PaymentListIn.View {

    ActivityPaymentListBinding binding;

    PaymentListIn.Presenter presenter;

    AbsractCommonAdapter<PaymentList> pAdapter;

    @BindView(R.id.noHistory)
    LinearLayout noHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_payment_list);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_list);
        binding.setActivity(PaymentListActivity.this);
        ButterKnife.bind(this);
        presenter = new PaymentListPresenter(this);
        presenter.loadData(this);
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Payment List");
        super.setToolbarColor();
        presenter.getTransaction();
    }

    @Override
    protected BaseActivity<PaymentListActivity> getActivityClass() {
        return PaymentListActivity.this;
    }


    @Override
    public void setPayment(String s) {
        Log.e("HJLEE", "JSON" + s);
        Gson gson = new Gson();
        Payment payment = gson.fromJson(s, Payment.class);

       /* List<PaymentList> list = payment.getList();
        for (PaymentList paymentList: list) {
            Log.e("HJLEE", paymentList.toString());
        }*/

        if (payment.getList().size() > 0) {
            binding.listview.setVisibility(View.VISIBLE);
            noHistory.setVisibility(View.GONE);
            pAdapter = new AbsractCommonAdapter<PaymentList>(PaymentListActivity.this, payment.getList()) {
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

        } else {
            binding.listview.setVisibility(View.GONE);
            noHistory.setVisibility(View.VISIBLE);
        }
    }

}
