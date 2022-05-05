package com.MikroFin.mikrofintrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        continueWithEmail();
    }

    private void continueWithEmail() {
        Button continueWdEmail = (Button) findViewById(R.id.conWdEmail);
        continueWdEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstPage.this, SignIn_email.class));
            }
        });

    }
}
