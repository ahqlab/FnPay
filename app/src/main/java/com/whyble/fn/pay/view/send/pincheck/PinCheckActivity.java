package com.whyble.fn.pay.view.send.pincheck;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.whyble.fn.pay.MainActivity;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.util.ValidationUtil;
import com.whyble.fn.pay.view.send.SendActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PinCheckActivity extends BaseActivity<PinCheckActivity> implements PinCheckIn.View {

    PinCheckIn.Presenter presenter;

    @BindView(R.id.pinnumber)
    EditText pinnumber;

    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_check);
        ButterKnife.bind(this);
        presenter = new PinCheckPresenter(PinCheckActivity.this);
        presenter.loadData(PinCheckActivity.this);

        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        Log.e("HJLEE", "address : " + address);
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<PinCheckActivity> getActivityClass() {
        return PinCheckActivity.this;
    }

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (ValidationUtil.isEmptyOfEditText(pinnumber)) {
                    super.showBasicOneBtnPopup(null, "Please enter your PIN number.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.verificationPinnumber(pinnumber.getText().toString());
                }
                break;
        }
    }

    @Override
    public void doVerificationPinnumberResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if (response.getResult().matches("0")) {
            //아이디 비밀번호 저장
            Intent intent = new Intent(getApplicationContext(), SendActivity.class);
            if(address != null){
                intent.putExtra("address", address);
            }
            startActivity(intent);
            finish();
        } else {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
