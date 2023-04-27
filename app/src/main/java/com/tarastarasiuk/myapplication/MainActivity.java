package com.tarastarasiuk.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends AppCompatActivity {

    private static final String REQUIRED_MEDIA_PERMISSION = Manifest.permission.READ_MEDIA_VIDEO;
    private boolean isStorageVideoPermitted = false;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnItemSelectedListener(getOnItemSelectedListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        permissionsRequest();
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


    private void permissionsRequest() {
        if (!isStorageVideoPermitted) {
            requestPermissionStorage();
        } else {
            Toast.makeText(this, "Video storage permission granted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermissionStorage() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, REQUIRED_MEDIA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            isStorageVideoPermitted = true;
        } else {
            requestPermissionLauncherStorageImages.launch(REQUIRED_MEDIA_PERMISSION);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncherStorageImages =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            isStorageVideoPermitted = true;
                        } else {
                            isStorageVideoPermitted = false;
                            sendToSettingDialog();
                        }
                    });

    private void sendToSettingDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert for permission")
                .setMessage("Go to setting and enable media permissions to use this app")
                .setPositiveButton("Settings", (dialogInterface, i) -> {
                    Intent settingsIntent = new Intent();
                    settingsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    settingsIntent.setData(uri);
                    startActivity(settingsIntent);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Exit", (dialogInterface, i) -> finish())
                .setCancelable(false)
                .show();
    }

}