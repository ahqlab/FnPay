package com.whyble.fn.pay.view.receive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.whyble.fn.pay.R;

import butterknife.ButterKnife;

public class ReceiveActivity extends AppCompatActivity {

    Spinner spinner;

    ArrayAdapter<CharSequence> adspin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        ButterKnife.bind(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        setSpinner();
    }

    private void setSpinner() {
        adspin = ArrayAdapter.createFromResource(this, R.array.coins , android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
