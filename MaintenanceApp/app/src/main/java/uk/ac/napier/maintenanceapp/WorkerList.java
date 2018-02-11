package uk.ac.napier.maintenanceapp;

import java.util.ArrayList;

/**
 * Created by stech on 11/02/2018.
 */

public class WorkerList {

    ArrayList<Worker> workerList = new ArrayList<>();

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

    public void remove(int id)
    {
        for (Worker worker : workerList) {
            if (id == worker.getId()) {
                workerList.remove(id);
            }
        }
    }

}
