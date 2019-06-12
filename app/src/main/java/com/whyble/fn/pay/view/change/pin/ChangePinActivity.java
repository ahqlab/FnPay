package com.whyble.fn.pay.view.change.pin;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.util.ValidationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePinActivity extends BaseActivity<ChangePinActivity> implements ChangePinIn.View{

    ChangePinIn.Presenter presenter;

    @BindView(R.id.prePincode)
    EditText prePincode;

    @BindView(R.id.newPincode)
    EditText newPincode;

    @BindView(R.id.repeatPincode)
    EditText repeatPincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
        ButterKnife.bind(this);
        presenter = new ChangePinPresenter(ChangePinActivity.this);
        presenter.loadData(ChangePinActivity.this);
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Pincode update");
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<ChangePinActivity> getActivityClass() {
        return ChangePinActivity.this;
    }

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.prePincode))) {
                    super.showBasicOneBtnPopup(null, "Enter pin number.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.newPincode))) {
                    super.showBasicOneBtnPopup(null, "Please enter a new pin number.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.repeatPincode))) {
                    super.showBasicOneBtnPopup(null, "Enter confirmation pin number.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (!newPincode.getText().toString().matches(repeatPincode.getText().toString())) {
                    super.showBasicOneBtnPopup(null, "Pin numbers do not match.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.setChangePin(prePincode.getText().toString(), newPincode.getText().toString());
                }
                break;
        }
    }


    @Override
    public void setChangeResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if(response.getResult().matches("3")){
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
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
    }
}
