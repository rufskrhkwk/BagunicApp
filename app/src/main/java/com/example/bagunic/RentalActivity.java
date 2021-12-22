package com.example.bagunic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class RentalActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
Button paybutton;
TextView basketname,basketprice;
CheckBox checkBox1,checkBox2,checkBox3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);
        paybutton = findViewById(R.id.paybutton);
        basketname = findViewById(R.id.basketname);
        basketprice = findViewById(R.id.basketprice);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);

        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = basketname.getText().toString();
                String price = basketprice.getText().toString();

                PayActivity payActivity = new PayActivity(name, price);
                Intent intent = new Intent(getApplicationContext(),payActivity.getClass());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String result = "";
        if(checkBox1.isChecked()) result += "22000원 ";
        if(checkBox2.isChecked()) result += "24000원 ";
        if(checkBox3.isChecked()) result += "20000원 ";

    }
}