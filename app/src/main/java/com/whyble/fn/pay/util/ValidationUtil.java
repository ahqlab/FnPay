package com.whyble.fn.pay.util;

import android.content.Context;
import android.widget.EditText;

import com.whyble.fn.pay.R;

public class ValidationUtil {

    Context context;

    public static boolean isEmptyOfEditText(EditText editText){
        if(editText.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

}
