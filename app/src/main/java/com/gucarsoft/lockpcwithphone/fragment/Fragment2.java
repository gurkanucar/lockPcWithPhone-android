package com.gucarsoft.lockpcwithphone.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.gucarsoft.lockpcwithphone.Capture;
import com.gucarsoft.lockpcwithphone.MainActivity;
import com.gucarsoft.lockpcwithphone.R;
import com.gucarsoft.lockpcwithphone.database.AppDatabase;
import com.gucarsoft.lockpcwithphone.model.Profile;
import com.gucarsoft.lockpcwithphone.service.ProfileService;

public class Fragment2 extends Fragment {

    ImageView saveButton;
    ImageView cameraButton;
    TextInputEditText profileNameText;
    TextInputEditText profileAddressText;
    TextInputEditText profilePortText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);


        saveButton = rootView.findViewById(R.id.createSaveBtn);
        cameraButton = rootView.findViewById(R.id.cameraBtn);
        profileNameText = rootView.findViewById(R.id.createProfileName);
        profileAddressText = rootView.findViewById(R.id.createProfileAddress);
        profilePortText = rootView.findViewById(R.id.createProfilePort);

        //scanCode();

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(profileNameText.getText().toString()) || TextUtils.isEmpty(profileAddressText.getText().toString()) || TextUtils.isEmpty(profilePortText.getText().toString())) {
                    showAlert("Warning!", "Please fill all fields");
                } else {
                    Profile profile = new Profile();
                    profile.setIpAddress(profileAddressText.getText().toString());
                    profile.setPortAddress(profilePortText.getText().toString());
                    profile.setProfileName(profileNameText.getText().toString());
                    AppDatabase appDatabase = AppDatabase.getAppDatabase(getActivity());
                    ProfileService profileService = new ProfileService(appDatabase);
                    profileService.create(profile);
                    profileNameText.setText("");
                    profileAddressText.setText("");
                    profilePortText.setText("");
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }
        });

        return rootView;
    }

    public void scanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scan a qr code");
        intentIntegrator.setCameraId(0);  // Use a specific camera of the device
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCaptureActivity(Capture.class);
       // intentIntegrator.initiateScan();
        IntentIntegrator.forSupportFragment(Fragment2.this).initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() != null) {
                if (intentResult.getContents().contains(":")) {
                    showAlert("Connection String Found", intentResult.getContents());
                    profileAddressText.setText(intentResult.getContents().split(":")[0]);
                    profilePortText.setText(intentResult.getContents().split(":")[1]);
                } else {
                    showAlert("Connection String Error", "connection string not acceptible");
                }
            } else {
                showAlert("Not found", "please fill all fields");
            }
        }

    }

    public void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
