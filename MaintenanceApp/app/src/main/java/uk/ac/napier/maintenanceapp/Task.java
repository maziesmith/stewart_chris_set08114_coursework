package uk.ac.napier.maintenanceapp;

import android.media.Image;

import java.util.Date;

/**
 * Created by stech on 08/02/2018.
 * Class to old the details of a task created
 */

public class Task {

    private String home;
    private String title;
    private Date dateSubmitted;
    private Date dateDue;
    private String Desc;
    private Image Location;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Image getLocation() {
        return Location;
    }

    public void setLocation(Image location) {
        Location = location;
    }



}
