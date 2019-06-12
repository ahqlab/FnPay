package com.whyble.fn.pay.view.send;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.whyble.fn.pay.MainActivity;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.CoinInfo;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.util.ValidationUtil;
import com.whyble.fn.pay.view.payment.PaymentActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendActivity extends BaseActivity<SendActivity> implements SendIn.View {

    @BindView(R.id.balance)
    TextView balance;

    @BindView(R.id.amount)
    EditText amount;

    @BindView(R.id.addr)
    EditText addr;

    SendIn.Presenter presenter;

    public String qRaddr;
    public String qRlength;
    public String qRusd;
    public String qRorderno = null;

    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);
        presenter = new SendPresenter(this);
        presenter.loadData(this);
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("SEND");
        super.setToolbarColor();

    }

    @Override
    protected BaseActivity<SendActivity> getActivityClass() {
        return SendActivity.this;
    }

    @Override
    public void setCoinInfo(String s) {
        Gson gson = new Gson();
        CoinInfo response = gson.fromJson(s, CoinInfo.class);
        balance.setText(response.getBalance());
        if(address != null){
            addr.setText(address);
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
    public void sendCoinResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        super.showBasicOneBtnPopup(null, response.getMsg())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        amount.setText("");
                        presenter.getCoinInfo();
                        //presenter.getCoinInfo(coinType);
                    }
                }).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
               // Toast.makeText(SendActivity.this, "취소됨", Toast.LENGTH_LONG).show();
            } else {
                if(result.getContents().indexOf("/") > -1){
                    Log.e("HJLEE", "1 문자열 있음.");
                    String[] array = result.getContents().split("/");
                    qRaddr  = array[0];
                    qRlength  = array[1];
                    qRusd  = array[2];
                    qRorderno  = array[3];
                    addr.setText(qRaddr);
                }else{
                    Log.e("HJLEE", "1 문자열 없음.");
                    addr.setText(result.getContents());
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void openCamera() {

        IntentIntegrator integrator = new IntentIntegrator(SendActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    private void requestCamera (){
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        if (permissionCamera == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SendActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
//                  Toast.makeText(getApplicationContext(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
            openCamera();
        }
    }

    @OnClick({R.id.submit, R.id.qr_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.addr))) {
                    super.showBasicOneBtnPopup(null, "Please enter the address to be sent.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.amount))) {
                    super.showBasicOneBtnPopup(null, "Please enter the quantity of coin to be sent.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.sendCoin(addr.getText().toString(), amount.getText().toString());
                }
                break;
            case R.id.qr_btn:
                requestCamera();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCoinInfo();
    }
}
