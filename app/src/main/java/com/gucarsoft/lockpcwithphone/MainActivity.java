package com.gucarsoft.lockpcwithphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gucarsoft.lockpcwithphone.database.AppDatabase;
import com.gucarsoft.lockpcwithphone.model.Profile;
import com.gucarsoft.lockpcwithphone.service.ProfileService;
import com.gucarsoft.lockpcwithphone.service.SocketService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    TextView ipAddressTextView;
    Button connectToSocketButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        ipAddressTextView = findViewById(R.id.ipAddress);
        connectToSocketButton = findViewById(R.id.connectToSocket);

        ipAddressTextView.setText("selam");


        AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
        ProfileService profileService = new ProfileService(appDatabase);

        for (Profile profile : profileService.getAll()) {
            System.out.println(profile.toString());
        }



        connectToSocketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              /*  Profile profile=new Profile();
                    profile.setIpAddress("192.168.0.21");
                    profile.setPortAddress("4999");
                    profile.setProfileName("home");
                    profileService.create(profile);
*/
                SocketService.sendMessage("deneme","192.168.0.23","4999");

            }
        });


    }

}