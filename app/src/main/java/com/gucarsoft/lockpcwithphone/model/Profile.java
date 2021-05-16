package com.gucarsoft.lockpcwithphone.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Profile implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "profile_name")
    private String profileName;

    @ColumnInfo(name = "ip_address")
    private String ipAddress;

    @ColumnInfo(name = "port_address")
    private String portAddress;

    @ColumnInfo(name = "selected")
    private Boolean selected;

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", profileName='" + profileName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", portAddress='" + portAddress + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortAddress() {
        return portAddress;
    }

    public void setPortAddress(String portAddress) {
        this.portAddress = portAddress;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
