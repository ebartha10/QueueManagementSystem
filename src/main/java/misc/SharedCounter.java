package misc;

public interface SharedCounter {
    int getSimulationTime();

    void setSimulationTime(int givenTime);
    void countTime();

    int getFinishedTasks();

    void setFinishedTasks(int finishedTasks);
    void countTasks();
}
