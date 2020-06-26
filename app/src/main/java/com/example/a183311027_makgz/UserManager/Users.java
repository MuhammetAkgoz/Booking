package com.example.a183311027_makgz.UserManager;


import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {

    private String username;
    private String usermail;
    private String userpassword;
    private String usernumber;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.usermail);
        dest.writeString(this.userpassword);
        dest.writeString(this.usernumber);
    }
    protected Users(Parcel in) {
        this.username = in.readString();
        this.usermail = in.readString();
        this.userpassword = in.readString();
        this.usernumber = in.readString();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getUsername() {
        return username;
    }

    public String getUsermail() {
        return usermail;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public Users() {

    }


    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel source) {
            return new Users(source);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
}
