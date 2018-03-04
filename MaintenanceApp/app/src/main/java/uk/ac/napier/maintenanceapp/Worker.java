package uk.ac.napier.maintenanceapp;

import java.io.Serializable;

/**
 * Created by stech on 11/02/2018.
 */

public class Worker implements Serializable{

    private static int serialiseVersionUID = 1324;

    private static int last_id = 1;
    private int id;
    private String name;
    private String dOB;
    private String password;

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
            throw new IllegalArgumentException("Please provide a name");
        }
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
