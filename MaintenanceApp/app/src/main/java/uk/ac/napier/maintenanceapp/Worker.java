package uk.ac.napier.maintenanceapp;

import android.graphics.Bitmap;

import java.io.Serializable;


/**
 * Created by stech on 11/02/2018.
 *
 * Class that holds all information about an individual worker
 */

public class Worker implements Serializable{

    private static int serialiseVersionUID = 8923;

    private static int last_id = 1;
    private int id;
    private String name;
    private String password;
    private Bitmap profile_pic;
    private String email;
    private String phone;

    public Worker(){
        id = last_id++;
    }

    public static int getLast_id() {
        return last_id;
    }

    public static void setLast_id(int last_id) {
        Worker.last_id = last_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!name.isEmpty()){
            this.name = name;
        }
        else{
            throw new RuntimeException("Please provide a name");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!password.isEmpty()){
            this.password = password;
        }else{
            throw new RuntimeException("Please provide a password");
        }
    }

    public Bitmap getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(Bitmap profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.contains("@")){
            this.email = email;
        }else{
            throw new RuntimeException("Please provide a valid email");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
            this.phone = phone;
    }
}
