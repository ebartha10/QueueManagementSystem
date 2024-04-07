package server.impl;

import misc.SharedCounter;
import misc.SimulationLogger;
import model.Task;
import server.Server;
import singleton.SharedCounterSingleton;
import singleton.SimulationLoggerSingleton;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("BusyWait")
public class ServerImpl implements Server, Runnable, Comparable<ServerImpl>{
    private static int SERVER_ID_COUNTER = 0;
    private int serverID;
    private volatile Queue<Task> serverQ;
    private int jobTimeEnd = 0;
    private double percentDone = 0;
    private int taskIDRunning = -1;
    private final SharedCounter sharedCounter;
    private final SimulationLogger simulationLogger;
    public ServerImpl() {
        sharedCounter = SharedCounterSingleton.getSharedCounter();
        simulationLogger = SimulationLoggerSingleton.getSimulationLogger();
        serverID = SERVER_ID_COUNTER++;
        this.jobTimeEnd = 0;
        this.serverQ = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void addClient(Task givenTask) {
        serverQ.add(givenTask);
        setJobTimeEnd(givenTask.getServiceTime() + jobTimeEnd);
    }

    @Override
    public void run() {
        while(true){
            Task currentTask = serverQ.poll();
            if(currentTask == null) {
                setTaskIDRunning(-1);
                continue;
            }
            currentTask.setWaitingTime(sharedCounter.getSimulationTime() - currentTask.getArrivalTime());
            //System.out.println("TASK RUNNING :" + currentTask.toString() + Thread.currentThread().getName() + "\n");
            simulationLogger.logTaskStart(currentTask, serverID);
            setTaskIDRunning(currentTask.getID());

            try{
                serviceTask(currentTask);
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
                return;
            }
            simulationLogger.logTaskEnd(currentTask, serverID);
            //System.out.println("TASK END:" + currentTask.toString() + Thread.currentThread().getName() + "\n");
        }
    }
    private void serviceTask(Task givenTask) throws InterruptedException{
        for (int i = 0; i < givenTask.getServiceTime(); i++) {
            setPercentDone((double)(i * 100 / givenTask.getServiceTime()));
            Thread.sleep(1000);
        }
        setPercentDone(100.0);
        sharedCounter.countTasks();
    }
    public void stopServer(){
        Thread.currentThread().interrupt();
    }
    public int getJobTimeEnd() {
        return jobTimeEnd;
    }

    public void setJobTimeEnd(int jobTimeEnd) {
        this.jobTimeEnd = jobTimeEnd;
    }

    @Override
    public String toString() {
        return "ServerImpl{" +
                "serverQ=" + serverQ.toString() +
                ", jobTimeEnd=" + jobTimeEnd +
                '}';
    }

    @Override
    public int compareTo(ServerImpl o) {
        if(o == null){
            return 1;
        }
        if(o.getJobTimeEnd() == this.jobTimeEnd){
            return 0;
        }
        else if(o.getJobTimeEnd() < this.jobTimeEnd){
            return -1;
        }
        else {
            return 1;
        }
    }

    public double getPercentDone() {
        return percentDone;
    }

    public Queue<Task> getServerQ() {
        return serverQ;
    }

    public int getTaskIDRunning() {
        return taskIDRunning;
    }

    public void setServerQ(Queue<Task> serverQ) {
        this.serverQ = serverQ;
    }

    public void setPercentDone(double percentDone) {
        this.percentDone = percentDone;
    }

    public void setTaskIDRunning(int taskIDRunning) {
        this.taskIDRunning = taskIDRunning;
    }
}
