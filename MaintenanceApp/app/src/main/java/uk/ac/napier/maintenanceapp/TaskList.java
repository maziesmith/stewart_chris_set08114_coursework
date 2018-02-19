package uk.ac.napier.maintenanceapp;

import java.io.IOException;
import java.util.ArrayList;

import static uk.ac.napier.maintenanceapp.CompletedList.completedTasks;

/**
 * Created by stech on 08/02/2018.
 */

public class TaskList {

    static ArrayList<Task> taskList = new ArrayList<>();

     public void add(Task task)
    {
        taskList.add(task);
    }

    public Task find(int id) {
        for (Task task : taskList) {
            if (id == task.getId()) {
                return task;
            }
        }
        return null;
    }

    public int size(){
        return taskList.size();
    }

    public void remove(int id)
    {
        for (Task task : taskList) {
            if (id == task.getId()) {
                taskList.remove(id);
            }
        }
    }

     public void complete(int id)
     {
         for (Task task : taskList) {
             if (id == task.getId()) {
                 completedTasks.add(task);
                 taskList.remove(id);
             }
         }
     }

}
