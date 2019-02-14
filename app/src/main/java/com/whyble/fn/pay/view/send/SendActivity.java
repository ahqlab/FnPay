package com.whyble.fn.pay.view.send;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whyble.fn.pay.MainActivity;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.CoinInfo;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.util.ValidationUtil;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendActivity extends BaseActivity<SendActivity> implements SendIn.View {

    @BindView(R.id.coin_title)
    TextView coinTitle;
    @BindView(R.id.s_coin_title)
    TextView sCoinTitle;
    @BindView(R.id.balance)
    TextView balance;

    @BindView(R.id.coin_length)
    EditText coinLength;
    @BindView(R.id.to)
    EditText to;

    @BindView(R.id.page_title)
    TextView pageTitle;

    SendIn.Presenter presenter;

    int coinType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);
        presenter = new SendPresenter(this);
        presenter.loadData(this);

        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("SEND");
        super.setToolbarColor();

        coinBarClick("FNC");
        coinLength.setHint("FNC");
        presenter.getCoinInfo(0);
        coinType = 0;

    }

    @Override
    protected BaseActivity<SendActivity> getActivityClass() {
        return SendActivity.this;
    }

    @Override
    public void setCoinInfo(String s) {
        Gson gson = new Gson();
        CoinInfo response = gson.fromJson(s, CoinInfo.class);
        coinTitle.setText(response.getCoin_title());
        sCoinTitle.setText(response.getCoin_title());
        balance.setText(response.getBalance());
    }

    @Override
    public void sendCoinResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        super.showBasicOneBtnPopup(null, response.getMsg())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        coinLength.setText("");
                    }
                }).show();

    }


    @OnClick({R.id.submit,
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
                coinLength.setHint("FNC");
                presenter.getCoinInfo(0);
                coinType = 0;
                break;
            case R.id.fnc_coin_btn:
                coinBarClick("FNC");
                coinLength.setHint("FNC");
                presenter.getCoinInfo(0);
                coinType = 0;
                break;
            case R.id.ltc_coin:
                coinBarClick("LTC");
                coinLength.setHint("LTC");
                presenter.getCoinInfo(1);
                coinType = 1;
                break;
            case R.id.ltc_coin_btn:
                coinBarClick("LTC");
                coinLength.setHint("LTC");
                presenter.getCoinInfo(1);
                coinType = 1;
                break;
            case R.id.dash_coin:
                coinBarClick("DASH");
                coinLength.setHint("DASH");
                presenter.getCoinInfo(2);
                coinType = 2;
                break;
            case R.id.dash_coin_btn:
                coinBarClick("DASH");
                coinLength.setHint("DASH");
                presenter.getCoinInfo(2);
                coinType = 2;
                break;
            case R.id.btc_coin:
                coinBarClick("BTC");
                coinLength.setHint("BTC");
                presenter.getCoinInfo(3);
                coinType = 3;
                break;
            case R.id.btc_coin_btn:
                coinBarClick("BTC");
                coinLength.setHint("BTC");
                presenter.getCoinInfo(3);
                coinType = 3;
                break;
            case R.id.bch_coin:
                coinBarClick("BCH");
                coinLength.setHint("BCH");
                presenter.getCoinInfo(4);
                coinType = 4;
                break;
            case R.id.bch_coin_btn:
                coinBarClick("BCH");
                coinLength.setHint("BCH");
                presenter.getCoinInfo(4);
                coinType = 4;
                break;
            case R.id.submit:
                if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.to))) {
                    super.showBasicOneBtnPopup(null, "보낼주소를 입력하세요.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.coin_length))) {
                    super.showBasicOneBtnPopup(null, "보낼 코인 수량을 입력하세요.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.sendCoin(to.getText().toString(), coinLength.getText().toString(), String.valueOf(coinType));
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
}
