package com.angel.memoappjava;

import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private UiModeManager uiModeManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Toolbar mToolbar = findViewById(R.id.toolbar_settings);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Settings");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,DisplayActivity.class));
                finish();
            }
        });

        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
    }

    public void NightModeON(View view){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
    }

    public void NightModeOFF(View view){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
    }

    public void NightModeSystemDefault(View view){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_AUTO);
    }
}
