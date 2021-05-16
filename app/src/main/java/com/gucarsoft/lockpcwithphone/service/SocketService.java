package com.gucarsoft.lockpcwithphone.service;

import java.io.PrintWriter;
import java.net.Socket;

public class SocketService {

    public static void sendMessage(String message, String ip, String port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket(ip, Integer.parseInt(port));
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println(message);
                    pr.flush();
                    s.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
