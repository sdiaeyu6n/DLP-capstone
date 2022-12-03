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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.emanuelef.remote_capture.R;
import com.emanuelef.remote_capture.adapters.AppListAdapter;
import com.emanuelef.remote_capture.model.AppListItem;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppList extends AppCompatActivity implements Runnable {
    private ArrayList<String> Apps_uids = null;
    private ArrayList<String> Apps_lebels = null;
    String Phone;
    private ListView list_apps;
    AppListAdapter adapter;
    public static ArrayList<String> Checked_Lebles = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list);
        Button finish = (Button)findViewById(R.id.finish);
        list_apps = (ListView) findViewById(R.id.list_apps);
        adapter = new AppListAdapter();

        getPackageList();

        list_apps.setAdapter(adapter);
        Database dbHelper = new Database(Input_Num.mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT Phone_num FROM phone;", null);
        while(cursor.moveToNext()){
            Phone = cursor.getString(0);
        }
        Phone = Phone.replaceAll("--", "");
        //폰 번호 가져오는 부분

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread th = new Thread(AppList.this);
                Log.d("FIN", String.valueOf(Checked_Lebles));

                th.start();

                finish();
            }
        });
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

    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0 ; i < Checked_Lebles.size(); i++){
                if(i == Checked_Lebles.size()-1){
                    sb.append(Checked_Lebles.get(i));
                }
                else {
                    sb.append(Checked_Lebles.get(i));
                    sb.append("*");
                }
            }
            String s = sb.toString();

            URL url = new URL("http://49.50.160.26:1235/user/app-list?appName=" + s + "&phoneNum=" + Phone);
            conn = (HttpURLConnection) url.openConnection();
            Log.d("URL", String.valueOf(url));
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d("TAG", "HTTP OK 성공");
                conn.disconnect();
                Checked_Lebles.clear();
            }
            else {
                Log.d("TAG", "HTTP OK 안됨");
                Checked_Lebles.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}