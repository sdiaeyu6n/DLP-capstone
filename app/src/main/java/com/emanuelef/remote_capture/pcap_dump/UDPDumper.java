package com.emanuelef.remote_capture.pcap_dump;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.emanuelef.remote_capture.CaptureService;
import com.emanuelef.remote_capture.Utils;
import com.emanuelef.remote_capture.activities.CaptureCtrl;
import com.emanuelef.remote_capture.activities.Database;
import com.emanuelef.remote_capture.activities.Input_Num;
import com.emanuelef.remote_capture.interfaces.PcapDumper;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class UDPDumper implements PcapDumper {
    public static final String TAG = "UDPDumper";
    private final InetSocketAddress mServer;
    private boolean mSendHeader;
    private DatagramSocket mSocket;
    SQLiteDatabase db;
    Database dbHelper;
    public static String Phone = "010-1234-5678";
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void dumpData(byte[] data) throws IOException {
        String appName = CaptureService.app__NAME;
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

            byte[] phone = ("My name is " + Phone + "App name is "+ appName +"dongguk").getBytes();

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
            byte[] phone = ("My name is " + Phone + "App name is "+ appName +"dongguk").getBytes();

            //암호화
            String secretKey = "Secret";
            String fSalt = "tJHnN5b1i6wvXMwzYMRk";

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(phone);
            outputStream.write(data);

            String plain = outputStream.toString();
            String cipherText="";
            String dcrCipherText="";
            try {
                cipherText = encrypt(secretKey, fSalt, plain);
                dcrCipherText = decrypt(secretKey, fSalt, cipherText);
            } catch (Exception e) {
                System.out.println("암호화실패");
            }

            byte[] result = cipherText.getBytes(StandardCharsets.UTF_8);
            try {
                System.out.println(pos+ "암호화==복호화:"+ plain.equals(dcrCipherText));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //끝 (아래의 result를 data로 다시 바꿔줘야함)
            int rec_len = it.next();
            sendDatagram(result, pos, rec_len);
            pos += rec_len;
        }
    }
    //Encrypt
    public static String encrypt(String secretKey, String salt, String value) throws Exception {
        Cipher cipher = initCipher(secretKey, salt, Cipher.ENCRYPT_MODE);
        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.encodeBase64String(encrypted);
    }

    public static String decrypt(String secretKey, String salt, String encrypted) throws Exception {
        Cipher cipher = initCipher(secretKey, salt, Cipher.DECRYPT_MODE);
        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new String(original);
    }

    private static Cipher initCipher(String secretKey, String salt, int mode) throws Exception {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec skeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(mode, skeySpec, new IvParameterSpec(new byte[16]));
        return cipher;
    }

}