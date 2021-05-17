package com.gucarsoft.lockpcwithphone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.gucarsoft.lockpcwithphone.database.AppDatabase;
import com.gucarsoft.lockpcwithphone.fragment.Fragment1;
import com.gucarsoft.lockpcwithphone.fragment.Fragment2;
import com.gucarsoft.lockpcwithphone.fragment.Fragment3;
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

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Fragment1()).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.item1:
                        fragment = new Fragment1();
                        break;
                    case R.id.item2:
                        fragment = new Fragment2();
                        break;
                  /*  case R.id.item3:
                        fragment = new Fragment3();
                        break;*/
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }
        });
    }
}