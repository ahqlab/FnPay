package com.whyble.fn.pay.view.payment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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

    @BindView(R.id.amount)
    EditText amount;
    
    @BindView(R.id.balance)
    EditText balance;

    @BindView(R.id.addr)
    EditText addr;

    String qRaddr;
    String qRlength;
    String qRusd;
    String qRorderno = null;

    CoinInfo response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("PAYMENT");
        super.setToolbarColor();

        presenter = new PaymentPresenter(this);
        presenter.loadData(this);


        amount.addTextChangedListener(new TextWatcher() {

            String beforeText;

            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {
                beforeText = cs.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    float value = Float.parseFloat(charSequence.toString());
                    if (value < Float.parseFloat(response.getBalance())) {
                        //amount.setText(String.valueOf(value));
                    } else {
                        amount.setText(beforeText);
                        PaymentActivity.super.showBasicOneBtnPopup(null, "You cannot send more coins than you currently own.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
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

    @OnClick({R.id.submit, R.id.qr_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (ValidationUtil.isEmptyOfEditText(addr)) {
                    super.showBasicOneBtnPopup(null, "Please enter the address to be sent.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText(amount)) {
                    super.showBasicOneBtnPopup(null, "Please enter the quantity of coin to be sent.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.doPayment(addr.getText().toString(), amount.getText().toString() , qRorderno);
                }
                break;
            case R.id.qr_btn:
                requestCamera();
                break;
        }
    }

    private void requestCamera(){
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        if (permissionCamera == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(PaymentActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
//                            Toast.makeText(getApplicationContext(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
            openCamera();
        }
    }

    private void openCamera() {
       /* IntentIntegrator integrator = new IntentIntegrator(PaymentActivity.this);
        integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setScanningRectangle(800, 800);
        integrator.setPrompt(getString(R.string.qr_message));
        integrator.initiateScan();*/



        IntentIntegrator integrator = new IntentIntegrator(PaymentActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
               // Toast.makeText(PaymentActivity.this, "", Toast.LENGTH_LONG).show();
            } else {
                Log.e("HJLEE", "QR 문자열 " + result.getContents());
                //QR 문자열 NQAJ2tQyapCBz4JGwnTFFJFCy7YPbSktSu/금액/코인수량/ordernum
                if(result.getContents().indexOf("/") > -1){
                    //Log.e("HJLEE", "1 문자열 있음.");

                    String[] array = result.getContents().split("/");
                    qRaddr  = array[0];
                    qRlength  = array[1];
                    qRusd  = array[2];
                    qRorderno  = array[3];

                    addr.setText(qRaddr);
                    amount.setText(qRusd);
                    //fcnCoin.setText(qRlength);
                    //usd.setText(qRusd);
                }else{
                    //Log.e("HJLEE", "1 문자열 없음.");
                    addr.setText(result.getContents());
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            openCamera();
                        } else {
                            Toast.makeText(getApplicationContext(), "CAMERA permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }




    @Override
    public void setCoinInfo(String s) {
        Gson gson = new Gson();
        response = gson.fromJson(s, CoinInfo.class);
        //amount.setText(response.getCoin_title());
        balance.setText(response.getBalance());
       // coinPrice = Integer.parseInt(response.getCoin_price());
       // totalBalance = Float.parseFloat(response.getBalance());
    }

    @Override
    public void doPaymentResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        //Log.e("HJLEE", "responcr :" + response.toString());
        if (response.getResult().matches("0")) {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //fcnCoin.setText("");
                            //usd.setText("");
                           //presenter.getCoinInfo(coinType);
                         /*   Log.e("HJLEE", "");
                            Log.e("HJLEE", addr.getText().toString());
                            Log.e("HJLEE", fcnCoin.getText().toString());
                            Log.e("HJLEE", String.valueOf(coinType));
                            Log.e("HJLEE", usd.getText().toString());*/
                            dialog.dismiss();
                            presenter.getCoinInfo();
                        }
                    }).show();
        } else {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // fcnCoin.setText("");
                           // usd.setText("");
                           //presenter.getCoinInfo(coinType);
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

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCoinInfo();
    }
}
