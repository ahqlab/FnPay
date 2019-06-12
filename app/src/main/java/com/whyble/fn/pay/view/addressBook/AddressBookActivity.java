package com.whyble.fn.pay.view.addressBook;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whyble.fn.pay.R;
import com.whyble.fn.pay.common.adapter.AbsractCommonAdapter;
import com.whyble.fn.pay.common.base.BaseActivity;
import com.whyble.fn.pay.databinding.ActivityAddressBookBinding;
import com.whyble.fn.pay.databinding.AddressBookListviewItemBinding;
import com.whyble.fn.pay.databinding.SendHistoryListviewItemBinding;
import com.whyble.fn.pay.domain.AddressBook;
import com.whyble.fn.pay.domain.AddressBookList;
import com.whyble.fn.pay.domain.Send;
import com.whyble.fn.pay.domain.SendList;
import com.whyble.fn.pay.domain.ServerResponse;
import com.whyble.fn.pay.util.device.DeviceUtils;
import com.whyble.fn.pay.view.history.HistoryActivity;
import com.whyble.fn.pay.view.send.pincheck.PinCheckActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressBookActivity extends BaseActivity<AddressBookActivity> implements AddressBookIn.View{

    ActivityAddressBookBinding binding;

    AddressBookIn.Presenter presenter;

    AbsractCommonAdapter<AddressBook> sAdapter;

    @BindView(R.id.noHistory)
    LinearLayout noHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        ButterKnife.bind(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_book);
        binding.setActivity(AddressBookActivity.this);
        presenter = new AddressBookPresenter(AddressBookActivity.this);
        presenter.loadData(AddressBookActivity.this);
        presenter.getAddressList();
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Address Book");
        super.setToolbarColor();

    }

    @Override
    protected BaseActivity<AddressBookActivity> getActivityClass() {
        return AddressBookActivity.this;
    }

    @Override
    public void setAddressList(String s) {
        Gson gson = new Gson();
        AddressBookList send = gson.fromJson(s, AddressBookList.class);
        Log.e("HJLEE", ">>" + send.getList().size());
        if(send.getList().size() > 0){
            binding.listview.setVisibility(View.VISIBLE);
            noHistory.setVisibility(View.GONE);
            sAdapter = new AbsractCommonAdapter<AddressBook>(AddressBookActivity.this, send.getList()) {

                AddressBookListviewItemBinding adapterBinding;
                @Override
                protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = sAdapter.inflater.inflate(R.layout.address_book_listview_item, null);
                        adapterBinding = DataBindingUtil.bind(convertView);
                        adapterBinding.setDomain(sAdapter.data.get(position));
                        convertView.setTag(adapterBinding);
                    } else {
                        adapterBinding = (AddressBookListviewItemBinding) convertView.getTag();
                        adapterBinding.setDomain(sAdapter.data.get(position));
                    }
                    adapterBinding.deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.doDeleteAddress(sAdapter.data.get(position).getA_no());
                        }
                    });

                    adapterBinding.copyBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DeviceUtils.setClipBoardLink(AddressBookActivity.this, sAdapter.data.get(position).getA_sendaddr());
                        }
                    });
                    adapterBinding.requestBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), PinCheckActivity.class);
                            intent.putExtra("address", sAdapter.data.get(position).getA_sendaddr());
                            startActivity(intent);
                            finish();
                        }
                    });
                    return adapterBinding.getRoot();
                }
            };
            binding.listview.setAdapter(sAdapter);

        }else{
            binding.listview.setVisibility(View.GONE);
            noHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setDeleteResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if(response.getResult().matches("1")){
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            presenter.getAddressList();
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
