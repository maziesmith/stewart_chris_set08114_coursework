package uk.ac.napier.maintenanceapp;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by stech on 08/02/2018.
 * Class to hold the details of a task created
 */

public class Task implements Serializable{

    private static int serialiseVersionUID = 2589;

    private static int last_id = 1;
    private int id;
    private String home;
    private String title;
    private String priority;
    private String dateSubmitted;
    private String dateDue;
    private String desc;
    private Bitmap picture;
    private String notes;
    private Boolean completed;
    private String completeDate;

    public Task()
    {
        id = last_id++;
    }

    public static int getLast_id() {
        return last_id;
    }

    public static void setLast_id(int last_id) {
        Task.last_id = last_id;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
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

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }




}
