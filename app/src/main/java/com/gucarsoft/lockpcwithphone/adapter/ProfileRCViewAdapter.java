package com.gucarsoft.lockpcwithphone.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.gucarsoft.lockpcwithphone.Constants;
import com.gucarsoft.lockpcwithphone.R;
import com.gucarsoft.lockpcwithphone.database.AppDatabase;
import com.gucarsoft.lockpcwithphone.fragment.Fragment1;
import com.gucarsoft.lockpcwithphone.model.Profile;
import com.gucarsoft.lockpcwithphone.service.ProfileService;
import com.gucarsoft.lockpcwithphone.service.SocketService;

import java.util.ArrayList;
import java.util.List;

public class ProfileRCViewAdapter extends RecyclerView.Adapter<ProfileRCViewAdapter.ViewHolder> {

    private List<Profile> profiles;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public ProfileRCViewAdapter(Context context, List<Profile> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.profiles = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.profile_item, parent, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.profileName.setText(profile.getProfileName());
        holder.addressName.setText(profile.getIpAddress() + ":" + profile.getPortAddress());
        holder.powerOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketService.sendMessage(Constants.POWEROFF, profile.getIpAddress(), profile.getPortAddress());
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profile selected = profiles.get(holder.getAdapterPosition());

                LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
                View dialog = inflater.inflate(R.layout.edit_dialog, null);
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setCancelable(true);
                ImageView deleteButton = dialog.findViewById(R.id.updateDeleteBtn);
                ImageView cancelButton = dialog.findViewById(R.id.updateCancelBtn);
                ImageView saveButton = dialog.findViewById(R.id.updateSaveBtn);
                TextInputEditText profileNameText = dialog.findViewById(R.id.editDialogProfileName);
                TextInputEditText profileAddressText = dialog.findViewById(R.id.editDialogProfileAddress);
                TextInputEditText profilePortText = dialog.findViewById(R.id.editDialogProfilePort);

                profileNameText.setText(selected.getProfileName());
                profileAddressText.setText(selected.getIpAddress());
                profilePortText.setText(selected.getPortAddress());
                alertDialog.setView(dialog);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Profile temp = new Profile();
                        temp.setId(selected.getId());
                        temp.setProfileName(profileNameText.getText().toString());
                        temp.setPortAddress(profilePortText.getText().toString());
                        temp.setIpAddress(profileAddressText.getText().toString());

                        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
                        ProfileService profileService = new ProfileService(appDatabase);
                        profileService.update(temp);
                        Fragment1.refresh(context);
                        Toast.makeText(context,"Kaydedildi",Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();

                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Silindi",Toast.LENGTH_SHORT).show();
                        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
                        ProfileService profileService = new ProfileService(appDatabase);
                        profileService.delete(selected);
                        Fragment1.refresh(context);
                        alertDialog.cancel();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout linearLayout;
        TextView profileName;
        TextView addressName;
        ImageButton editBtn;
        ImageButton messageBtn;
        ImageButton powerOffBtn;
        ImageButton pinBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.profileItemLinearLayout);
            profileName = itemView.findViewById(R.id.profileItemName);
            addressName = itemView.findViewById(R.id.profileItemAddress);
            editBtn = itemView.findViewById(R.id.profileItemEditBtn);
            messageBtn = itemView.findViewById(R.id.profileItemMessageBtn);
            powerOffBtn = itemView.findViewById(R.id.profileItemCloseBtn);
            pinBtn = itemView.findViewById(R.id.profileItemPinBtn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public Profile getItem(int id) {
        return this.profiles.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
