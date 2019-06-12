package com.whyble.fn.pay.view.change.password;

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
import com.whyble.fn.pay.util.TextManager.TextManager;
import com.whyble.fn.pay.util.ValidationUtil;
import com.whyble.fn.pay.view.change.pin.ChangePinIn;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordActivity> implements ChangePasswordIn.View {

    ChangePasswordIn.Presenter presenter;

    @BindView(R.id.prePassword)
    EditText prePassword;

    @BindView(R.id.newPassword)
    EditText newPassword;

    @BindView(R.id.repeatPassword)
    EditText repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        presenter = new ChangePasswordPresenter(ChangePasswordActivity.this);
        presenter.loadData(ChangePasswordActivity.this);
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Change password");
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<ChangePasswordActivity> getActivityClass() {
        return ChangePasswordActivity.this;
    }

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.prePassword))) {
                    super.showBasicOneBtnPopup(null, "Enter a password.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.newPassword))) {
                    super.showBasicOneBtnPopup(null, "Please enter a new password.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.repeatPassword))) {
                    super.showBasicOneBtnPopup(null, "Enter confirmation password.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (!newPassword.getText().toString().matches(repeatPassword.getText().toString())) {
                    super.showBasicOneBtnPopup(null, "Password do not match.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.doChangePassword(prePassword.getText().toString(), newPassword.getText().toString());
                }
                break;
        }
    }

    @Override
    public void setChangeInfo(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if(response.getResult().matches("0")){
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
