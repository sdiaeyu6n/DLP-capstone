package com.emanuelef.remote_capture.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.emanuelef.remote_capture.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class NotTraceInfo extends AppCompatActivity implements Runnable{
    String Phone;
    String s ="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_trace_info);

        CheckBox check1 = findViewById(R.id.check1);
        CheckBox check2 = findViewById(R.id.check2);
        CheckBox check3 = findViewById(R.id.check3);
        CheckBox check4 = findViewById(R.id.check4);
        CheckBox check5 = findViewById(R.id.check5);
        CheckBox check6 = findViewById(R.id.check6);
        Button finish = findViewById(R.id.finish);
        List<String> lst1 = new ArrayList<>();
        StringBuilder sb = new StringBuilder();


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
                Thread th = new Thread(NotTraceInfo.this);
                if(check1.isChecked()){
                    lst1.add("1");
                }else{
                    lst1.add("0");
                }
                if(check2.isChecked()){
                    lst1.add("1");
                }else{
                    lst1.add("0");
                }
                if(check3.isChecked()){
                    lst1.add("1");
                }else{
                    lst1.add("0");
                }
                if(check4.isChecked()){
                    lst1.add("1");
                }else{
                    lst1.add("0");
                }
                if(check5.isChecked()){
                    lst1.add("1");
                }else{
                    lst1.add("0");
                }
                if(check6.isChecked()){
                    lst1.add("1");
                }else{
                    lst1.add("0");
                }
                for (int i = 0; i <6 ; i++){
                   if (s.length() < 6) {
                       sb.append(lst1.get(i));
                   }
                }
                s = sb.toString(); // 체크박스 문자열 추출 ex) 100011
                Log.d("CHECK", s);

                th.start();

                finish();
            }
        });

    }

    public void run() {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://49.50.160.26:1235/user/info-list?notTrace=" + s + "&phoneNum=" + Phone);
            conn = (HttpURLConnection) url.openConnection();
            Log.d("URL", String.valueOf(url));
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d("TAG", "HTTP OK 성공");
            }
            else {
                Log.d("TAG", "HTTP OK 안됨");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
