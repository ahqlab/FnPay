package com.whyble.fn.pay.view.editInfo;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class EdtInfoActivity extends BaseActivity<EdtInfoActivity> implements EdtInfoIn.View{

    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.new_password)
    EditText newPassword;

    EdtInfoIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_info);
        ButterKnife.bind(this);
        presenter = new EdtInfoPresenter(this);
        presenter.loadData(this);


        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("정보수정");
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<EdtInfoActivity> getActivityClass() {
        return EdtInfoActivity.this;
    }

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:

                if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.old_password))) {
                    super.showBasicOneBtnPopup(null, "아이디를 입력하세요")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.new_password))) {
                    super.showBasicOneBtnPopup(null, "비밀번호를 입력하세요.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.editInfo(oldPassword.getText().toString(), newPassword.getText().toString());
                }
                break;
        }
    }

    @Override
    public void editInfoResult(String s) {
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
