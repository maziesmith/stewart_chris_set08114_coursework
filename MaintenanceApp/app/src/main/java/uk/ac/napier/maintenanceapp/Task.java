package uk.ac.napier.maintenanceapp;

import android.media.Image;

import java.util.Date;

/**
 * Created by stech on 08/02/2018.
 * Class to old the details of a task created
 */

public class Task {

    private static int id = 1;
    private String home;
    private String title;
    private String dateSubmitted;
    private String dateDue;
    private String desc;
    private Image location;
    private String notes;

    public Task()
    {
        this.setId(id);
        id++;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Task.id = id;
    }

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

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Image getLocation() {
        return location;
    }

    public void setLocation(Image location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }




}
