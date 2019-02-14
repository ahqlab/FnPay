package com.whyble.fn.pay.view.join;

import android.content.DialogInterface;
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

public class JoinActivity extends BaseActivity<JoinActivity> implements JoinIn.View{

    JoinIn.Presenter presenter;

    @BindView(R.id.input_id)
    EditText id;
    @BindView(R.id.input_password)
    EditText password;
    @BindView(R.id.pinnumber)
    EditText pinnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        presenter = new JoinPresenter(this);
        presenter.loadData(this);

        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("JOIN");
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<JoinActivity> getActivityClass() {
        return JoinActivity.this;
    }

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:

                if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.input_id))) {
                    super.showBasicOneBtnPopup(null, "아이디를 입력하세요")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.input_password))) {
                    super.showBasicOneBtnPopup(null, "비밀번호를 입력하세요.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.pinnumber))) {
                    super.showBasicOneBtnPopup(null, "핀번호를 입력하세요.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.signup(id.getText().toString(), password.getText().toString(), pinnumber.getText().toString());
                }
                break;
        }
    }

    @Override
    public void signupResult(String result) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(result, ServerResponse.class);
        if(response.getResult() == "0"){
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
