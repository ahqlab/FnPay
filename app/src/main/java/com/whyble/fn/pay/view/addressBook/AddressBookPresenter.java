package com.whyble.fn.pay.view.addressBook;

import android.content.Context;

import com.whyble.fn.pay.common.CommonModel;

public class AddressBookPresenter implements AddressBookIn.Presenter {

    AddressBookIn.View view;

    AddressBookModel model;

    public AddressBookPresenter(AddressBookIn.View view) {
        this.view = view;
        this.model = new AddressBookModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getAddressList() {
        model.getAddress(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setAddressList(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void doDeleteAddress(String a_no) {
        model.doDeleteAddress(a_no, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setDeleteResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }


    /*@Override
    public void getTransaction() {
        model.getSend(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setSend(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }*/

}
