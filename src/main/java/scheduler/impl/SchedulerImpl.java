package scheduler.impl;

import model.Task;
import scheduler.Scheduler;
import server.impl.ServerImpl;

import java.util.ArrayList;
import java.util.List;

public class SchedulerImpl implements Scheduler {

    private List<ServerImpl> serverList;
    private int numberOfServers;

    public SchedulerImpl(int serverNumber, List<ServerImpl> serverList) {
        this.numberOfServers = serverNumber;
        this.serverList = serverList;

    }

    @Override
    public void dispatchTask(Task givenTask, int strategy) {
        if(strategy == Scheduler.STRATEGY_TIME) {
            ServerImpl minServer = serverList.get(0);
            for (ServerImpl serverIterator : serverList) {
                if (serverIterator.getJobTimeEnd() < minServer.getJobTimeEnd()) {
                    minServer = serverIterator;
                }
            }
            if (minServer != null) {
                minServer.addClient(givenTask);
            }
        }
        else if(strategy == Scheduler.STRATEGY_LENGTH){
            ServerImpl minServer = serverList.get(0);
            for (ServerImpl serverIterator : serverList) {
                if (serverIterator.getServerQ().size() < minServer.getServerQ().size()) {
                    minServer = serverIterator;
                }
            }
            if (minServer != null) {
                minServer.addClient(givenTask);
            }
        }
    }

    @Override
    public String toString() {
        return "SchedulerImpl{" +
                "serverList=" + serverList.toString() +
                ", serverNumber=" + numberOfServers +
                "}";
    }
    public List<ServerImpl> getServerList() {
        return serverList;
    }

    public void setServerList(List<ServerImpl> serverList) {
        this.serverList = serverList;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }
}
