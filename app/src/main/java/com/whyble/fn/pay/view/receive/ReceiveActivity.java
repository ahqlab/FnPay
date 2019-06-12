package com.whyble.fn.pay.view.receive;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.whyble.fn.pay.databinding.LayoutNumberKeyboardBinding;
import com.whyble.fn.pay.domain.CoinInfo;
import com.whyble.fn.pay.util.device.DeviceUtils;
import com.whyble.fn.pay.view.payment.PaymentActivity;
import com.whyble.fn.pay.view.receive.request.RequestAnAmountActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiveActivity extends BaseActivity<ReceiveActivity> implements ReceiveIn.View {

    // Spinner spinner;

    //ArrayAdapter<CharSequence> adspin;

    ReceiveIn.Presenter presenter;

    @BindView(R.id.addr)
    TextView addr;

    @BindView(R.id.qrcode)
    ImageView qrcode;

    private Dialog dialog;

    private boolean state;

    CoinInfo response;

    private final String suffixStr = "kg";

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
        //spinner = (Spinner) findViewById(R.id.spinner);
        //setSpinner();
        presenter.getCoinInfo();
    }

    @Override
    protected BaseActivity<ReceiveActivity> getActivityClass() {
        return ReceiveActivity.this;
    }

    /*private void setSpinner() {
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
    }*/

    @Override
    public void setCoinInfo(String s) {
        Gson gson = new Gson();
        response = gson.fromJson(s, CoinInfo.class);
        Picasso.with(ReceiveActivity.this)
                .load(response.getQr())
                .into(qrcode);
        addr.setText(response.getAddress());

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.tabToCopy
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tabToCopy:
                DeviceUtils.setClipBoardLink(ReceiveActivity.this, addr.getText().toString());
                break;
            /*case R.id.request:
                state = false;

                final LayoutNumberKeyboardBinding keyboardBinding = DataBindingUtil.inflate(LayoutInflater.from(ReceiveActivity.this), R.layout.layout_number_keyboard, null, false);
                keyboardBinding.setActivity(ReceiveActivity.this);
                keyboardBinding.setState(false);
                keyboardBinding.setWeight("0");

                Toolbar toolbar = keyboardBinding.myToolbar;
                toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Picasso.with(ReceiveActivity.this)
                        .load(response.getQr())
                        .into(keyboardBinding.qrcode);
                keyboardBinding.addr.setText(response.getAddress());

                dialog = new Dialog(ReceiveActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialog.setContentView(keyboardBinding.getRoot());
                dialog.create();
                dialog.show();

                for (int i = 0; i < keyboardBinding.numericKeybord.getChildCount(); i++) {
                    final int position = i;
                    keyboardBinding.numericKeybord.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("HJLEE", "keyboardBinding.kilogram.getText().toString() : " + keyboardBinding.kilogram.getText().toString());

                                String kilogram = keyboardBinding.kilogram.getText().toString();
                                Log.e("HJLEE", "kilogram : " + kilogram);
                                //kilogram = kilogram.replaceAll(suffixStr, "");
                                //keyboardBinding.kilogram.getText().toString();
                                float kilogramInt;
                                if (kilogram != "") {
                                    kilogramInt = Float.parseFloat(kilogram);
                                }else{
                                    kilogramInt = Float.parseFloat(kilogram);
                                }


                                if (view instanceof TextView) {
                                    TextView textView = (TextView) view;
                                    Log.e("HJLEE", ">>> " + textView.getText().toString());
                                    if(!textView.getText().toString().matches("") || textView.getText().toString() != null){
                                        if (kilogramInt == 0) {
                                            kilogram = textView.getText().toString();
                                        } else {
                                            kilogram += textView.getText().toString();
                                        }
                                    }

                                    state = true;
                                } else if (view instanceof ImageView) {
                                    kilogram = kilogram.substring(0, kilogram.length() - 1);
                                    if (kilogram.indexOf(".") > -1) {
                                        String[] splitStr = kilogram.split("\\.");
                                        if (splitStr.length == 1) {
                                            kilogram = splitStr[0];
                                        }
                                    }
                                    if (kilogram.equals("")) {
                                        kilogram = "0";
                                        state = false;
                                    }
                                }
                                //info.setWeight(kilogram);
                                keyboardBinding.setState(state);
                                keyboardBinding.kilogram.setText(kilogram);
                                if (0 == kilogramInt) {
                                    Picasso.with(ReceiveActivity.this)
                                            .load(response.getQr())
                                            .into(keyboardBinding.qrcode);
                                } else {
                                    Picasso.with(ReceiveActivity.this)
                                            .load("https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=" + response.getAddress() + "/" + kilogram + "&choe=UTF-8")
                                            .into(keyboardBinding.qrcode);
                                }
                            }
                    });

                }
//                keyboardBinding.ㅇ.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Log.e("test", "ㅇ 클릭");
//                    }
//                });

                keyboardBinding.doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeviceUtils.setClipBoardLink(ReceiveActivity.this, keyboardBinding.addr.getText().toString());
                    }
                });
                break;*/
        }
    }
}
