package uk.ac.napier.maintenanceapp;

import java.util.ArrayList;

/**
 * Created by stech on 08/02/2018.
 */

public class CompletedList {

    ArrayList<Task> completedTasks = new ArrayList<>();

    public void add(Task task)
    {
        completedTasks.add(task);
    }

    public Task find(int id) {
        for (Task task : completedTasks) {
            if (id == task.getId()) {
                return task;
            }
        }
        return null;
    }

    public void remove(int id)
    {
        for (Task task : completedTasks) {
            if (id == task.getId()) {
                completedTasks.remove(id);
            }
        }
    }

}
