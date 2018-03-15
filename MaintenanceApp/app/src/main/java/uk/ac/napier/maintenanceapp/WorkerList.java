package uk.ac.napier.maintenanceapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by stech on 11/02/2018.
 *
 * List holds all worker profiles
 */

public class WorkerList implements Serializable {

    private static int serialiseVersionUID = 4158;

    public static ArrayList<Worker> workerList = new ArrayList<>();

    public void add(Worker worker)
    {
        workerList.add(worker);
    }

    public Worker find(int id) {
        for (Worker worker : workerList) {
            if (id == worker.getId()) {
                return worker;
            }
        }
        return null;
    }
}
