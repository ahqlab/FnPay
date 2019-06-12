package com.whyble.fn.pay.view.addressBook;

import android.content.Context;

public class AddressBookIn {

    interface View{
        void setAddressList(String s);

        void setDeleteResult(String s);

        /*void setSend(String s);*/
    }
    interface Presenter{
        void loadData(Context context);

        void getAddressList();

        void doDeleteAddress(String a_no);

        /*void getTransaction();*/
    }
}
