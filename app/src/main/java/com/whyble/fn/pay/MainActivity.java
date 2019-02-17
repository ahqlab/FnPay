package com.whyble.fn.pay;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.CoinInfo;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.view.editInfo.EdtInfoActivity;
import com.whyble.fn.pay.view.exchange.ExchangeActivity;
import com.whyble.fn.pay.view.history.HistoryActivity;
import com.whyble.fn.pay.view.login.LoginActivity;
import com.whyble.fn.pay.view.payment.PaymentActivity;
import com.whyble.fn.pay.view.receive.ReceiveActivity;
import com.whyble.fn.pay.view.send.SendActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainActivity> implements NavigationView.OnNavigationItemSelectedListener, MainIn.View {

    MainIn.Presenter presenter;

    @BindView(R.id.coin_title)
    TextView coinTitle;
    @BindView(R.id.balance)
    TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
        presenter.loadData(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter.getCoinInfo(0);
        coinBarClick("FNC");
    }

    @Override
    protected BaseActivity<MainActivity> getActivityClass() {
        return MainActivity.this;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.logout) {
            mSharedPrefManager.removeAllPreferences();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else if (id == R.id.edit_user_info) {
            startActivity(new Intent(getApplicationContext(), EdtInfoActivity.class));
        } else if (id == R.id.payment) {
            startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
        } else if (id == R.id.exchange) {
            startActivity(new Intent(getApplicationContext(), ExchangeActivity.class));
        } else if (id == R.id.share) {
            Intent msg = new Intent(Intent.ACTION_SEND);
            msg.addCategory(Intent.CATEGORY_DEFAULT);
            msg.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            msg.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
            msg.putExtra(Intent.EXTRA_TITLE, R.string.app_name);
            msg.setType("text/plain");
            startActivity(Intent.createChooser(msg, "Share"));
        } else if (id == R.id.history) {
            startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
        } else if (id == R.id.send) {
            startActivity(new Intent(getApplicationContext(), SendActivity.class));
        } else if (id == R.id.receive) {
            startActivity(new Intent(getApplicationContext(), ReceiveActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @OnClick({R.id.btn_payment, R.id.btn_exchange, R.id.btn_send, R.id.btn_receive, R.id.btn_share, R.id.btn_edit_info, R.id.history_btn,
            R.id.fnc_coin, R.id.fnc_coin_btn,
            R.id.ltc_coin, R.id.ltc_coin_btn,
            R.id.dash_coin, R.id.dash_coin_btn,
            R.id.btc_coin, R.id.btc_coin_btn,
            R.id.bch_coin, R.id.bch_coin_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_exchange:
                startActivity(new Intent(getApplicationContext(), ExchangeActivity.class));
                break;
            case R.id.btn_payment:
                startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                break;
            case R.id.btn_send:
                startActivity(new Intent(getApplicationContext(), SendActivity.class));
                break;
            case R.id.btn_receive:
                startActivity(new Intent(getApplicationContext(), ReceiveActivity.class));
                break;
            case R.id.btn_edit_info:
                startActivity(new Intent(getApplicationContext(), EdtInfoActivity.class));
                break;
            case R.id.history_btn:
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                break;
            case R.id.fnc_coin:
                coinBarClick("FNC");
                presenter.getCoinInfo(0);
                break;
            case R.id.fnc_coin_btn:
                coinBarClick("FNC");
                presenter.getCoinInfo(0);
                break;
            case R.id.ltc_coin:
                coinBarClick("LTC");
                presenter.getCoinInfo(1);
                break;
            case R.id.ltc_coin_btn:
                coinBarClick("LTC");
                presenter.getCoinInfo(1);
                break;
            case R.id.dash_coin:
                coinBarClick("DASH");
                presenter.getCoinInfo(2);
                break;
            case R.id.dash_coin_btn:
                coinBarClick("DASH");
                presenter.getCoinInfo(2);
                break;
            case R.id.btc_coin:
                coinBarClick("BTC");
                presenter.getCoinInfo(3);
                break;
            case R.id.btc_coin_btn:
                coinBarClick("BTC");
                presenter.getCoinInfo(3);
                break;
            case R.id.bch_coin:
                coinBarClick("BCH");
                presenter.getCoinInfo(4);
                break;
            case R.id.bch_coin_btn:
                coinBarClick("BCH");
                presenter.getCoinInfo(4);
                break;
            case R.id.btn_share:
                Intent msg = new Intent(Intent.ACTION_SEND);
                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                msg.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                msg.putExtra(Intent.EXTRA_TITLE, R.string.app_name);
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "Share"));
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
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.press_back_message), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void getCoinInfo(String s) {
        Gson gson = new Gson();
        CoinInfo response = gson.fromJson(s, CoinInfo.class);
        coinTitle.setText(response.getCoin_title());
        balance.setText(response.getBalance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

}
