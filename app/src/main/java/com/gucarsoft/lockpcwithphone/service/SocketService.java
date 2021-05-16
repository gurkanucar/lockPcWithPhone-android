package com.gucarsoft.lockpcwithphone.service;

import android.util.Log;

import com.gucarsoft.lockpcwithphone.Constants;

import java.io.PrintWriter;
import java.net.Socket;

public class SocketService {

    public static void sendMessage(String message, String ip, String port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(Constants.CAN_SEND_MESSAGE){
                        Socket s = new Socket(ip, Integer.parseInt(port));
                        PrintWriter pr = new PrintWriter(s.getOutputStream());
                        pr.println(message);
                        pr.flush();
                        s.close();
                        Log.d("OK","yollandı");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ERROR","hata oldu");
                }
            }
        }).start();
    }

}
