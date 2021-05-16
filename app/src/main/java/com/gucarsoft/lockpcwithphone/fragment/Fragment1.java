package com.gucarsoft.lockpcwithphone.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gucarsoft.lockpcwithphone.R;
import com.gucarsoft.lockpcwithphone.adapter.ProfileRCViewAdapter;
import com.gucarsoft.lockpcwithphone.database.AppDatabase;
import com.gucarsoft.lockpcwithphone.model.Profile;
import com.gucarsoft.lockpcwithphone.service.ProfileService;
import com.gucarsoft.lockpcwithphone.service.SocketService;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Fragment1 extends Fragment {


    TextView ipAddressTextView;
    Button connectToSocketButton;
    static ProfileRCViewAdapter adapter;
    static List<Profile> profileList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        AppDatabase appDatabase = AppDatabase.getAppDatabase(getActivity());
        ProfileService profileService = new ProfileService(appDatabase);

        profileList = profileService.getAll();

       /* Profile profile = new Profile();
        profile.setIpAddress("192.168.0.21");
        profile.setPortAddress("4999");
        profile.setProfileName("home");
        profileService.create(profile);

        Profile profile2 = new Profile();
        profile2.setIpAddress("192.168.0.21");
        profile2.setPortAddress("4999");
        profile2.setProfileName("iş");
        profileService.create(profile2);

        Profile profile3 = new Profile();
        profile3.setIpAddress("192.168.0.21");
        profile3.setPortAddress("4999");
        profile3.setProfileName("library");
        profileService.create(profile3);*/


        for (Profile prof : profileService.getAll()) {
            System.out.println(prof.toString());
        }

        RecyclerView recyclerView = rootView.findViewById(R.id.RCViewProfile);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProfileRCViewAdapter(getActivity(), profileList);
        adapter.setClickListener(this::onItemClick);
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    public void onItemClick(View view, int position) {
       // Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position).getProfileName(), Toast.LENGTH_SHORT).show();
    }

    public static void refresh(Context context) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        ProfileService profileService = new ProfileService(appDatabase);
        profileList.clear();
        profileList.addAll(profileService.getAll());
        adapter.notifyDataSetChanged();
    }


}

