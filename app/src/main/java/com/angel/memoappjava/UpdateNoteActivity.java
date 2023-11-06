package com.angel.memoappjava;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Date;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class UpdateNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        Toolbar mToolbar = findViewById(R.id.toolbar_update_note);

        setSupportActionBar(mToolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Memo/To-Do");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateNoteActivity.this,DisplayActivity.class));
            }
        });

        editTextTitle = findViewById(R.id.et_title);
        editTextDescription = findViewById(R.id.et_description);
        Button update = findViewById(R.id.btn_update);

        String userID = FirebaseAuth.getInstance().getUid();
        DocumentReference notebookRef = FirebaseFirestore.getInstance()
                .collection(Objects.requireNonNull(userID)).document(Objects.requireNonNull(getIntent().getStringExtra("id")));

        notebookRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    if (documentSnapshot.exists()){
                        String getTitle = documentSnapshot.getString("title");
                        String getDescription = documentSnapshot.getString("description");

                        editTextTitle.setText(getTitle);
                        editTextDescription.setText(getDescription);
                    }
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateNote(){
        final String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        Date date = new Date(System.currentTimeMillis());

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Please insert title and description",Toast.LENGTH_SHORT).show();
            return;
        }
        String userID = FirebaseAuth.getInstance().getUid();
        DocumentReference notebookRef = FirebaseFirestore.getInstance()
                .collection(Objects.requireNonNull(userID)).document(Objects.requireNonNull(getIntent().getStringExtra("id")));

        notebookRef.update("title", title,
                "description" , description,
                "date", date);
        Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show();
        finish();
    }
}