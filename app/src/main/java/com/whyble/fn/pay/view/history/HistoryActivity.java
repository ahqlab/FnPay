package com.whyble.fn.pay.view.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.databinding.ActivityHistoryBinding;
import com.whyble.fn.pay.view.exchange.ExchangeActivity;

import butterknife.ButterKnife;

public class HistoryActivity extends BaseActivity<HistoryActivity> implements HistoryIn.View{

    ActivityHistoryBinding binding;

    HistoryIn.Presenter presenter;

    Spinner spinner;

    ArrayAdapter<CharSequence> adspin;

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


    }

    @Override
    protected BaseActivity<HistoryActivity> getActivityClass() {
        return HistoryActivity.this;
    }

    private void setSpinner() {
        adspin = ArrayAdapter.createFromResource(this, R.array.coins , android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //presenter.getCoinInfo(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
