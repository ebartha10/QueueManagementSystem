package scheduler;

import model.Task;
import server.impl.ServerImpl;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public interface Scheduler {
     int STRATEGY_TIME = 0, STRATEGY_LENGTH = 1;
    void dispatchTask(Task givenTask, int strategy);
    List<ServerImpl> getServerList();
    int getNumberOfServers();
    void setNumberOfServers(int numberOfServers);
    void setServerList(List<ServerImpl> serverList);
}
