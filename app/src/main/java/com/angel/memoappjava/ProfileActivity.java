package com.angel.memoappjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPassword;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        textViewName = findViewById(R.id.profile_name);
        textViewEmail = findViewById(R.id.profile_email);
        textViewPassword = findViewById(R.id.profile_password);

        Toolbar mToolbar = findViewById(R.id.toolbar_profile);

        setSupportActionBar(mToolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,DisplayActivity.class));
                finish();
            }
        });

        loadData();
    }

    private void loadData() {
        String userId = FirebaseAuth.getInstance().getUid();

        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection(userId).document("ProfileData");

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name = documentSnapshot.getString("name");
                    String email = documentSnapshot.getString("email");
                    String password = documentSnapshot.getString("password");

                    textViewName.setText(name);
                    textViewEmail.setText(email);
                    textViewPassword.setText(password);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
