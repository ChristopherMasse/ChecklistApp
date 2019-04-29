package com.christophermasse.checklist.presentation.activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {


    protected void showToastShort(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToastLong(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
