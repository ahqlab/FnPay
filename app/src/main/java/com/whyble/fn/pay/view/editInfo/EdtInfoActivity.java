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
import com.whyble.fn.pay.common.SharedPrefManager;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.domain.Payment;
import com.whyble.fn.pay.domain.Profile;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.util.TextManager.TextManager;
import com.whyble.fn.pay.util.ValidationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EdtInfoActivity extends BaseActivity<EdtInfoActivity> implements EdtInfoIn.View{

    EdtInfoIn.Presenter presenter;

    @BindView(R.id.id)
    EditText id;

    @BindView(R.id.name)
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_info);
        ButterKnife.bind(this);
        presenter = new EdtInfoPresenter(this);
        presenter.loadData(this);
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Profile");
        super.setToolbarColor();
        presenter.getProfile();
        id.setText(mSharedPrefManager.getStringExtra(TextManager.VALID_USER));

    }

    @Override
    protected BaseActivity<EdtInfoActivity> getActivityClass() {
        return EdtInfoActivity.this;
    }

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.name))) {
                    super.showBasicOneBtnPopup(null, "Please enter a name.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.editInfo(name.getText().toString());
                }
                break;
        }
    }

    @Override
    public void editInfoResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if(response.getResult().matches("2")){
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

    @Override
    public void setProfile(String s) {
        Gson gson = new Gson();
        Profile profile = gson.fromJson(s, Profile.class);
        name.setText(profile.getUsername());
    }
}
