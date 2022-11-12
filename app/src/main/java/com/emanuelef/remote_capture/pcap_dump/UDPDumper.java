package com.emanuelef.remote_capture.pcap_dump;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emanuelef.remote_capture.CaptureService;
import com.emanuelef.remote_capture.Utils;
import com.emanuelef.remote_capture.activities.CaptureCtrl;
import com.emanuelef.remote_capture.activities.Database;
import com.emanuelef.remote_capture.activities.Input_Num;
import com.emanuelef.remote_capture.interfaces.PcapDumper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Iterator;

public class UDPDumper implements PcapDumper {
    public static final String TAG = "UDPDumper";
    private final InetSocketAddress mServer;
    private boolean mSendHeader;
    private DatagramSocket mSocket;
    SQLiteDatabase db;
    Database dbHelper;
    private static String Phone = "010-1234-5678";
    public UDPDumper(InetSocketAddress server) {
        mServer = server;
        mSendHeader = true;
    }

    @Override
    public void startDumper() throws IOException {
        mSocket = new DatagramSocket();
        CaptureService.requireInstance().protect(mSocket);
    }

    @Override
    public void stopDumper() throws IOException {
        mSocket.close();
    }

    @Override
    public String getBpf() {
        return "not (host " + mServer.getAddress().getHostAddress() + " and udp port " + mServer.getPort() + ")";
    }

    private void sendDatagram(byte[] data, int offset, int len) throws IOException {
        DatagramPacket request = new DatagramPacket(data, offset, len, mServer);
        mSocket.send(request);
    }

    @Override
    public void dumpData(byte[] data) throws IOException {
        String appName = CaptureService.app__NAME;
        Log.d(TAG, "ASDSADASDASD: " + appName);
        dbHelper = new Database(Input_Num.mContext);
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT Phone_num FROM phone;", null);
        while(cursor.moveToNext()){
            Phone = cursor.getString(0);
        }
        if(mSendHeader) {
            mSendHeader = false;
            byte[] hdr = CaptureService.getPcapHeader();
            //code 수정 시작
            byte[] phone = ("My name is " + Phone + " App name is " + appName + " end").getBytes();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(phone);
            outputStream.write(hdr);

            byte[] result = outputStream.toByteArray();
            //끝 (아래의 result를 hdr로 다시 바꿔줘야함)
            sendDatagram(result, 0, hdr.length);
        }

        Iterator<Integer> it = Utils.iterPcapRecords(data);
        int pos = 0;

        while(it.hasNext()) {
            //code 수정 시작
            byte[] phone = ("My name is " + Phone + " App name is " + appName + " end").getBytes();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(phone);
            outputStream.write(data);

            byte[] result = outputStream.toByteArray();
            //끝 (아래의 result를 data로 다시 바꿔줘야함)

            int rec_len = it.next();
            sendDatagram(result, pos, rec_len);
            pos += rec_len;
        }
    }
}