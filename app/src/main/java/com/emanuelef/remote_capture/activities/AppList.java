package com.emanuelef.remote_capture.activities;

import static com.emanuelef.remote_capture.activities.MainActivity.mainContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.emanuelef.remote_capture.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppList extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list);

        getPackageList();
    }

    private void getPackageList() {
        PackageManager packageManager = mainContext.getPackageManager();
        int flag = 0;
        List<ApplicationInfo> applications = packageManager.getInstalledApplications(flag);

        for (ApplicationInfo info: applications){
            Log.d("APP", "uid: " + info.uid
                    + ", label: " + info.loadLabel(packageManager));
        }
    }
}
