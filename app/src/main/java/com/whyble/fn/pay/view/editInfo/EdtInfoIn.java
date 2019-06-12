package com.whyble.fn.pay.view.editInfo;

import android.content.Context;

public class EdtInfoIn {

    interface View{

        void editInfoResult(String s);

        void setProfile(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void editInfo(String name);

        void getProfile();
    }
}
