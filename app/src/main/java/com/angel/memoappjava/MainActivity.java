package com.angel.memoappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseUser mAuth =FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAuth == null){
            startActivity(new Intent(this,LoginActivity.class));
        }else {
            startActivity(new Intent(this,DisplayActivity.class));
        }
        finish();

    }
}