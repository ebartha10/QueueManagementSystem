package misc.impl;

import misc.SimulationLogger;
import misc.StatisticsKeeper;
import model.Task;
import singleton.StatisticsKeeperSingleton;

import java.io.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class SimulationLoggerImpl implements SimulationLogger {
    private final File fileWriter;
    private PrintWriter bufferedWriter;
    public SimulationLoggerImpl() {
        fileWriter = new File("log.txt");
        System.out.println(fileWriter.getAbsolutePath());
        if(!fileWriter.exists()){
            try {
                fileWriter.createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        }
        try {
            bufferedWriter = new PrintWriter(new FileWriter(fileWriter));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void logSimulationStart(List<Task> givenTaskList, int serverNumber, int simulationTime) {
        bufferedWriter.print("Simulation started: " + LocalTime.now() + "\nTask number: " + givenTaskList.size() + "\nQueue number: " + serverNumber + "\nTime limit: " + simulationTime + "\nTasks:\n");
        for (Task taskIterator : givenTaskList) {
            bufferedWriter.println(taskIterator.toString());
        }
        bufferedWriter.flush();
    }

    @Override
    public void logTaskStart(Task givenTask, int serverID) {
        bufferedWriter.println("TASK START: " + givenTask.toString() + " at queue: " + serverID + " at time: " + LocalTime.now());
        bufferedWriter.flush();
    }

    @Override
    public void logTaskEnd(Task givenTask, int serverID) {
        bufferedWriter.println("TASK END: " + givenTask.toString() + " at queue: " + serverID + " at time: " + LocalTime.now());
        bufferedWriter.flush();
    }

    @Override
    public void logSimulationEnd(float givenServiceTime, float givenWaitingTime) {
        bufferedWriter.println("SIMULATION ENDED\nAverage service time:" + givenServiceTime + "\nAverage waiting time" + givenWaitingTime);
        bufferedWriter.flush();
    }

    @Override
    public void logTime(int givenTime) {
        bufferedWriter.println("Time = " + givenTime);
        bufferedWriter.flush();
    }
}
