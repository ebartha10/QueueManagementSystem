package misc;

import model.Task;

import java.io.IOException;
import java.util.List;

public interface SimulationLogger {
    String LOG_FILE_WRITE = "log.txt";
    void logSimulationStart(List<Task> givenTaskList, int serverNumber, int simulationTime);
    void logTaskStart(Task givenTask, int serverID);
    void logTaskEnd(Task givenTask, int serverID);
    void logSimulationEnd(float givenServiceTime, float givenWaitingTime);
    void logTime(int givenTime);
}
