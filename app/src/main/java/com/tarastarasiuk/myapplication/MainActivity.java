package com.tarastarasiuk.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnItemSelectedListener(getOnItemSelectedListener());
    }

    @NonNull
    private NavigationBarView.OnItemSelectedListener getOnItemSelectedListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.folderList:
                    Toast.makeText(MainActivity.this, "Folder", Toast.LENGTH_SHORT).show();
                    item.setChecked(true);
                    break;
                case R.id.filesList:
                    Toast.makeText(MainActivity.this, "Files", Toast.LENGTH_SHORT).show();
                    item.setChecked(true);
                    break;
            }
            return false;
        };
    }

}