package uk.ac.napier.maintenanceapp;

/**
 * Created by stech on 11/02/2018.
 */

public class Worker {

    private static int id = 1;
    private String firstName;
    private String lastName;
    private String dOB;
    private String password;

    public Worker(){
        this.id = id;
        id++;
    }

    public static int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
