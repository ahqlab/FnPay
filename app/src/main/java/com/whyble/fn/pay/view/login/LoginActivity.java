package com.whyble.fn.pay.view.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.whyble.fn.pay.MainActivity;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.SharedPrefManager;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.util.TextManager.TextManager;
import com.whyble.fn.pay.util.ValidationUtil;
import com.whyble.fn.pay.view.join.JoinActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginActivity> implements LoginIn.View {

    LoginIn.Presenter presenter;

    @BindView(R.id.input_id)
    EditText inputId;

    @BindView(R.id.input_password)
    EditText inputPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        presenter.loadData(this);
        if(!mSharedPrefManager.getStringExtra(TextManager.VALID_USER).matches("")){
            inputId.setText(mSharedPrefManager.getStringExtra(TextManager.VALID_USER));
            autoLogin();
        }
    }

    @Override
    protected BaseActivity<LoginActivity> getActivityClass() {
        return LoginActivity.this;
    }

    @OnClick({R.id.login_btn, R.id.join_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (ValidationUtil.isEmptyOfEditText(inputId)) {
                    super.showBasicOneBtnPopup(null, "아이디를 입력하세요")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText(inputPass)) {
                    super.showBasicOneBtnPopup(null, "비밀번호를 입력하세요.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.login(inputId.getText().toString(), inputPass.getText().toString());
                }
                break;
            case R.id.join_btn:
                startActivity(new Intent(getApplicationContext(), JoinActivity.class));
                break;
        }
    }

    public void autoLogin(){
        String id = mSharedPrefManager.getStringExtra(TextManager.VALID_USER);
        String passwd = mSharedPrefManager.getStringExtra(TextManager.PASSWD);
        if(!id.matches("")){
            presenter.login(id, passwd);
        }
    }

    @Override
    public void setLoginResult(String s) {

        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if (response.getResult().matches("0")) {
            //아이디 비밀번호 저장
            saveUserInfo();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    private void saveUserInfo() {
        mSharedPrefManager.putStringExtra(TextManager.VALID_USER, inputId.getText().toString());
        mSharedPrefManager.putStringExtra(TextManager.PASSWD, inputPass.getText().toString());
    }
}
