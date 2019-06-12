package com.whyble.fn.pay.view.join;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
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

    @BindView(R.id.input_password_check)
    EditText passwordCheck;

    @BindView(R.id.pinnumber)
    EditText pinnumber;

    @BindView(R.id.pinnumber_check)
    EditText pinnumberCheck;

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.EditText01)
    EditText agree1;

    @BindView(R.id.accept)
    CheckBox accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        presenter = new JoinPresenter(this);
        presenter.loadData(this);

        agree1.setMovementMethod(new ScrollingMovementMethod());

       /* TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("JOIN");
        super.setToolbarColor();*/
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
                    super.showBasicOneBtnPopup(null, "Please enter e-mail.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (!isValidEmail(id.getText())) {
                    super.showBasicOneBtnPopup(null, "The email format does not match.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.input_password))) {
                    super.showBasicOneBtnPopup(null, "Please enter your password.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.input_password_check))) {
                    super.showBasicOneBtnPopup(null, "Enter confirmation password.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (!password.getText().toString().matches(passwordCheck.getText().toString())) {
                    super.showBasicOneBtnPopup(null, "Password doesn't match.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.pinnumber))) {
                    super.showBasicOneBtnPopup(null, "Please enter your PIN number.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (ValidationUtil.isEmptyOfEditText((EditText) findViewById(R.id.pinnumber_check))) {
                    super.showBasicOneBtnPopup(null, "Enter confirmation pin number.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (!pinnumber.getText().toString().matches(pinnumberCheck.getText().toString())) {
                    super.showBasicOneBtnPopup(null, "Pin numbers do not match.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else if (!name.getText().toString().matches(name.getText().toString())) {
                    super.showBasicOneBtnPopup(null, "Please enter a name.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }else if(!accept.isChecked()){
                    super.showBasicOneBtnPopup(null, "Please agree to the terms and conditions.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    presenter.signup(id.getText().toString(), password.getText().toString(), passwordCheck.getText().toString() ,pinnumber.getText().toString(), name.getText().toString());
                }
                break;
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void signupResult(String result) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(result, ServerResponse.class);
        Log.e("HJLEE", response.toString());
        if(response.getResult().matches("0")){
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }else if(response.getResult().matches("1")){
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(response.getResult().matches("3")){
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("HJLEE","asdasdasd");
                            Log.e("HJLEE","asdasdasd");
                            password.setText("");
                            passwordCheck.setText("");
                            password.post(new Runnable() {
                                @Override
                                public void run() {
                                    password.setFocusableInTouchMode(true);
                                    password.requestFocus();
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(password,0);

                                }
                            });
                            dialog.dismiss();

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
