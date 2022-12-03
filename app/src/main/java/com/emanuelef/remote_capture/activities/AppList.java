package com.emanuelef.remote_capture.activities;

import static com.emanuelef.remote_capture.activities.MainActivity.mainContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.emanuelef.remote_capture.R;
import com.emanuelef.remote_capture.adapters.AppListAdapter;
import com.emanuelef.remote_capture.model.AppListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppList extends AppCompatActivity {
    private ArrayList<String> Apps_uids = null;
    private ArrayList<String> Apps_lebels = null;
    private ListView list_apps;
    AppListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list);

        list_apps = (ListView) findViewById(R.id.list_apps);
        adapter = new AppListAdapter();

        getPackageList();

        list_apps.setAdapter(adapter);

    }

    private void getPackageList() {
        PackageManager packageManager = mainContext.getPackageManager();
        int flag = 0;
        List<ApplicationInfo> applications = packageManager.getInstalledApplications(flag);
        Apps_uids = new ArrayList<String>();
        Apps_lebels = new ArrayList<String>();

        for (ApplicationInfo info: applications){
            Apps_uids.add(String.valueOf(info.uid));
            Apps_lebels.add((String) info.loadLabel(packageManager));
            adapter.addItem(new AppListItem((String)info.loadLabel(packageManager), String.valueOf(info.uid), false));
        }
    }
}