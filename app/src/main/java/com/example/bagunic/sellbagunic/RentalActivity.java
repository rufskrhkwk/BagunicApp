package com.example.bagunic.sellbagunic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bagunic.R;

public class RentalActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Button paybutton;
    TextView basketname, basketprice;
    CheckBox checkBox1, checkBox2, checkBox3;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);
        spinner = (Spinner) findViewById(R.id.spinner);
        paybutton = findViewById(R.id.paybutton);
        basketname = findViewById(R.id.basketname);
        basketprice = findViewById(R.id.basketprice);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);

        checkBox1.setOnCheckedChangeListener(this);
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3.setOnCheckedChangeListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = basketname.getText().toString();
                String price = basketprice.getText().toString();
                String text = spinner.getSelectedItem().toString();


                PayActivity payActivity = new PayActivity(name, price,text);
                Intent intent = new Intent(getApplicationContext(), payActivity.getClass());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int result = 19000;///19000
        if (checkBox1.isChecked()) result += 3000;//3000
        if (checkBox2.isChecked()) result += 5000;//5000
        if (checkBox3.isChecked()) result += 1000;//1000
        basketprice.setText("가격 : " + result + "원");

    }


}