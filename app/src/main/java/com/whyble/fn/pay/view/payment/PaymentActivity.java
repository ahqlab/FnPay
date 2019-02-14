package com.whyble.fn.pay.view.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.CoinInfo;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.util.ValidationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity<PaymentActivity> implements PaymentIn.View {

    PaymentIn.Presenter presenter;

    @BindView(R.id.coin_title)
    TextView coinTitle;
    @BindView(R.id.balance)
    TextView balance;

    @BindView(R.id.addr)
    EditText addr;

    @BindView(R.id.fcn_coin)
    EditText fcnCoin;

    @BindView(R.id.usd)
    EditText usd;

    int coinPrice;
    float totalBalance;
    int coinType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        presenter = new PaymentPresenter(this);
        presenter.loadData(this);
        presenter.getCoinInfo(0);
        coinBarClick("FNC");

        usd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().matches("")) {
                    int value = Integer.parseInt(charSequence.toString());
                    int cal = value / coinPrice;
                    if (cal < totalBalance) {
                        fcnCoin.setText(String.valueOf(cal));
                    } else {
                        PaymentActivity.super.showBasicOneBtnPopup(null, "현재 보유한 코인보다 많은양을 보낼수없습니다..")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                }else{
                    fcnCoin.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected BaseActivity<PaymentActivity> getActivityClass() {
        return PaymentActivity.this;
    }

    @OnClick({ R.id.paymennt_btn,
            R.id.fnc_coin, R.id.fnc_coin_btn,
            R.id.ltc_coin, R.id.ltc_coin_btn,
            R.id.dash_coin, R.id.dash_coin_btn,
            R.id.btc_coin, R.id.btc_coin_btn,
            R.id.bch_coin, R.id.bch_coin_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fnc_coin:
                coinBarClick("FNC");
                presenter.getCoinInfo(0);
                coinType = 0;
                break;
            case R.id.fnc_coin_btn:
                coinBarClick("FNC");
                presenter.getCoinInfo(0);
                coinType = 0;
                break;
            case R.id.ltc_coin:
                coinBarClick("LTC");
                presenter.getCoinInfo(1);
                coinType = 1;
                break;
            case R.id.ltc_coin_btn:
                coinBarClick("LTC");
                presenter.getCoinInfo(1);
                coinType = 1;
                break;
            case R.id.dash_coin:
                coinBarClick("DASH");
                presenter.getCoinInfo(2);
                coinType = 2;
                break;
            case R.id.dash_coin_btn:
                coinBarClick("DASH");
                presenter.getCoinInfo(2);
                coinType = 2;
                break;
            case R.id.btc_coin:
                coinBarClick("BTC");
                presenter.getCoinInfo(3);
                coinType = 3;
                break;
            case R.id.btc_coin_btn:
                coinBarClick("BTC");
                presenter.getCoinInfo(3);
                coinType = 3;
                break;
            case R.id.bch_coin:
                coinBarClick("BCH");
                presenter.getCoinInfo(4);
                coinType = 4;
                break;
            case R.id.bch_coin_btn:
                coinBarClick("BCH");
                presenter.getCoinInfo(4);
                coinType = 4;
                break;
            case R.id.paymennt_btn:
                if (ValidationUtil.isEmptyOfEditText(addr)) {
                    super.showBasicOneBtnPopup(null, "보낼주소를 입력하세요.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText(fcnCoin)) {
                    super.showBasicOneBtnPopup(null, "보낼 코인 수량을 입력하세요.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    presenter.doPayment(addr.getText().toString(), fcnCoin.getText().toString(), String.valueOf(coinType), usd.getText().toString());
                }
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
    public void doPaymentResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if(response.getResult() == "0"){
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fcnCoin.setText("");
                            usd.setText("");
                         /*   Log.e("HJLEE", "");
                            Log.e("HJLEE", addr.getText().toString());
                            Log.e("HJLEE", fcnCoin.getText().toString());
                            Log.e("HJLEE", String.valueOf(coinType));
                            Log.e("HJLEE", usd.getText().toString());*/
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }


       /* if(response.getResult().matches("2")){
            //아이디 비밀번호 저장
            saveUserInfo();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else{

        }*/
    }
}
