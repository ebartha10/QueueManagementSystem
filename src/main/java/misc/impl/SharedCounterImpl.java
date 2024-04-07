package misc.impl;

import misc.SharedCounter;
import singleton.SimulationLoggerSingleton;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedCounterImpl implements SharedCounter {
    private AtomicInteger simulationTime;
    private AtomicInteger finishedTasks;

    public SharedCounterImpl() {
        simulationTime = new AtomicInteger(0);
        finishedTasks = new AtomicInteger(0);
    }

    public int getSimulationTime() {
        return this.simulationTime.get();
    }

    public void setSimulationTime(int givenTime) {
        this.simulationTime.set(givenTime);
    }
    public void countTime(){
        this.simulationTime.incrementAndGet();
        SimulationLoggerSingleton.getSimulationLogger().logTime(simulationTime.get());
    }

    public int getFinishedTasks() {
        return this.finishedTasks.get();
    }

    public void setFinishedTasks(int finishedTasks) {
        this.finishedTasks.set(finishedTasks);
    }
    public void countTasks(){
        this.finishedTasks.incrementAndGet();
    }
}
