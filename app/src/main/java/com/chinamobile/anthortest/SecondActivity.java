package com.chinamobile.anthortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button btnLoginOut = (Button) findViewById(R.id.login_out);
        btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                intent.putExtra("isRemember",false);
                startActivity(intent);
            }
        });
    }
}
