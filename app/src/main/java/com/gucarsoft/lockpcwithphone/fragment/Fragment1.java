package com.gucarsoft.lockpcwithphone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gucarsoft.lockpcwithphone.R;
import com.gucarsoft.lockpcwithphone.service.SocketService;

import java.io.PrintWriter;
import java.net.Socket;

public class Fragment1 extends Fragment {


    TextView ipAddressTextView;
    Button connectToSocketButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);


        ipAddressTextView = rootView.findViewById(R.id.ipAddress);
        connectToSocketButton = rootView.findViewById(R.id.connectToSocket);

        ipAddressTextView.setText("selam");

        connectToSocketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("SEND","message send");
                SocketService.sendMessage("deneme", "192.168.0.21", "4999");

            }
        });
        return rootView;
    }
}
