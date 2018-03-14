package uk.ac.napier.maintenanceapp;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static uk.ac.napier.maintenanceapp.CompletedList.completedTasks;

/**
 * Created by stech on 08/02/2018.
 */

public class TaskList implements Serializable{

    private static int serialiseVersionUID = 45800088;

    public static ArrayList<Task> taskList = new ArrayList<>();

    public void add(Task task) {
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

    public int size() {
        return taskList.size();
    }

    public void remove(int id) {
        for (int i = 0; i < taskList.size(); i++) {
            if (id == taskList.get(i).getId()) {
                taskList.remove(i);
            }
        }
    }

    public void complete(int id) {
        for (int i = 0; i < taskList.size(); i++) {
            if (id == taskList.get(i).getId()) {
                completedTasks.add(taskList.get(i));
                taskList.remove(id);
            }
        }
    }
}
