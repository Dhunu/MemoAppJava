package com.angel.memoappjava;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;




import java.util.Date;
import java.util.Objects;

public class NewNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Toolbar mToolbar = findViewById(R.id.toolbar_note);

        setSupportActionBar(mToolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Memo/To-Do");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewNoteActivity.this,DisplayActivity.class));
            }
        });

        editTextTitle = findViewById(R.id.et_title);
        editTextDescription = findViewById(R.id.et_description);
        Button save = findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        Date date = new Date(System.currentTimeMillis());

        String userID = FirebaseAuth.getInstance().getUid();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Please insert title and description",Toast.LENGTH_SHORT).show();
            return;
        }

        assert userID != null;
        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection(userID);
        notebookRef.add(new Note(title, description, date ));
        Toast.makeText(this,"Note Added " + date,Toast.LENGTH_SHORT).show();
        finish();
    }
}