package misc.impl;

import misc.StatisticsKeeper;

public class StatisticsKeeperImpl implements StatisticsKeeper {
    private int serviceTime;
    private int waitingTime;

    public int getServiceTime() {
        return serviceTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void updateServiceTime(int serviceTime) {
        this.serviceTime += serviceTime;
    }

    public void updateWaitingTime(int waitingTime) {
        this.waitingTime += waitingTime;
    }
}