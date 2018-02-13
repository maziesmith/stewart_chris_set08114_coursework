package uk.ac.napier.maintenanceapp;

/**
 * Created by stech on 11/02/2018.
 */

public class Worker {

    private static int id = 1;
    private String name;
    private String dOB;
    private String password;

    public Worker(){
        this.id = id;
        id++;
    }

    public static int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
