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
import com.whyble.fn.pay.view.addressBook.AddressBookActivity;
import com.whyble.fn.pay.view.change.password.ChangePasswordActivity;
import com.whyble.fn.pay.view.change.pin.ChangePinActivity;
import com.whyble.fn.pay.view.editInfo.EdtInfoActivity;
import com.whyble.fn.pay.view.exchange.ExchangeActivity;
import com.whyble.fn.pay.view.history.HistoryActivity;
import com.whyble.fn.pay.view.login.LoginActivity;
import com.whyble.fn.pay.view.payment.PaymentActivity;
import com.whyble.fn.pay.view.payment.list.PaymentListActivity;
import com.whyble.fn.pay.view.receive.ReceiveActivity;
import com.whyble.fn.pay.view.send.SendActivity;
import com.whyble.fn.pay.view.send.pincheck.PinCheckActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainActivity> implements NavigationView.OnNavigationItemSelectedListener, MainIn.View {

    MainIn.Presenter presenter;

    @BindView(R.id.balance1)
    TextView balance1;

    @BindView(R.id.balance2)
    TextView balance2;

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



    }

    @Override
    protected BaseActivity<MainActivity> getActivityClass() {
        return MainActivity.this;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.dash_board){
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else if (id == R.id.send) {
            startActivity(new Intent(getApplicationContext(), PinCheckActivity.class));
        }else if (id == R.id.receive) {
            startActivity(new Intent(getApplicationContext(), ReceiveActivity.class));
        }else if (id == R.id.payment) {
            startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
        }else if (id == R.id.history) {
            startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
        }else if (id == R.id.paymentList) {
            startActivity(new Intent(getApplicationContext(), PaymentListActivity.class));
        }else if (id == R.id.profile) {
            startActivity(new Intent(getApplicationContext(), EdtInfoActivity.class));
        }else if (id == R.id.changePassword) {
            startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
        }else if (id == R.id.pincodeUpdate) {
            startActivity(new Intent(getApplicationContext(), ChangePinActivity.class));
        }else if (id == R.id.addressBook) {
            startActivity(new Intent(getApplicationContext(), AddressBookActivity.class));
        }else if (id == R.id.logout) {
            mSharedPrefManager.removeAllPreferences();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @OnClick({R.id.btn_payment,  R.id.btn_send, R.id.btn_receive, R.id.history_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_payment:
                startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                break;
            case R.id.btn_send:
                startActivity(new Intent(getApplicationContext(), PinCheckActivity.class));
                break;
            case R.id.btn_receive:
                startActivity(new Intent(getApplicationContext(), ReceiveActivity.class));
                break;
            case R.id.history_btn:
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                break;

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
    public void setCoinInfo(String s) {
        Gson gson = new Gson();
        CoinInfo response = gson.fromJson(s, CoinInfo.class);
        String[] array = response.getBalance().split("\\.");
        //coinTitle.setText(response.getCoin_title());
        balance1.setText(array[0]);
        balance2.setText(array[1]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCoinInfo();
    }
}
