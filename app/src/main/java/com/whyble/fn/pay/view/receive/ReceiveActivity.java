package com.whyble.fn.pay.view.receive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.CoinInfo;
import com.whyble.fn.pay.util.device.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiveActivity extends BaseActivity<ReceiveActivity> implements ReceiveIn.View{

    Spinner spinner;

    ArrayAdapter<CharSequence> adspin;

    ReceiveIn.Presenter presenter;

    @BindView(R.id.addr)
    TextView addr;

    @BindView(R.id.qrcode)
    ImageView qrcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        ButterKnife.bind(this);
        presenter = new ReceivePresenter(this);
        presenter.loadData(this);

        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("RECEIVE");
        super.setToolbarColor();

        spinner = (Spinner) findViewById(R.id.spinner);
        setSpinner();
    }

    @Override
    protected BaseActivity<ReceiveActivity> getActivityClass() {
        return ReceiveActivity.this;
    }

    private void setSpinner() {
        adspin = ArrayAdapter.createFromResource(this, R.array.coins , android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.getCoinInfo(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void setCoinInfo(String s) {
        Gson gson = new Gson();
        CoinInfo response = gson.fromJson(s, CoinInfo.class);
        Picasso.with(ReceiveActivity.this)
                .load(response.getQr())
                .into(qrcode);
        addr.setText(response.getAddress());

    }

    @OnClick({R.id.tabToCopy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tabToCopy:
                DeviceUtils.setClipBoardLink(ReceiveActivity.this, addr.getText().toString());
                break;
        }
    }
}
