package com.whyble.fn.pay.view.exchange;

import android.content.DialogInterface;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.adapter.AbsractCommonAdapter;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.databinding.ActivityExchangeBinding;
import com.whyble.fn.pay.databinding.SpinnerItemBinding;
import com.whyble.fn.pay.domain.CoinInfo;
import com.whyble.fn.pay.domain.ExchangeItem;
import com.whyble.fn.pay.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeActivity extends BaseActivity<ExchangeActivity> implements ExchangetIn.View{

    ActivityExchangeBinding binding;

    AbsractCommonAdapter<ExchangeItem> adapter;

    int coinType;

    ExchangetIn.Presenter presenter;

    @BindView(R.id.coin_title)
    TextView coinTitle;
    @BindView(R.id.balance)
    TextView balance;

    int coinPrice;
    float totalBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exchange);
        binding.setActivity(ExchangeActivity.this);
        ButterKnife.bind(this);
        presenter = new ExchangePresenter(this);
        presenter.loadData(this);
        presenter.getCoinInfo(0);
        coinBarClick("FNC");

        setFcnSpinner();
    }

    @OnClick({R.id.fnc_coin, R.id.fnc_coin_btn,
            R.id.ltc_coin, R.id.ltc_coin_btn,
            R.id.dash_coin, R.id.dash_coin_btn,
            R.id.btc_coin, R.id.btc_coin_btn,
            R.id.bch_coin, R.id.bch_coin_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fnc_coin:
                coinBarClick("FNC");
                setFcnSpinner();
                presenter.getCoinInfo(0);
                coinType = 0;
                break;
            case R.id.fnc_coin_btn:
                coinBarClick("FNC");
                setFcnSpinner();
                presenter.getCoinInfo(0);
                coinType = 0;
                break;
            case R.id.ltc_coin:
                coinBarClick("LTC");
                setLtcSpinner();
                presenter.getCoinInfo(1);
                coinType = 1;
                break;
            case R.id.ltc_coin_btn:
                coinBarClick("LTC");
                setLtcSpinner();
                presenter.getCoinInfo(1);
                coinType = 1;
                break;
            case R.id.dash_coin:
                coinBarClick("DASH");
                setDashSpinner();
                presenter.getCoinInfo(2);
                coinType = 2;
                break;
            case R.id.dash_coin_btn:
                coinBarClick("DASH");
                setDashSpinner();
                presenter.getCoinInfo(2);
                coinType = 2;
                break;
            case R.id.btc_coin:
                coinBarClick("BTC");
                setBtcSpinner();
                presenter.getCoinInfo(3);
                coinType = 3;
                break;
            case R.id.btc_coin_btn:
                coinBarClick("BTC");
                setBtcSpinner();
                presenter.getCoinInfo(3);
                coinType = 3;
                break;
            case R.id.bch_coin:
                coinBarClick("BCH");
                setBchSpinner();
                presenter.getCoinInfo(4);
                coinType = 4;
                break;
            case R.id.bch_coin_btn:
                coinBarClick("BCH");
                setBchSpinner();
                presenter.getCoinInfo(4);
                coinType = 4;
                break;
        }
    }

    public void coinBarClick(String coin) {
        if (coin.equals("FNC")) {
            LinearLayout fcLayout = (LinearLayout) findViewById(R.id.fnc_coin);
            fcLayout.setBackgroundResource(R.drawable.rounded_left_radius_light_gray_bg);

            LinearLayout ltcLayout = (LinearLayout) findViewById(R.id.ltc_coin);
            ltcLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout dashLayout = (LinearLayout) findViewById(R.id.dash_coin);
            dashLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout btnLayout = (LinearLayout) findViewById(R.id.btc_coin);
            btnLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout bchLayout = (LinearLayout) findViewById(R.id.bch_coin);
            bchLayout.setBackgroundResource(R.drawable.rounded_right_radius_trans_bg);
        } else if (coin.equals("LTC")) {
            LinearLayout fcLayout = (LinearLayout) findViewById(R.id.fnc_coin);
            fcLayout.setBackgroundResource(R.drawable.rounded_left_radius_trans_bg);

            LinearLayout ltcLayout = (LinearLayout) findViewById(R.id.ltc_coin);
            ltcLayout.setBackgroundResource(R.drawable.rounded_radius_light_gray_bg);

            LinearLayout dashLayout = (LinearLayout) findViewById(R.id.dash_coin);
            dashLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout btnLayout = (LinearLayout) findViewById(R.id.btc_coin);
            btnLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout bchLayout = (LinearLayout) findViewById(R.id.bch_coin);
            bchLayout.setBackgroundResource(R.drawable.rounded_right_radius_trans_bg);
        } else if (coin.equals("DASH")) {
            LinearLayout fcLayout = (LinearLayout) findViewById(R.id.fnc_coin);
            fcLayout.setBackgroundResource(R.drawable.rounded_left_radius_trans_bg);

            LinearLayout ltcLayout = (LinearLayout) findViewById(R.id.ltc_coin);
            ltcLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout dashLayout = (LinearLayout) findViewById(R.id.dash_coin);
            dashLayout.setBackgroundResource(R.drawable.rounded_radius_light_gray_bg);

            LinearLayout btnLayout = (LinearLayout) findViewById(R.id.btc_coin);
            btnLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout bchLayout = (LinearLayout) findViewById(R.id.bch_coin);
            bchLayout.setBackgroundResource(R.drawable.rounded_right_radius_trans_bg);
        } else if (coin.equals("BTC")) {
            LinearLayout fcLayout = (LinearLayout) findViewById(R.id.fnc_coin);
            fcLayout.setBackgroundResource(R.drawable.rounded_left_radius_trans_bg);

            LinearLayout ltcLayout = (LinearLayout) findViewById(R.id.ltc_coin);
            ltcLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout dashLayout = (LinearLayout) findViewById(R.id.dash_coin);
            dashLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout btnLayout = (LinearLayout) findViewById(R.id.btc_coin);
            btnLayout.setBackgroundResource(R.drawable.rounded_radius_light_gray_bg);

            LinearLayout bchLayout = (LinearLayout) findViewById(R.id.bch_coin);
            bchLayout.setBackgroundResource(R.drawable.rounded_right_radius_trans_bg);
        } else if (coin.equals("BCH")) {
            LinearLayout fcLayout = (LinearLayout) findViewById(R.id.fnc_coin);
            fcLayout.setBackgroundResource(R.drawable.rounded_left_radius_trans_bg);

            LinearLayout ltcLayout = (LinearLayout) findViewById(R.id.ltc_coin);
            ltcLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout dashLayout = (LinearLayout) findViewById(R.id.dash_coin);
            dashLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout btnLayout = (LinearLayout) findViewById(R.id.btc_coin);
            btnLayout.setBackgroundResource(R.drawable.rounded_radius_trans_bg);

            LinearLayout bchLayout = (LinearLayout) findViewById(R.id.bch_coin);
            bchLayout.setBackgroundResource(R.drawable.rounded_right_radius_light_gray_bg);
        }
    }

    public void setFcnSpinner(){
        presenter.getExchangeSpinner(0);
    }


    public void setLtcSpinner(){
        presenter.getExchangeSpinner(1);
    }

    public void setDashSpinner(){
        presenter.getExchangeSpinner(2);
    }

    public void setBtcSpinner(){
        presenter.getExchangeSpinner(3);
    }

    public void setBchSpinner(){
        presenter.getExchangeSpinner(4);
    }


    public void setSpinner(List<ExchangeItem> list){

        adapter = new AbsractCommonAdapter<ExchangeItem>(ExchangeActivity.this, list) {

            SpinnerItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = adapter.inflater.inflate(R.layout.spinner_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(adapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (SpinnerItemBinding) convertView.getTag();
                    adapterBinding.setDomain(adapter.data.get(position));
                }
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return adapterBinding.getRoot();
            }
        };
        binding.spinner.setAdapter(adapter);
    }

    @Override
    protected BaseActivity<ExchangeActivity> getActivityClass() {
        return ExchangeActivity.this;
    }

    @BindingAdapter({"loadPetPicasoImage"})
    public static void loadPicasoImage(ImageView imageView, String title) {
        if(title.matches("FNC")){
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.coin01));
        }else if(title.matches("LTC")){
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.coin02));
        }else if(title.matches("DASH")){
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.coin03));
        }else if(title.matches("BTC")){
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.coin04));
        }else if(title.matches("BCH")){
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.coin05));
        }
    }

    @Override
    public void setCoinInfo(String s) {
        Gson gson = new Gson();
        CoinInfo response = gson.fromJson(s, CoinInfo.class);
        coinTitle.setText(response.getCoin_title());
        balance.setText(response.getBalance());
        coinPrice = Integer.parseInt(response.getCoin_price());
        totalBalance = Float.parseFloat(response.getBalance());
    }

    @Override
    public void setExchangeSpinner(String s) {
        Log.e("HJLEE", "S : " + s);
        Gson gson = new Gson();
        List<ExchangeItem> list = gson.fromJson(s, new TypeToken<List<ExchangeItem>>(){}.getType());
        setSpinner(list);
    }
}
